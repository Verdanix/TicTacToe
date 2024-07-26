package org.acadrix.players;

public class Player {
    private int score = 0;
    private final String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
}
