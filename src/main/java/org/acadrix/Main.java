package org.acadrix;

import org.acadrix.players.Difficulty;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Board board = new Board(scanner, 3, Main.choosePlayer(scanner, true), Main.choosePlayer(scanner, false));
            String menu = """
                    1.) Start new Game
                    2.) Show Scores
                    3.) Reset Scores
                    4.) Exit""";
            int input;
            do {
                System.out.println(menu);
                input = scanner.nextInt();
                if (input == 1) {
                    board.start();
                } else if (input == 2) {
                    board.showScores();
                } else if (input == 3) {
                    board.resetScores();
                } else if (input == 4) {
                    continue;
                }
            } while (input < 4 && input > 0);
        }
    }

    private static Difficulty choosePlayer(Scanner scanner, boolean isP1) {
        String menu = String.format("""
                Choose %s:
                1.) Player
                2.) EASY AI
                3.) Mid AI
                4.) Hard AI""", isP1 ? "P1" : "P2");
        System.out.println(menu);
        int choice = scanner.nextInt();

        if (choice == 2) {
            return Difficulty.EASY;
        } else if (choice == 3) {
            return Difficulty.MEDIUM;
        } else if (choice == 4) {
            return Difficulty.HARD;
        }
        return Difficulty.PLAYER;
    }
}
