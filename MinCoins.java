/*
Solving the Min Coins DP problem. 
*/

import java.util.Arrays;

public class MinCoins {

    public static void main(String[] args) {
        int[] coins = {1, 5, 10, 25};
        int minCoin = minCoinsDynamicProgramming(15, coins);
        System.out.println("For 15 cents, we need : " + minCoin + " coins.");
        for(int i = 1; i <= 10_000; i++) {
            int result = minCoinsDynamicProgramming(i, coins);
            System.out.println("target sum: " + i + " min coins needed: " + result);
        }
    }

    // minimum number of coins to make change for FINAL_TARGET
    // given the denominations in coins
    public static int minCoinsDynamicProgramming(final int FINAL_TARGET, int[] coinValues) {
        int[] minCoins = new int[FINAL_TARGET + 1];
        // minCoins[0] == 0
        for (int tgt = 1; tgt <= FINAL_TARGET; tgt++) {
            minCoins[tgt] = INFINITY;
            // try all the coin denominations
            for (int coin : coinValues) {
                if (coin <= tgt) {
                    // I can use the coin
                    int used = 1 + minCoins[tgt - coin];
                    if (used < minCoins[tgt]) {
                        // that's better, let's remember
                        minCoins[tgt] = used;
                    }
                }
            }
        }
        return minCoins[FINAL_TARGET];
    }  
}
