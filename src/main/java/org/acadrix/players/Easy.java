package org.acadrix.players;

import java.util.Random;
import java.util.Scanner;

public class Easy extends Player {
    private final Random random = new Random();
    private int dimensions;
    public Easy(String name, int[][] board) {
        super(name);
        this.dimensions = board.length;
    }

    @Override
    public int getInputX(Scanner scanner) {
        return random.nextInt(1, this.dimensions+1);
    }

    @Override
    public int getInputY(Scanner scanner) {
        return random.nextInt(1, this.dimensions+1);
    }
}
