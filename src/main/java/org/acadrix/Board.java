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
        for (int i = 0; i < this.dimensions; i++) {
            Arrays.fill(this.board[i], 0);
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

        for (int i = 1; i <= this.dimensions; i++) {
            builder.append(String.format("%2d |", i));
        }

        builder.append("\n");

        for (int i = 0; i < this.dimensions; i++) {
            builder.append((i + 1)).append(" |");
            for (int j = 0; j < this.dimensions; j++) {
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

    private boolean checkDiags(int i) {
        int count = 0;
        for (int j = 0; j < this.dimensions; j++) {
            if (this.board[j][j] == i) {
                count++;
            }
        }

        if (count == this.dimensions) return true;
        count = 0;

        int row = 0;
        for (int j = this.dimensions - 1; j >= 0; j--) {
            if (this.board[row][j] == i) {
                count++;
            }
            row++;
        }
        return count == this.dimensions;
    }

    private boolean checkGrid(int i, boolean horizontal) {
        int count = 0;
        for (int j = 0; j < this.dimensions; j++) {
            for (int k = 0; k < this.dimensions; k++) {
                if (horizontal) {
                    if (this.board[j][k] == i) {
                        count++;
                    }
                } else {
                    if (this.board[k][j] == i) {
                        count++;
                    }
                }
            }
            if (count == this.dimensions) {
                return true;
            }

            if ((j + 1) % this.dimensions == 0) {
                count = 0;
            }
        }
        if (count == this.dimensions) {
            return true;
        } else if (!horizontal) {
            return false;
        }
        return this.checkGrid(i, false);
    }

    public boolean didWin(boolean p1) {
        int val = p1 ? -1 : 1;
        return checkDiags(val) || checkGrid(val, true);
    }

    public int getDimensions() {
        return this.dimensions;
    }

    public int[][] getBoard() {
        return board;
    }
}
