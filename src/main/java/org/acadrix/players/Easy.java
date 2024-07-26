package org.acadrix.players;

import java.util.Random;
import java.util.Scanner;

public class Easy extends Player {
    private final Random random = new Random();

    public Easy(String name, boolean p1,int[][] board) {
        super(name, p1, board);
    }

    @Override
    public int getInputX(Scanner scanner) {
        return random.nextInt(1, this.getDimensions()+1);
    }

    @Override
    public int getInputY(Scanner scanner) {
        return random.nextInt(1, this.getDimensions()+1);
    }
}
