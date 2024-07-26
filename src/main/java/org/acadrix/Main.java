package org.acadrix;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Board board = new Board(5, null, null);
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
}
