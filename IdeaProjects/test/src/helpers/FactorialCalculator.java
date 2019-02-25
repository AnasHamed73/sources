package helpers;

import java.math.BigInteger;
import java.util.Arrays;

public class FactorialCalculator {

    public static final BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);
    private static BigInteger[] factCache = new BigInteger[1001];

    static {
        Arrays.fill(factCache, BigInteger.valueOf(-1));
    }


    public static BigInteger factorial(int n) {
        if(n == 0 || n == 1)
            return BigInteger.ONE;
        if(n <= 1001 && !factCache[n].equals(NEGATIVE_ONE))
            return factCache[n];
        BigInteger factVal = BigInteger.valueOf(n).multiply(factorial(n - 1));
        return factVal;
    }
}
