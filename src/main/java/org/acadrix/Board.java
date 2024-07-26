package org.acadrix;

import org.acadrix.players.*;

import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private final int dimensions;
    private final int[][] board;
    private final Player p1;
    private final Player p2;
    private boolean isP1Turn;

    public Board(Scanner scanner, int dimensions, Difficulty p1, Difficulty p2) {
        this.dimensions = dimensions;

        scanner.nextLine();

        if (p1 == Difficulty.PLAYER) {
            System.out.println("Choose your Player 1 name: ");
        }

        this.p1 = switch (p1) {
            case EASY -> new Easy("P1 Easy AI", this.getBoard());
            case MEDIUM -> new Medium("P1 Mid AI", this.getBoard());
            case HARD -> new Hard("P1 Hard AI", this.getBoard());
            default -> new Player(scanner.nextLine());
        };

        if (p2 == Difficulty.PLAYER) {
            System.out.println("Choose your Player 2 name: ");
        }

        this.p2 = switch (p2) {
            case EASY -> new Easy("P2 Easy AI", this.getBoard());
            case MEDIUM -> new Medium("P2 Mid AI", this.getBoard());
            case HARD -> new Hard("P2 Hard AI", this.getBoard());
            default -> new Player(scanner.nextLine());
        };

        this.board = new int[dimensions][dimensions];
    }

    public void setup() {
        int[] row = new int[dimensions];
        Arrays.fill(row, 0);
        for (int i = 0; i < this.dimensions; i++) {
            Arrays.fill(this.board, row);
        }
    }

    public void resetScores() {
        this.p1.setScore(0);
        this.p2.setScore(0);
        System.out.println("Scores reset!\n");
    }

    public void showScores() {
        System.out.println(" Scores:");
        System.out.println("---------");
        System.out.printf("P1: %s\n", this.p1.getScore());
        System.out.printf("P2: %s\n\n", this.p2.getScore());
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
