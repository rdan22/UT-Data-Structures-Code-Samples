/*
Using functional programming to determine whether a number n is prime.
*/
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class FP {
    
    public static void main(String[] args) {
        testPrime();
    }

    public static void testPrime() {
        System.out.println("\n\nDemo of Finding Primes via Streams:");
        Stopwatch st = new Stopwatch();
        st.start();
        final int START = 2;
        final int STOP = 16_000_000; // try 8_000_000
        long count = 0;
        count = 
        IntStream.rangeClosed(START, STOP)
                .parallel()
                .filter(FP::isPrimeFaster) 
                .count();
        st.stop();
        System.out.println("time to calculate: " + st);
        System.out.println("numer of primes in range [" 
                + START + " to " + STOP + "] : " + count);
    }

    /*
     * Stop the calcualtion when we find a divisor.
     * Based on suggestion from:
     * https://stackoverflow.com/questions/23308193/break-or-return-from-java-8-stream-foreach/32566745
     */
    public static boolean isPrimeFaster(int n) {
        OptionalInt firstDivisor 
            = IntStream.range(2, ((int) (Math.sqrt(n)) + 1))
                .filter(x -> n % x == 0)
                .findFirst();
//        if (firstDivisor.isPresent()) {
//            int div = firstDivisor.getAsInt();
//            System.out.println(n + " % " + div + " = " + (n % div));
//            System.out.println(n + " / " + div + " = " + (n / div));
//        }
        return !firstDivisor.isPresent();
    }
}
