package org.acadrix;

import org.acadrix.players.Difficulty;

import java.util.Arrays;

public class Board {
    private final int dimensions;
    private final int[][] board;

    public Board(int dimensions, Difficulty p1, Difficulty p2) {
        this.dimensions = dimensions;
        this.board = new int[dimensions][dimensions];
    }

    public void setup() {
        int[] row = new int[dimensions];
        Arrays.fill(row, 0);
        for (int i = 0; i < this.dimensions; i++) {
            Arrays.fill(this.board, row);
        }
    }

    public void print() {
        StringBuilder builder = new StringBuilder("  |");

        for (int i = 1; i <= dimensions; i++) {
            builder.append(String.format("%2d |", i));
        }

        builder.append("\n");

        for (int i = 0; i < dimensions; i++) {
            builder.append((i + 1)).append(" |");
            for (int j = 0; j < dimensions; j++) {
                int cellContent = this.board[i][j];
                char displayChar = cellContent == -1 ? 'X' : cellContent == 0 ? ' ' : 'O';
                builder.append(String.format("%2c |", displayChar));
            }
            builder.append("\n");
        }

        System.out.println(builder);
    }

    public void start() {
        this.setup();
        this.print();
    }

    public int getDimensions() {
        return dimensions;
    }

    public int[][] getBoard() {
        return board;
    }
}
