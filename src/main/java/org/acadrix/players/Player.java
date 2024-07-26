package org.acadrix.players;

import java.util.Scanner;

public class Player {
    private final String playerName;
    private final boolean isFirstPlayer;
    private int score = 0;
    public int[][] board;

    public Player(String playerName, boolean p1, int[][] board) {
        this.playerName = playerName;
        this.isFirstPlayer = p1;
        this.board = board;
    }

    public int getDimensions() {
        return this.board.length;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getInputX(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Please enter the x-coordinate of where you want to move: ");
        return scanner.nextInt();
    }

    public int getInputY(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Please enter the y-coordinate of where you want to move: ");
        return scanner.nextInt();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void incremenetScore() {
        this.score += 20;
    }
}
