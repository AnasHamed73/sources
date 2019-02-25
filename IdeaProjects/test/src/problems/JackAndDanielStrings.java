package problems;

import java.util.Scanner;

public class JackAndDanielStrings {
    public static void morganString() {
        try (Scanner scn = new Scanner(System.in)) {
            int cases = Integer.parseInt(scn.nextLine());
            while (cases-- > 0) {
                String jack = scn.nextLine();
                String daniel = scn.nextLine();
                System.out.println(morganAndString(jack, daniel));
            }
        }
    }

    private static String morganAndString(String j, String d) {
        int totalLength = j.length() + d.length();
        StringBuilder buffer = new StringBuilder(totalLength);
        int jInd = 0;
        int dInd = 0;
        while (buffer.length() < totalLength) {
            if (appendJ(j, d, jInd, dInd))
                buffer.append(j.charAt(jInd++));
            else
                buffer.append(d.charAt(dInd++));
        }
        return buffer.toString();
    }

    private static boolean appendJ(String j, String d, int jStartIndex, int dStartIndex) {
        if (jStartIndex == j.length())
            return false;
        if (dStartIndex == d.length())
            return true;
        if (j.charAt(jStartIndex) < d.charAt(dStartIndex))
            return true;
        if (j.charAt(jStartIndex) > d.charAt(dStartIndex))
            return false;
        boolean jIsWrap = false, dIsWrap = false;
        int jIncrementedIndex = jStartIndex, dIncrementedIndex = dStartIndex;
        while ((jIncrementedIndex < j.length() || dIncrementedIndex < d.length()) && !(jIsWrap && dIsWrap)) {
            if (!jIsWrap) {
                jIncrementedIndex = circularIncrementIndex(jIncrementedIndex, j.length());
                if (jIncrementedIndex == -1) {
                    jIsWrap = true;
                    jIncrementedIndex = jStartIndex;
                }
            }
            if (!dIsWrap) {
                dIncrementedIndex = circularIncrementIndex(dIncrementedIndex, d.length());
                if (dIncrementedIndex == -1) {
                    dIsWrap = true;
                    dIncrementedIndex = dStartIndex;
                }
            }
            if (j.charAt(jIncrementedIndex) < d.charAt(dIncrementedIndex))
                return true;
            if (j.charAt(jIncrementedIndex) > d.charAt(dIncrementedIndex))
                return false;
        }
        return true;
    }

    private static int circularIncrementIndex(int ind, int length) {
        if (ind == length - 1)
            return -1;
        return ind + 1;
    }
}
