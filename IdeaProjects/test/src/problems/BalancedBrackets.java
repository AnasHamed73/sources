package problems;

import java.util.*;

public class BalancedBrackets {

    private static Map<Character, Character> openingChars = new HashMap<>(3);

    static {
        openingChars.put('{', '}');
        openingChars.put('(', ')');
        openingChars.put('[', ']');
    }

    public static void start() {
        try (Scanner scn = new Scanner(System.in)) {
            int size = Integer.parseInt(scn.nextLine());
            while (size-- > 0)
                System.out.println(isBalanced(scn.nextLine().toCharArray()) ? "YES" : "NO");
        }
    }

    private static boolean isBalanced(char[] chars) {
        Deque<Character> deq = new ArrayDeque<>(1_000);
        for (int i = 0; i < chars.length; i++)
            if (openingChars.containsKey(chars[i]))
                deq.push(chars[i]);
            else if (deq.isEmpty() || !openingChars.get(deq.pop()).equals(chars[i]))
                return false;
        return deq.isEmpty();
    }
}
