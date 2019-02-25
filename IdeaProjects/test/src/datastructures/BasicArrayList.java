package datastructures;

public class BasicArrayList<T> {
    private static final int DEFAULT_CAPACITY = 3;
    private T[] elements;
    private int size = 0;
    private int capacity;

    public BasicArrayList() {
        initElements(DEFAULT_CAPACITY);
    }

    public BasicArrayList(int capacity) {
        initElements(capacity);
    }

    private void initElements(int capacity) {
        elements = (T[]) new Object[capacity];
        this.capacity = elements.length;
    }

    public boolean add(T el) {
        if (size == capacity) {
            growArray(capacity + 1);
        }
        elements[size++] = el;
        return false;
    }

    public boolean remove(T el) {
        if (el == null)
            return false;
        int index = indexOf(el);
        if (index == -1)
            return false;
        return remove(index);
    }

    public boolean remove(int i) {
        if(i >= size || i < 0)
            throw new ArrayIndexOutOfBoundsException();
        if (i < size - 1) {
            System.arraycopy(elements, i + 1, elements, i, size - 1 - i);
        }
        elements[size - 1] = null;
        size--;
        return true;
    }

    public int indexOf(T el) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(el))
                return i;
        }
        return -1;
    }

    public boolean contains(T el) {
        return indexOf(el) != -1;
    }

    public T get(int index) {
        return elements[index];
    }

    private void growArray(int minSize) {
        T[] newArray = (T[]) new Object[minSize * 2];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
        capacity = elements.length;
    }

    public int capacity() {
        return capacity;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        BasicStringBuilder sb = new BasicStringBuilder();
        sb.append("[");
        if (size > 0)
            sb.append(elements[0].toString());
        for (int i = 1; i < size; i++)
            sb.append(", " + elements[i]);
        return sb.append("]").toString();
    }
}
