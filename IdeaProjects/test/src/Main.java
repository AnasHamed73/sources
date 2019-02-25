//import helpers.Utils;
//
//import java.util.*;
//
//public class Main {
//    private static Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        int[] arr = {9, 3, 1, 99, 7, -1};
//        Utils.mergeSort(arr);
//        Utils.dispArr(arr);
//    }
//}

import datastructures.BasicStringBuilder;
import datastructures.BinaryTreeNode;
import helpers.Utils;

import java.util.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        LinkedList<Integer>[] graph = new LinkedList[n + 1];
        for (int i = 0; i < m; i++) {
            int fromVertex = scanner.nextInt();
            int toVertex = scanner.nextInt();
            appendEdges(graph, fromVertex, toVertex);
        }
        detectCycle(graph, n, m);
//        System.out.println(perfectSubstring("1102021222", 2));
    }

    public static void detectCycle(List<Integer>[] graph, int n, int m) {
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                List<Integer> cycle = detectCycle(graph, i, new LinkedList<>(), new boolean[n + 1], visited);
                if (cycle != null) {
                    printPath(cycle);
                    return;
                }
            }
        }
        System.out.println(0);
    }

    private static List<Integer> detectCycle(List<Integer>[] graph, int startingVertexInd, LinkedList<Integer> path,
                                             boolean[] encountered, boolean[] visited) {
        List<Integer> neighbors = graph[startingVertexInd];
        if(neighbors != null && !neighbors.isEmpty()) {
            encountered[startingVertexInd] = true;
            path.addLast(startingVertexInd);
            for (Integer v : graph[startingVertexInd]) {
                if (encountered[v]) {  //cycle detected
                    return path.subList(path.indexOf(v), path.size());
                } else {
                    List<Integer> res = detectCycle(graph, v, path, encountered, visited);
                    if (res != null) {
                        return res;
                    }
                }
            }
            encountered[startingVertexInd] = false;
            path.removeLast();
        }
        visited[startingVertexInd] = true;
        return null;
    }

    private static void appendEdges(LinkedList<Integer>[] graph, int fromVertex, int toVertex) {
        LinkedList<Integer> neighbors = graph[fromVertex];
        if (neighbors == null) {
            graph[fromVertex] = new LinkedList<>();
        }
        graph[fromVertex].addLast(toVertex);
    }


    private static void printPath(List<Integer> cycle) {
        System.out.print(cycle.size());
        for (int i = 0; i < cycle.size(); i++) {
            System.out.print(" " + cycle.get(i));
        }
    }

