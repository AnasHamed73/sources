package problems;

import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {

    public static Map<String, Integer> calcFrequency(String s) {
        Map<String, Integer> map = new HashMap<>();
        int curInd = 0;
        char[] chars = s.toCharArray();
        int strLen = chars.length;

        loop: while (curInd < strLen) {
            while (chars[curInd++] == ' ')
                if(curInd >= strLen)
                    break loop;
            int nextSpaceIndex = s.indexOf(' ', curInd);
            String curWord = s.substring(curInd, nextSpaceIndex < 0 ? strLen : nextSpaceIndex);
            curWord = stripSpecials(curWord);
            incWordCount(map, curWord);
            curInd += curWord.length() + 1;
        }
        return map;
    }

    private static String stripSpecials(String curWord) {
        if (curWord.substring(curWord.length() - 1).matches("[@#$!%^&()_-[{]};:'\",./<>?\n]"))
            curWord = curWord.substring(0, curWord.length() - 1);
        return curWord;
    }

    private static void incWordCount(Map<String, Integer> map, String curWord) {
        if(curWord.isEmpty())
            return;
        Integer curCount = map.get(curWord);
        if (curCount == null) {
            map.put(curWord, 1);
        } else {
            map.put(curWord, curCount + 1);
        }
    }
}
