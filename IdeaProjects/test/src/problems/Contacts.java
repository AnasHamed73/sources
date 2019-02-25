package problems;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * We're going to make our own problems.Contacts application! The application must perform two types of operations:
 * <p>
 * add name, where name is a string denoting a contact name. This must store  as a new contact in the application.
 * find partial, where partial is a string denoting a partial name to search the application for. It must count the number of contacts starting with partial and print the count on a new line.
 * Given n sequential add and find operations, perform each operation in order.
 * <p>
 * Input Format
 * <p>
 * The first line contains a single integer, , denoting the number of operations to perform.
 * Each line  of the  subsequent lines contains an operation in one of the two forms defined above.
 * <p>
 * Constraints
 * <p>
 * It is guaranteed that  and  contain lowercase English letters only.
 * The input doesn't have any duplicate  for the  operation.
 * Output Format
 * <p>
 * For each find partial operation, print the number of contact names starting with  on a new line.
 * <p>
 * Sample Input
 * <p>
 * 4
 * add hack
 * add hackerrank
 * find hac
 * find hak
 * Sample Output
 * <p>
 * 2
 * 0
 */

public class Contacts {

    private static Map<String, Integer> keys = new HashMap<>(2_100_000);

    public void start() {
        try (Scanner s = new Scanner(System.in)) {
            int size = Integer.parseInt(s.nextLine());
            while (size-- > 0) {
                String input = s.nextLine();
                String[] inputEls = input.split(" ");
                String op = inputEls[0];
                String contact = inputEls[1];

                if ("add".equals(op))
                    addContact(contact);
                else if ("find".equals(op))
                    printMatches(contact);
            }
        }
    }

    public static void printMatches(String contact) {
        Integer val = keys.get(contact);
        System.out.println(val != null ? val : 0);
    }

    public static void addContact(String contact) {
        char[] chars = contact.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char[] key = new char[i + 1];
            System.arraycopy(chars, 0, key, 0, i + 1);
            String keyString = String.valueOf(key);
            addToKeys(keyString);
        }
    }

    private static void addToKeys(String key) {
        if (keys.get(key) == null) {
            keys.put(key, 1);
            return;
        }
        keys.put(key, keys.get(key) + 1);
    }
}
