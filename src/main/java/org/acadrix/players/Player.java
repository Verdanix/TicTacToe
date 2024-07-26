package org.acadrix.players;

import java.util.Scanner;

public class Player {
    private final String playerName;
    private int score = 0;

    public Player(String playerName) {
        this.playerName = playerName;
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
