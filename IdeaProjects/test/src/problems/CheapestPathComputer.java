package problems;

import helpers.Utils;

public class CheapestPathComputer {

    private final int[][] arr;
    private final int rows;
    private final int columns;
    private int cost;

    public static void lowestCostDemo() {
        int[][] arr = {
                {1, 2, 3, 0},
                {4, 1, 6, 20},
                {6, 7, 89, 0},
                {4, 1, 6, 209},
                {6, 7, 89, 0},
                {1, 2, 3, 0},
                {4, 1, 6, 20},
                {6, 7, 89, 0}
        };
        Utils.disp2dArr(arr);
        System.out.println("cost: " + new CheapestPathComputer(arr, Integer.MAX_VALUE).computeLowestCost());
    }

    public CheapestPathComputer(int[][] arr, int intialCost) {
        this.arr = arr;
        this.rows = arr.length;
        this.columns = arr[arr.length - 1].length;
        this.cost = intialCost;
    }

    public int computeLowestCost() {
        compSum(0, 0, 0);
        return cost;
    }

    private void compSum(int curRow, int curCol, int sum) {
        if (curRow == rows || curCol == columns)
            return;
        int total = arr[curRow][curCol] + sum;
        cost = (curRow == rows - 1 && curCol == columns - 1 && total < this.cost) ? total : cost;
        compSum(curRow, curCol + 1, total);
        compSum(curRow + 1, curCol, total);
    }
}