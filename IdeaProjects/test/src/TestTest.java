import org.junit.Assert;
import org.junit.Test;
import problems.CheapestPathComputer;
import problems.CoinChange;

public class TestTest {

    @Test
    public void test00() {
        Assert.assertEquals(0, CoinChange.possibleSums(51, new int[]{}));
    }

    @Test
    public void test0() {
        Assert.assertEquals(1, CoinChange.possibleSums(5, new int[]{1}));
    }

    @Test
    public void test1() {
        Assert.assertEquals(15, CoinChange.possibleSums(12, new int[]{1, 2, 5, 10}));
    }

    @Test
    public void test2() {
        Assert.assertEquals(4, CoinChange.possibleSums(4, new int[]{1, 2, 3}));
    }

    @Test
    public void test3() {
        Assert.assertEquals(5, CoinChange.possibleSums(10, new int[]{2, 5, 3, 6}));
    }

    @Test
    public void test4() {
        long actual = CoinChange.possibleSums(75, new int[]{25, 10, 11, 29, 49, 31, 33, 39, 12, 36, 40, 22, 21, 16, 37, 8, 18, 4, 27, 17, 26, 32, 6, 38, 2, 30, 34});
        Assert.assertEquals(16694, actual);
    }

    @Test
    public void test5() {
        long actual = CoinChange.possibleSums(166, new int[]{5, 37, 8, 39, 33, 17, 22, 32, 13, 7, 10, 35, 40, 2, 43, 49, 46, 19, 41, 1, 12, 11, 28});
        Assert.assertEquals(96190959, actual);
    }

    @Test
    public void test6() {
        long actual = CoinChange.possibleSums(250, new int[]{41, 34, 46, 9, 37, 32, 42, 21, 7, 13, 1, 24, 3, 43, 2, 23, 8, 45, 19, 30, 29, 18, 35, 11});
        Assert.assertEquals(15685693751L, actual);
    }

    public static long makeChange(int money, int[] coins) {
        long[] DP = new long[money + 1];
        DP[0] = (long) 1;
        for (int coin : coins)
            for (int j = coin; j < DP.length; j++)
                DP[j] += DP[j - coin];
        return DP[money];
    }

}