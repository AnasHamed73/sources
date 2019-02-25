import datastructures.BasicLinkedList;
import org.junit.Test;

public class LinkedListTest {

    @Test
    public void givenEmptyList_WhenCallingAddLast_ElementsShouldBeAppended() {
        BasicLinkedList<String> list = new BasicLinkedList<>();
        list.addLast("a");
        print(list);
        list.addLast("b");
        print(list);
        list.addLast("c");
        print(list);
        list.remove("a");
        print(list);
        list.remove("b");
        print(list);
    }

    @Test
    public void removeTest() {
        BasicLinkedList<String> list = new BasicLinkedList<>();
        list.addLast("a");
        list.addLast("pr");
        list.addLast("fg");
        print(list);
        list.addLast("b");
        print(list);
        list.addLast("c");
        print(list);
        list.remove("pr");
        print(list);
        list.remove("c");
        print(list);

        list.remove(1);
        print(list);
    }

    @Test
    public void addFirstTest() {
        BasicLinkedList<String> list = new BasicLinkedList<>();
        list.addFirst("a");
        print(list);
        list.addFirst("v");
        print(list);
        list.remove("v");
        print(list);
    }

    @Test
    public void getTest() {
        BasicLinkedList<String> list = new BasicLinkedList<>();
        list.addFirst("a");
        list.addLast("t");
        list.addLast("t");
        list.addLast("p");
        print(list);
        System.out.println(list.get(2));
    }

    @Test
    public void containsTest() {
        BasicLinkedList<String> list = new BasicLinkedList<>();
        list.addFirst("a");
        System.out.println(list.contains("a"));
        list.addLast("t");
        list.remove("t");
        System.out.println(list.contains("t"));
    }

    private void print(BasicLinkedList<String> list) {
        System.out.print(list);
        System.out.print(" first: " + list.getFirst());
        System.out.print(" last: " + list.getLast());
        System.out.println(" size: " + list.getSize() + "\n");
    }
}
