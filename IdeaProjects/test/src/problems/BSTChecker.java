package problems;

import java.util.function.Predicate;

public class BSTChecker {

    static boolean checkBST(Node root) {
        return doCheckBST(root, d -> true, d -> true);
    }

    static boolean doCheckBST(Node node, Predicate<Integer> parentPred, Predicate<Integer> ancestorPred) {
        if(node == null)
            return true;
        boolean leftConf = true, rightConf = true;
        int data = node.data;
        Node left = node.left;
        Node right = node.right;

        if(left != null) {
            int val = left.data;
            leftConf = parentPred.test(val) && val < data;
        }
        if(right != null) {
            int val = right.data;
            rightConf = parentPred.test(val) && val > data ;
        }

        return parentPred.test(data) && ancestorPred.test(data) && leftConf && rightConf
                && doCheckBST(left, d -> d < data, ancestorPred.and(d -> d < data))
                && doCheckBST(right, d -> d > data, ancestorPred.and(d -> d > data));
    }

    static class Node {
        int data;
        Node left;
        Node right;
    }
}
