/*
A faster way to calculate the nth fibonacci number
using Dynamic Programming (memoization)
*/
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibMemo {

    private static List<BigInteger> lookupTable 
        = new ArrayList<BigInteger>();
    
    private static final BigInteger one 
        = new BigInteger("1");
    
    static {
        // no fib for n == 0
        lookupTable.add(null);
        lookupTable.add(one);
        lookupTable.add(one);
    }
    
    // prevent instantation
    private FibMemo(){}
    
    public static BigInteger fib(int n) {
        // check lookup table
        if (n < lookupTable.size()) {
            return lookupTable.get(n);
        }
        
        // must calculate nth fibonacci
        // don't repeat work
        BigInteger smallTerm 
            = lookupTable.get(lookupTable.size() - 2);
        BigInteger largeTerm 
            = lookupTable.get(lookupTable.size() - 1);
        for(int i = lookupTable.size(); i <= n; i++) {
            BigInteger temp = largeTerm;
            largeTerm = largeTerm.add(smallTerm);
            // memo, store in lookup table
            lookupTable.add(largeTerm); 
            smallTerm = temp;
        }
        return largeTerm;
    }
    
    public static void showTable() {
        for(int i = 1; i < lookupTable.size(); i++) 
            System.out.println(i + " " + lookupTable.get(i));
    }
    
    public static void main(String[] args) {
        System.out.println(FibMemo.fib(50));
        FibMemo.showTable();
    }
}