/*
   private static int[] getCyclePath(LinkedList<Integer> path, Integer v) {
       Iterator<Integer> pathItr = path.iterator();
       int[] cycle = null;
       int i = 0;
       while (pathItr.hasNext()) {  //find cycle path
           Integer pathV = pathItr.next();
           if (pathV == v) {
               int cycleLength = path.size() - i;
               cycle = new int[cycleLength];
               cycle[0] = pathV;
               for (int j = 1; j < cycleLength; j++) {
                   cycle[j] = pathItr.next();
               }
           } else {
               i++;
           }
       }
       return cycle;
   }
*/

    private static void cycle2(LinkedList<Integer>[] graph) {
        appendEdges(graph, 1, 2);
        appendEdges(graph, 2, 3);
        appendEdges(graph, 3, 4);
        appendEdges(graph, 4, 5);
        appendEdges(graph, 5, 6);
        appendEdges(graph, 5, 1);
    }

    private static void noCycle2(LinkedList<Integer>[] graph) {
        appendEdges(graph, 1, 2);
        appendEdges(graph, 2, 3);
        appendEdges(graph, 3, 4);
        appendEdges(graph, 4, 5);
        appendEdges(graph, 5, 6);
        appendEdges(graph, 6, 7);
        appendEdges(graph, 7, 8);
        appendEdges(graph, 8, 9);
    }

    private static void cycle(LinkedList<Integer>[] graph) {
        appendEdges(graph, 1, 2);
        appendEdges(graph, 4, 2);
        appendEdges(graph, 1, 4);
        appendEdges(graph, 3, 4);
        appendEdges(graph, 4, 6);
        appendEdges(graph, 3, 6);
        appendEdges(graph, 5, 3);
        appendEdges(graph, 2, 5);
        appendEdges(graph, 1, 5);
    }

    private static void noCycle(LinkedList<Integer>[] graph) {
        appendEdges(graph, 1, 2);
        appendEdges(graph, 2, 3);
        appendEdges(graph, 1, 3);
    }


    public static int perfectSubstring(String s, int k) {
        int perfectStrs = 0;
        int div = k;
        while (div < s.length()) {
            int[] freqs = new int[10];
            for (int j = 0; j < div; j++)
                freqs[asInt(s.charAt(j))]++;
            if (isPerfect(freqs, k))
                perfectStrs++;
            for (int i = 0; i < s.length() - div; i++) {
                freqs[asInt(s.charAt(i))]--;
                freqs[asInt(s.charAt(i + div))]++;
                if (isPerfect(freqs, k))
                    perfectStrs++;
            }
            div += k;
        }
        return perfectStrs;
    }

    private static boolean isPerfect(int[] freqs, int k) {
        for (int i = 0; i < freqs.length; i++)
            if (freqs[i] != k && freqs[i] != 0)
                return false;
        return true;
    }

    private static int asInt(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return 0;
        }
    }

    static int closestPal(int n) {
        if (isPalindrome(n)) return n;
        int numDigits = calcDigits(n);
        int[] pals = new int[numDigits * 2];
        int[] diff = new int[numDigits * 2];
        int palsCounter = 0;
        for (int i = 1; palsCounter < numDigits * 2; i *= 10) {
            for (int j = -1; j <= 1; j += 2) {
                int lessPal = closestPal0(n + (j * i));
                pals[palsCounter] = lessPal;
                diff[palsCounter++] = Math.abs(n - lessPal);
            }
        }
        Utils.dispArr(pals);
        Utils.dispArr(diff);
        return pals[minIndex(diff, pals)];
    }

    static int closestPal0(int n) {
        if (n < 0)
            return Integer.MAX_VALUE;
        StringBuilder builder = new StringBuilder();
        String nstr = String.valueOf(n);
        boolean evenDigits = nstr.length() % 2 == 0;
        builder.append(nstr, 0, evenDigits ? nstr.length() / 2 : nstr.length() / 2 + 1);
        for (int i = nstr.length() / 2 - 1; i >= 0; i--)
            builder.append(nstr.charAt(i));
        return Integer.parseInt(builder.toString());
    }

    private static int minIndex(int[] diff, int[] pals) {
        int minIndex = 0;
        for (int i = 0; i < diff.length; i++)
            if (diff[i] < diff[minIndex] ||
                    (diff[i] == diff[minIndex] && pals[i] < pals[minIndex]))
                minIndex = i;
        return minIndex;
    }

    private static int calcDigits(int n) {
        int digits = 1;
        while ((n /= 10) > 0)
            ++digits;
        return digits;
    }

    private static boolean isPalindrome(int n) {
        String str = String.valueOf(n);
        for (int i = 0, j = str.length() - 1; i < j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) return false;
        }
        return true;
    }

    static void diamond(char c, Integer sideLen) {
        if (sideLen < 2) return;
        int spaceCount = sideLen - 1;
        int spaceUpdate = -1, charUpdate = 2;
        int charCount = -1;
        String str = String.valueOf(c);
        String sideLenStr = sideLen.toString();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < sideLen - j; i++) {
                print(" ", spaceCount);
                print(sideLenStr, 1);
                print(str, charCount);
                if (charCount > 0)
                    print(sideLenStr, 1);
                print("\n", 1);
                spaceCount += spaceUpdate;
                charCount += charUpdate;
            }
            spaceCount = 1;
            charCount = (sideLen * 2) - 5;
            spaceUpdate = 1;
            charUpdate = -2;
        }
    }

    static void print(String c, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(c);
        }
    }

    static <T> void partition(T[][] a, int partitions) {
        if (partitions > a[0].length)
            return;
        int biggerPartitionsCount = a[0].length % partitions;
        int partitionSize = 1 + (a[0].length / partitions);
        for (int i = 0; i < biggerPartitionsCount; i++) {
            printArr(a, i * partitionSize, partitionSize);
        }
        for (int i = 0; i < partitions - biggerPartitionsCount; i++) {
            printArr(a, i * (partitionSize - 1), partitionSize - 1);
        }
    }

    static <T> void printArr(T[][] a, int startColumn, int numColumns) {
        for (int i = 0; i < a.length; i++) {
            for (int j = startColumn; j < startColumn + numColumns; j++)
                System.out.print(a[i][j]);
            System.out.println();
        }
        System.out.println("---------------");
    }

    static <T> void zigzag(T[][] a) {
        Coordinate upperLeft = new Coordinate(0, 0);
        Coordinate bottomRight = new Coordinate(0, 0);
        boolean firstHalf = true;
        boolean downToUp = true;
        for (int i = 0; i < (a.length * 2) - 1;
             i++, firstHalf = i < a.length - 1, downToUp = !downToUp) {
            printInvDiagonal(a, upperLeft, bottomRight, downToUp);
            if (firstHalf) {
                bottomRight.r++;
                bottomRight.c++;
            } else {
                upperLeft.c++;
                upperLeft.r++;
            }
        }
    }

    static <T> void printInvDiagonal(T[][] a, Coordinate upperLeft,
                                     Coordinate bottomRight, boolean downToUp) {
        int len = bottomRight.c - upperLeft.c;
        int r, c;
        if (downToUp) {
            r = bottomRight.r;
            c = upperLeft.c;
            for (int i = 0; i <= len; i++)
                System.out.print(a[r--][c++] + " ");
        } else {
            r = upperLeft.r;
            c = bottomRight.c;
            for (int i = 0; i <= len; i++)
                System.out.print(a[r++][c--] + " ");
        }
        System.out.println();
    }

    private static class Coordinate {
        int c;
        int r;

        public Coordinate(int x, int y) {
            this.c = x;
            this.r = y;
        }
    }

    static <T> boolean isBalanced(BinaryTreeNode<T> root) {
        return isBalanced(root, -1);
    }

    private static <T> boolean isBalanced(BinaryTreeNode<T> node, int depth) {
        if (node == null) return true;
        int leftDepth = getHeight(node.getLeft(), depth + 1);
        int rightDepth = getHeight(node.getRight(), depth + 1);
        return Math.abs(leftDepth - rightDepth) <= 1 && isBalanced(node.getLeft(), depth + 1)
                && isBalanced(node.getRight(), depth + 1);
    }

    private static int getHeight(BinaryTreeNode node, int depth) {
        if (node == null) return depth;
        return Math.max(getHeight(node.getLeft(), depth + 1),
                getHeight(node.getRight(), depth + 1));
    }

    static void highestSum(int[] a) {
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min)
                min = a[i];
            if (a[i] > max)
                max = a[i];
        }
        System.out.println(max - min);
    }

    static void createBst(int[] a) {
        if (a.length == 0)
            return;
        createBst(a, 0, a.length - 1);
    }

    private static void createBst(int[] a, int lo, int hi) {
        int m = (lo + hi) / 2;
        System.out.println("adding " + a[m]);
        if (lo != m) createBst(a, lo, m - 1);
        if (hi != m) createBst(a, m + 1, hi);
    }


    static boolean isRotation(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;
        List<Integer> indexes = indexesOf(s2, s1.charAt(0));
        for (int index : indexes) {
            if (isRotation(s1, 0, s2, index))
                return true;
        }
        return false;
    }

    private static boolean isRotation(String s1, int s1Start, String s2, int s2Start) {
        int count = 0;
        for (int i = s1Start, j = s2Start; count < s1.length(); ) {
            if (s1.charAt(i) != s2.charAt(j))
                return false;
            i = (i + 1) % s1.length();
            j = (j + 1) % s2.length();
            count++;
        }
        return true;
    }

    static List<Integer> indexesOf(String s, char c) {
        List<Integer> inds = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == c)
                inds.add(i);
        return inds;
    }

    static void rotate(int[][] img) {
        int temp1, temp2, temp3;
        int len = img.length;
        for (int i = 0; i < len / 2; i++)
            for (int j = i; j < len - i - 1; j++) {
                temp1 = img[len - i - 1][j];
                temp2 = img[len - j - 1][len - i - 1];
                temp3 = img[i][len - j - 1];
                img[len - i - 1][j] = img[j][i];
                img[len - j - 1][len - i - 1] = temp1;
                img[i][len - j - 1] = temp2;
                img[j][i] = temp3;
            }
    }

    static String compress(String s) {
        BasicStringBuilder builder = new BasicStringBuilder();
        int i = 0;
        while (i < s.length()) {
            char current = s.charAt(i);
            int count = 1;
            while (++i < s.length() && s.charAt(i) == current)
                count++;
            builder.append(current).append(count);
        }
        return builder.length() < s.length() ? builder.toString() : s;
    }

    static boolean oneAway(String a, String b) {
        String shorts, longs;
        if (Math.abs(a.length() - b.length()) >= 2)
            return false;
        if (a.length() < b.length()) {
            shorts = a;
            longs = b;
        } else {
            shorts = b;
            longs = a;
        }
        if (a.length() == b.length())
            return repCheck(longs, shorts);
        else return addRemCheck(longs, shorts);
    }

    private static boolean addRemCheck(String longs, String shorts) {
        int c = 0;
        for (int i = 0; i < shorts.length(); i++) {
            if (longs.charAt(i) != shorts.charAt(i - c)) {
                c++;
                if (c > 1)
                    return false;
            }
        }
        return (!longs.isEmpty() && c == 0) || c <= 1;
    }

    private static boolean repCheck(String a, String b) {
        int c = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                c++;
                if (c > 1)
                    return false;
            }
        }
        return true;
    }


    static boolean palPerm(String s) {
        Map<Character, Integer> freqs = new HashMap<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            char current = Character.toLowerCase(s.charAt(i));
            if (current == ' ')
                continue;
            Integer count = freqs.get(current);
            if (count == null)
                count = 0;
            freqs.put(current, count + 1);
        }
        int odds = 0;
        for (int c : freqs.values()) {
            if (c % 2 == 1) {
                odds++;
                if (odds > 1)
                    return false;
            }
        }
        return true;
    }

    static void url(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                System.arraycopy(chars, i + 1, chars, i + 3, chars.length - i - 3);
                subSpace(chars, i);
            }
        }
    }

    static void subSpace(char[] c, int i) {
        c[i] = '%';
        c[i + 1] = '2';
        c[i + 2] = '0';
    }

    static boolean isPerm(String a, String b) {
        if (a.length() != b.length())
            return false;
        Map<Character, Integer> aChars = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            Integer c = aChars.get(a.charAt(i));
            if (c == null)
                c = 0;
            aChars.put(a.charAt(i), c + 1);
        }
        for (int i = 0; i < b.length(); i++) {
            char current = b.charAt(i);
            Integer aCount = aChars.get(current);
            if (aCount == null || aCount == 0)
                return false;
            aChars.put(current, aCount - 1);
        }
        return true;
    }

    private static boolean isUnique(String s) {
        boolean[] codes = new boolean[(int) Character.MAX_VALUE];
        for (int i = 0; i < s.length(); i++) {
            if (codes[s.charAt(i)] == true) {
                return false;
            }
            codes[s.charAt(i)] = true;
        }
        return true;
    }

    private static List<Integer> findCommon(int[] a, int[] b) {
        List<Integer> common = new ArrayList<>(a.length);
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            int av = a[i];
            int bv = b[j];
            if (av == bv) {
                common.add(av);
                i++;
                j++;
            } else if (av > bv) {
                j++;
            } else if (av < bv) {
                i++;
            }
        }
        return common;
    }

    private static void perms(String s, String b) {
        Map<Character, Integer> sFreq = getFreq(s);
        for (int i = 0; i <= b.length() - s.length(); i++) {
            if (sFreq.get(b.charAt(i)) != null) {
                Map<Character, Integer> bFreq = new HashMap<>(s.length());
                boolean found = true;
                for (int j = 0; j < s.length(); j++) {
                    char current = b.charAt(i + j);
                    if (sFreq.get(current) == null) {
                        found = false;
                        break;
                    }
                    Integer currentFreq = bFreq.get(current);
                    if (currentFreq == null)
                        currentFreq = 0;
                    bFreq.put(current, currentFreq + 1);
                    if (bFreq.get(current) > sFreq.get(current)) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    System.out.println("perm found at " + i);
                }
            }
        }
    }

    private static Map<Character, Integer> getFreq(String s) {
        Map<Character, Integer> freqs = new HashMap<>(s.length());
        for (char current : s.toCharArray()) {
            Integer f = freqs.get(current);
            if (f == null)
                f = 0;
            freqs.put(current, f + 1);
        }
        return freqs;
    }

    private static void printEq(int lo, int hi) {
        Map<Integer, Integer> cubes = new HashMap<>(hi);
        for (int i = lo; i <= hi; i++) {
            cubes.put(cube(i), i);
        }
        for (int a = lo; a <= hi; a++) {
            for (int b = lo; b <= hi; b++) {
                for (int c = lo; c <= hi; c++) {
                    int missing = cube(a) + cube(b) - cube(c);
                    Integer missingIndex = cubes.get(missing);
                    if (missingIndex != null) {
                        print(a, b, c, missingIndex);
                    }
                }
            }
        }
    }

    private static void print(int a, int b, int c, Integer d) {
        System.out.format("a=%d, b=%d, c=%d, d=%d\n", a, b, c, d);
    }

    private static int cube(int n) {
        return n * n * n;
    }

    private static int hashPairDiff(int[] a, int k) {
        int matches = 0;
        HashSet<Integer> cache = new HashSet<>(a.length);
        for (int i = 0; i < a.length; i++) {
            if (cache.contains(a[i] - k)) {
                matches++;
            }
            if (cache.contains(a[i] + k)) {
                matches++;
            }
            cache.add(a[i]);
        }
        return matches;
    }

    private static int pairDiff(int[] a, int k) {
        int matches = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (Math.abs(a[i] - a[j]) == k)
                    matches++;
            }
        }
        return matches;
    }

    private static void zeroArray(int[][] a) {
        int[][] zeros = new int[2][a.length * a[0].length];
        int zerosIndex = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] == 0) {
                    zeros[0][zerosIndex] = i;
                    zeros[1][zerosIndex] = j;
                    zerosIndex++;
                }
            }
        }
        for (int i = 0; i < zerosIndex; i++) {
            a[zeros[0][i]] = new int[a[zeros[0][i]].length];
            for (int j = 0; j < a.length; j++) {
                a[j][zeros[1][i]] = 0;
            }
        }
    }

    static void permutation(String str) {
        permutation(str, "");
    }

    static void permutation(String str, String prefix) {
        System.out.println("str: " + str + "; prefix: " + prefix);
        if (str.length() == 0)
//            System.out.println(prefix);
            ;
        else {
            for (int i = 0; i < str.length(); i++) {
                String rem = str.substring(0, i) + str.substring(i + 1);
                permutation(rem, prefix + str.charAt(i));
            }
        }
    }
}


