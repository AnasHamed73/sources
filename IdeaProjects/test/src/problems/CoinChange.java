package problems;

public class CoinChange {
    private long[][] dp;
    private int[] coins;

    private CoinChange(int[] coins) {
        this.coins = coins;
    }

    public static long possibleSums(int desired, int[] coins) {
        return new CoinChange(coins).possibleSums(desired);
    }

    private long possibleSums(int desired) {
        if (coins.length == 0)
            return 0;
        this.dp = new long[desired + 1][coins.length];
        return possibleSums0(desired, 0);
    }

    private long possibleSums0(int desired, int coinInd) {
        long ways = 0;
        if (dp[desired][coinInd] != 0)
            return dp[desired][coinInd];
        for (int i = 0, con; coinInd < coins.length && (con = coins[coinInd] * i) <= desired; ++i) {
            int rem = desired - con;
            if (rem == 0) {
                ways++;
                break;
            }
            if (coinInd < coins.length - 1)
                ways += possibleSums0(rem, coinInd + 1);
        }
        return dp[desired][coinInd] = ways;
    }
}