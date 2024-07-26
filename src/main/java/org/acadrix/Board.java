package org.acadrix;

import org.acadrix.players.*;

import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private final int dimensions;
    private final Difficulty p1Difficulty;
    private final Difficulty p2Difficulty;
    private final Player p1;
    private final Player p2;
    private int[][] board;
    private boolean isP1Turn = true;

    public Board(Scanner scanner, int dimensions, Difficulty p1, Difficulty p2) {
        this.p1Difficulty = p1;
        this.p2Difficulty = p2;
        this.dimensions = dimensions;
        this.board = new int[dimensions][dimensions];

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
        System.out.printf("%s: %s\n", this.p1.getPlayerName(), this.p1.getScore());
        System.out.printf("%s: %s\n\n", this.p2.getPlayerName(), this.p2.getScore());
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

    public void start(Scanner scanner) {
        this.board = new int[this.dimensions][this.dimensions];
        this.setup();

        do {
            Player player = this.isP1Turn ? p1 : p2;
            Difficulty difficulty = this.isP1Turn ? p1Difficulty : p2Difficulty;
            System.out.printf("%s's turn\n", player.getPlayerName());
            this.print();

            int x = player.getInputX(scanner);
            int y = player.getInputY(scanner);

            if (x > dimensions || x < 1) {
                System.out.println("You need a valid x-coordinate from 1 - " + this.dimensions + ".");
                continue;
            }
            if (y > dimensions || y < 1) {
                System.out.println("You need a valid y-coordinate from 1 - " + this.dimensions + ".");
                continue;
            }
            if (this.board[x - 1][y - 1] != 0 && difficulty.equals(Difficulty.PLAYER)) {
                System.out.println("That spot is occupied already.");
                continue;
            }

            this.board[y - 1][x - 1] = this.isP1Turn ? -1 : 1;
            if (didWin(this.isP1Turn)) {
                break;
            } else if (!canMove()) {
                System.out.println("Draw");
                return;
            }
            this.isP1Turn = !this.isP1Turn;
        } while (true);
        this.print();
        Player player = this.isP1Turn ? p1 : p2;
        System.out.printf("Congratulations! %s has won! That is 20 points added to their score.\n", player.getPlayerName());

        if (this.isP1Turn) {
            this.p1.incremenetScore();
            return;
        }
        this.p2.incremenetScore();

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

                if (count == this.dimensions) {
                    return true;
                }
            }
            count = 0;
        }
        if (!horizontal) {
            return false;
        }
        return this.checkGrid(i, false);
    }

    public boolean canMove() {
        return Arrays.stream(this.board)
                .flatMapToInt(Arrays::stream)
                .anyMatch(e -> e == 0);
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
