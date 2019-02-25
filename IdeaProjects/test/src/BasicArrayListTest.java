import datastructures.BasicArrayList;
import org.junit.Test;

public class BasicArrayListTest {

    @Test
    public void givenEmptyArray_WhenAddingMoreElementsThanCapacity_ShouldResize() {
        BasicArrayList<String> list = new BasicArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        System.out.println(list);
        list.remove("b");
        System.out.println(list);
        list.remove(list.size() - 1);
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
    }
}
