/*
Solving the Knapsack DP problem. 
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Knapsack {

    public static void main(String[] args) {
        System.out.println();

        int[][] data = {{1, 2, 4, 4, 6, 7}, {6, 11, 1, 12, 19, 12}};
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < data[0].length; i++) {
            items.add(new Item(data[0][i], data[1][i]));
        }
        System.out.println(knapsack(items, 0, 8));
        System.out.println(knapsack(items, 8));

        Random r = new Random(7281993);
        Stopwatch st = new Stopwatch();
        for(int numItems = 20; numItems <= 10000; numItems++) {

            items = genItems(r, numItems);
            int cap = getCap(r, items);
            int result;
            System.out.println("Number of items: " + numItems + ". Capacity: " + cap);
//            st.start();
//            result = knapsack(items, 0, cap);
//            st.stop();
//            System.out.println("Recursive knapsack. Answer: " + result + ", time: " + st.time());
            st.start();
            result = knapsack(items, cap);
            st.stop();
            System.out.println("Dynamic knapsack.   Answer: " + result + ", time: " + st.time());
        }
    }

    private static int getCap(Random r, ArrayList<Item> items) {
        int totalWeight = 0;
        for(Item i : items)
            totalWeight += i.weight;

        int tenPercent = totalWeight / 10;
        totalWeight += r.nextInt(tenPercent) - (tenPercent / 2);
        return totalWeight;
    }

    private static ArrayList<Item> genItems(Random r, int numItems) {
        ArrayList<Item> result = new ArrayList<Item>();
        for(int i = 0; i < numItems; i++) {
            int weight = (int) Math.ceil(r.nextGaussian() * 5 + 7);
            if(weight <= 0)
                weight = 1;

            int value = (int) Math.ceil(r.nextGaussian() * 10 + 10);
            if(value <= 0)
                value = 1;

            result.add(new Item(weight, value));
        }
        return result;
    }

    private static int knapsack(ArrayList<Item> items, 
            int current, int capacity) {

        int result = 0;
        if(current < items.size()) {
            // don't use item
            int withoutItem = knapsack(items, current + 1, capacity);
            int withItem = 0;
            // if current item will fit, try it
            Item currentItem = items.get(current);
            if(currentItem.weight <= capacity) {
                withItem += currentItem.value;
                withItem += knapsack(items, current + 1, 
                        capacity - currentItem.weight);
            }
            result = Math.max(withoutItem, withItem);
        }
        return result;
    }

    // dynamic programming approach
    public static int knapsack(ArrayList<Item> items, int maxCapacity) {
        final int ROWS = items.size() + 1;
        final int COLS = maxCapacity + 1;
        int[][] partialSolutions = new int[ROWS][COLS];
        // first row and first column all zeros

        for(int item = 1; item <= items.size(); item++) {
            for(int capacity = 1; capacity <= maxCapacity; capacity++) {
                Item currentItem = items.get(item - 1);
                int bestSoFar = partialSolutions[item - 1][capacity];
                if(currentItem.weight <= capacity) {
                    int withItem = currentItem.value;
                    int capLeft = capacity - currentItem.weight;
                    withItem += partialSolutions[item - 1][capLeft];
                    if(withItem > bestSoFar)
                        bestSoFar = withItem;
                }
                partialSolutions[item][capacity] = bestSoFar;
            }
        }
        return partialSolutions[ROWS - 1][COLS - 1];
    }

    private static class Item {
        private int weight;
        private int value;

        private Item(int w, int v) {
            weight = w;
            value = v;
        }
    }


    public static void coinDP() {
        int[] coins = {1, 5, 12};
        int goal = 15;
        int[] solutions = findMinCoins(coins, goal);
        for(int i = 0; i < solutions.length; i++) {
            System.out.println("Total value: " + i 
                    + ", Minimum coins required: " + solutions[i]);
        }
    }


    private static int[] findMinCoins(int[] coins, final int goal) {
        int[] partialSolutions = new int[goal + 1];
        Arrays.fill(partialSolutions, Integer.MAX_VALUE);
        partialSolutions[0] = 0;
        for(int g = 1; g < partialSolutions.length; g++) {
            for(int coin : coins) {
                if(coin <= g) {
                    int coinsUsed = 1 + partialSolutions[g - coin];
                    if(coinsUsed < partialSolutions[g])
                        partialSolutions[g] = coinsUsed;
                }
            }
        }
        return partialSolutions;
    }

}
