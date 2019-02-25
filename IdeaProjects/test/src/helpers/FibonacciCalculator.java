package helpers;

import java.math.BigInteger;
import java.util.Arrays;

public class FibonacciCalculator {

    private static BigInteger[] fibCache = new BigInteger[1001];
    public static final BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);

    static {
        Arrays.fill(fibCache, BigInteger.valueOf(-1));
    }

    public static BigInteger fib(int index) {
        if(!fibCache[index].equals(NEGATIVE_ONE))
            return fibCache[index];
        if(index == 1)
            return BigInteger.ZERO;
        if(index == 2)
            return BigInteger.ONE;
        BigInteger fibVal = fib(index - 1).add(fib(index - 2));
        fibCache[index] = fibVal;
        return fibVal;
    }
}
