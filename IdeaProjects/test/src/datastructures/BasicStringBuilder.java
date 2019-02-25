package datastructures;

public class BasicStringBuilder {
    private static final int DEFAULT_CAPACITY = 10;
    private char[] chars;
    private int capacity;
    private int size = 0;

    public BasicStringBuilder() {
        chars = new char[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public BasicStringBuilder(String s) {
        capacity = Math.max(s.length() * 2, DEFAULT_CAPACITY);
        chars = new char[capacity];
        append(s);
    }

    public BasicStringBuilder append(String s) {
        int minSize = size + s.length();
        if(minSize > capacity)
            growArray(minSize);
        internalAppend(s);
        return this;
    }

    public BasicStringBuilder append(char c) {
        return append(String.valueOf(c));
    }

    public BasicStringBuilder append(int c) {
        return append(String.valueOf(c));
    }

    private void internalAppend(String s) {
        System.arraycopy(s.toCharArray(), 0, chars, size, s.length());
        size += s.length();
    }

    private void growArray(int minSize) {
        char[] newArray = new char[minSize * 2];
        System.arraycopy(chars, 0, newArray, 0, size);
        chars = newArray;
        capacity = chars.length;
    }

    public int length() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return new String(chars, 0, size);

    }
}
