package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Utils {

    private Utils() {
    }

    public static void dispArr(int[] list) {
        for (int i : list)
            System.out.print(i + " ");
        System.out.println();
    }

    public static void dispArr(char[] list) {
        for (char o : list)
            System.out.print(o + " ");
        System.out.println();
    }

    public static void dispArr(Object[] list) {
        for (Object o : list)
            System.out.print(o + " ");
        System.out.println();
    }


    public static void disp2dArr(int[][] arr) {
        for (int[] row : arr) {
            for (int i : row)
                System.out.print(i + " ");
            System.out.println();
        }
    }

    public static void disp2dArr(char[][] arr) {
        for (char[] row : arr) {
            for (char i : row)
                System.out.print(i + " ");
            System.out.println();
        }
    }

    public static void dispMap(Map<?, ?> map) {
        map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    public static void mergeSort(int[] a) {
        int[] working = new int[a.length];
        mergeSort0(a, 0, a.length, working);
    }

    private static void mergeSort0(int[] a, int start, int length, int[] working) {
        if (length > 1) {
            int listSize = length / 2;
            mergeSort0(a, start, listSize, working);
            mergeSort0(a, start + listSize, listSize + (length % 2), working);
            mergeLists(a, start, length, working);
        }
    }

    private static void mergeLists(int[] a, int start, int length, int[] working) {
        int midPoint = length / 2;
        int l = 0, h = midPoint;

        System.arraycopy(a, start, working, 0, length);

        while (l < midPoint && h < length)
            a[start++] = (working[l] >= working[h]) ? working[h++] : working[l++];
        while (l < midPoint)
            a[start++] = working[l++];
        while (h < length)
            a[start++] = working[h++];
    }

    public static <T extends Comparable> T[] insertionSort(T[] list) {
        Comparable[] sorted = new Comparable[list.length];
        for (int i = 0; i < list.length; i++) {
            int index = findInsertionIndex(sorted, list[i], i);
            insert(sorted, list[i], index);
        }
        return (T[]) sorted;
    }

    private static <T extends Comparable> int findInsertionIndex(T[] list, T el, int size) {
        if (size == 0)
            return 0;
        int lo = 0, hi = size;
        int mid = (lo + hi) / 2;
        while (lo < hi) {
            T midElement = list[mid];
            int comparison = el.compareTo(midElement);
            if (comparison == 0 || (comparison > 0 && ((mid + 1) == size || el.compareTo(list[mid + 1]) <= 0))) {
                mid++;
                break;
            } else if (comparison > 0) {
                lo = mid;
                mid = (mid + hi) / 2;
            } else if (comparison < 0) {
                hi = mid;
                mid = (mid + lo) / 2;
            }
        }
        return mid;
    }

    private static <T extends Comparable> void insert(Comparable[] list, T t, int index) {
        System.arraycopy(list, index, list, index + 1, list.length - index - 1);
        list[index] = t;
    }

    public static void dispFactorials(int n) {
        System.out.println("factorials");
        for (int i = 0; i <= n; i++) {
            System.out.println(i + "! = " + FactorialCalculator.factorial(i));
        }
    }

    public static void dispFibonacci(int n) {
        System.out.println("fibonacci series");
        for (int i = 1; i <= n; i++)
            System.out.println("fib term #" + i + ": " + FibonacciCalculator.fib(i));
    }

    public static int[] trimToSize(int[] arr, int size) {
        int[] trimmed = new int[size];
        System.arraycopy(arr, 0, trimmed, 0, size);
        return trimmed;
    }

    public static int[] rotateLeft(int[] arr, int numberOfRotations) {
        int size = arr.length;
        int[] rotated = new int[size];
        System.arraycopy(arr, 0, rotated, size - numberOfRotations, numberOfRotations);
        System.arraycopy(arr, numberOfRotations, rotated, 0, size - numberOfRotations);
        return rotated;
    }

    public static <T> Iterable<T[]> calcPermutations(T[] arr) {
        return new CombinationComputer<>(arr).allPermutations();
    }

    public static double executeAndMeasureTimeFor(Runnable runnable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        runnable.run();
        return stopWatch.elapsedTime();
    }

    public static class CombinationComputer<T> {

        private T[] arr;
        private int permIndex = 0;
        private List<T[]> combos;
        private boolean invoked = false;

        public CombinationComputer(T[] dirs) {
            arr = dirs;
            combos = new ArrayList<>(arr.length);
        }

        public boolean hasNext() {
            return permIndex < combos.size();
        }

        public T[] nextPermutation() {
            if (!invoked)
                allPermutations();
            if (hasNext())
                return combos.get(permIndex++);
            throw new IllegalStateException("There are no more permutations left");
        }

        public Iterable<T[]> allPermutations() {
            invoked = true;
            combosRec(0);
            return combos;
        }

        private void combosRec(int depth) {
            if (depth == arr.length - 2) {
                for (int i = 0; i < 2; i++) {
                    combos.add(Arrays.copyOf(arr, arr.length));
                    rotateLeft(arr, depth + 1);
                }
                return;
            }
            for (int i = depth; i < arr.length; i++) {
                combosRec(depth + 1);
                rotateLeft(arr, depth + 1);
            }
        }

        private void rotateLeft(T[] arr, int from) {
            if (arr.length <= 1 || from == 0)
                return;
            T tmp = arr[from - 1];
            System.arraycopy(arr, from, arr, from - 1, arr.length - from);
            arr[arr.length - 1] = tmp;
        }
    }

    public static class StopWatch {

        private long startTime;

        public void start() {
            this.startTime = System.nanoTime();
        }

        public double elapsedTime() {
            return (double) (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

}
