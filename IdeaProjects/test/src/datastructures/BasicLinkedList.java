package datastructures;

public class BasicLinkedList<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public BasicLinkedList() {
    }

    public T getFirst() {
        return first.getValue();
    }

    public T getLast() {
        return last.getValue();
    }

    public int getSize() {
        return size;
    }

    public boolean contains(T el) {
        Node<T> node = first;
        while(node != null) {
            if(node.getValue().equals(el))
                return true;
            node = node.getNext();
        }
        return false;
    }

    public T get(int index) {
        rangeCheck(index);
        Node<T> n = first;
        for(int i = 0; i < index; i++)
            n = n.getNext();
        return n.getValue();
    }

    public void addFirst(T el) {
        if(first == null) {
            addLast(el);
            return;
        }
        Node<T> n = new Node<>(el);
        Node<T> oldFirst = first;
        n.setNext(oldFirst);
        first = n;
        size++;
    }

    public boolean addLast(T el) {
        Node<T> n = new Node<>(el);
        if(isEmpty()) {
            first = n;
            last = first;
        } else {
            Node<T> oldLast = last;
            oldLast.setNext(n);
            last = n;
        }
        size++;
        return true;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public boolean remove(T el) {
        if(isEmpty())
            return false;
        Node<T> prev = null;
        Node<T> node = first;
        while(node != null) {
            if(node.getValue().equals(el)) {
                remove(node, prev);
                return true;
            }
            prev = node;
            node = node.getNext();
        }
        return false;
    }

    public boolean remove(int index) {
        rangeCheck(index);
        Node<T> prev = null;
        Node<T> node = first;
        for(int i = 0; i < index; i++) {
            prev = node;
            node = node.getNext();
        }
        remove(node, prev);
        return true;
    }

    private void rangeCheck(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
    }

    private void remove(Node<T> node, Node<T> prev) {
        if(first == node) {
            Node<T> oldFirst = first;
            first = oldFirst.getNext();
        } else if(last == node) {
            prev.setNext(null);
            last = prev;
        } else {
            Node<T> newNext = node.next;
            prev.setNext(newNext);
        }
        size--;
    }

    @Override
    public String toString() {
        BasicStringBuilder sb = new BasicStringBuilder();
        sb.append("[");
        if(!isEmpty()) {
            sb.append(first.getValue().toString());
            if (first.getNext() != null) {
                Node<T> node = first.getNext();
                while (node != null) {
                    sb.append("->" + node.getValue());
                    node = node.getNext();
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static class Node<T> {
        private Node<T> next;
        private T val;

        Node(T val) {
            this.val = val;
        }

        Node<T> getNext() {
            return next;
        }

        void setNext(Node<T> next) {
            this.next = next;
        }

        T getValue() {
            return val;
        }

        void setVal(T val) {
            this.val = val;
        }
    }
}
