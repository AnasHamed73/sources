package problems;

import helpers.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Robot {

    public static int robot() {
        Scanner s = new Scanner(System.in);
        Coordinate startCo = null;
        int rows = s.nextInt();
        int cols = s.nextInt();
        char[][] maze = new char[rows][cols];
        int matches = 0;

        s.nextLine();
        for (int i = 0; i < rows; i++) {
            String ip = s.nextLine();
            maze[i] = ip.toCharArray();
            int start = ip.indexOf("S");
            if (start >= 0)
                startCo = new Coordinate(i, start);
        }
        char[] sequence = s.nextLine().toCharArray();

        Iterable<Character[]> combos = Utils.calcPermutations(new Character[]{'U', 'D', 'L', 'R'});
        Coordinate finalStartCo = startCo;
        for (Character[] combo : combos) {
            if (leads(combo, sequence, maze, finalStartCo))
                matches++;
        }
        System.out.println(matches);
        return matches;
    }

    private static boolean leads(Character[] combo, char[] sequence,
                                 char[][] maze, Coordinate startCo) {
        Map<Character, Character> mapping = mapping(combo);
        int seqIndex = 0;
        Coordinate current = startCo;
        Coordinate nextCo = null;
        while (seqIndex < sequence.length) {
            nextCo = next(sequence[seqIndex++], current, mapping, maze);
            if (nextCo.getRow() >= maze.length || nextCo.getColumn() >= maze[0].length
                    || nextCo.getRow() < 0 || nextCo.getColumn() < 0)
                return false;
            char nextChar = maze[nextCo.getRow()][nextCo.getColumn()];
            if (nextChar == '#') {
                return false;
            }
            if (nextChar == 'E') {
                return true;
            }
            current = nextCo;
        }
        return false;
    }

    private static Coordinate next(char seqChar, Coordinate current, Map<Character, Character> mapping, char[][] maze) {
        Character dir = mapping.get(seqChar);
        switch (dir) {
            case 'U':
                return new Coordinate(current.getRow() - 1, current.getColumn());
            case 'D':
                return new Coordinate(current.getRow() + 1, current.getColumn());
            case 'L':
                return new Coordinate(current.getRow(), current.getColumn() - 1);
            case 'R':
                return new Coordinate(current.getRow(), current.getColumn() + 1);
        }
        return null;
    }

    private static Map<Character, Character> mapping(Character[] combo) {
        Map<Character, Character> mapping = new HashMap<>(4);
        for (int i = 0; i < 4; i++)
            mapping.put(String.valueOf(i).charAt(0), combo[i]);
        return mapping;
    }

    private static class Coordinate {
        private int row;
        private int column;

        public Coordinate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        @Override
        public String toString() {
            return "row: " + row + "; column: " + column;
        }
    }
}
