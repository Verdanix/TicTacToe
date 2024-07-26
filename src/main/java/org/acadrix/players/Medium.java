package org.acadrix.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Medium extends Easy {
    private int[] bestMove;

    public Medium(String name, boolean p1, int[][] board) {
        super(name, p1, board);
    }

    private int score(boolean maximizingPlayer) {
        if (this.didWin(maximizingPlayer)) {
            return 10;
        } else if (this.didWin(!maximizingPlayer)) {
            return -10;
        }
        return 0;
    }

    private List<int[]> getIndexes() {
        List<int[]> args = new ArrayList<>();
        for (int i = 0; i < this.getDimensions(); i++) {
            for (int j = 0; j < this.getDimensions(); j++) {
                if (this.board[i][j] == 0) {
                    args.add(new int[]{i, j});
                }
            }
        }
        return args;
    }

    private int miniMax(int depth, boolean isP1) {
       if (this.didWin(isP1)) {
           return 10;
       }else if (this.didWin(!isP1)) {
           return -10;
        }
        if (!canMove()) {
            return 0;
        }

        List<int[]> pointsAvailable = this.getIndexes();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        int playerNum = isP1 ? -1 : 1;

        for (int i = 0; i < pointsAvailable.size(); i++) {
            int[] point = pointsAvailable.get(i);
            board[point[0]][point[1]] = playerNum;
            if (isP1) {
                int currentScore = miniMax(depth + 1, false);
                max = Math.max(currentScore, max);
                if (currentScore >= 0) {
                    if (depth == 0) {
                        this.bestMove = point;
                    }
                }
                if (currentScore == 10) {
                    board[point[0]][point[1]] = 0;
                    break;
                }

                if (i == pointsAvailable.size() - 1 && max < 0) {
                    if (depth == 0) {
                        this.bestMove = point;
                    }
                }
            } else {
                int currentScore = miniMax(depth + 1, true);
                min = Math.min(currentScore, min);
                if (currentScore == -10) {
                    board[point[0]][point[1]] = 0;
                    break;
                }
                if (currentScore <= 0) {
                    if (depth == 0) {
                        this.bestMove = point;
                    }
                }
                if (currentScore == 10) {
                    board[point[0]][point[1]] = 0;
                    break;
                }
            }
            board[point[0]][point[1]] = 0;
        }
        System.out.println(max + " " + min);
        return isFirstPlayer() ? max : min;
    }

    private int miniMaxAlgorithm(int depth, boolean maximizingPlayer) {
        int score = this.score(maximizingPlayer);

        if (score != 0) {
            return score;
        }
        if (!this.canMove()) {
            return 0;
        }
        int best;
        int currentScore;
        if (maximizingPlayer) {
            best = Integer.MIN_VALUE;
            for (int i = 0; i < this.getDimensions(); i++) {
                for (int j = 0; j < this.getDimensions(); j++) {
                    if (this.board[i][j] == 0) {
                        this.board[i][j] = -1;
                        currentScore = miniMaxAlgorithm(depth + 1, false);
                        best = Math.max(currentScore, best);
                        this.board[i][j] = 0;
                        if (currentScore >= 0) {
                            if (depth == 0) {
                                this.bestMove = new int[]{i + 1, j + 1};
                            }
                        }
                        if (currentScore == 10) {
                            break;
                        }
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (int i = 0; i < this.getDimensions(); i++) {
                for (int j = 0; j < this.getDimensions(); j++) {
                    if (this.board[i][j] == 0) {
                        this.board[i][j] = 1;
                        currentScore = miniMaxAlgorithm(depth + 1, true);
                        best = Math.min(currentScore, best);
                        this.board[i][j] = 0;
                        if (currentScore == -10) {
                            break;
                        }
                        if (currentScore >= 0) {
                            if (depth == 0) {
                                this.bestMove = new int[]{i + 1, j + 1};
                            }
                        }
                    }
                }
            }
        }
        return best;
    }

    private int[] getBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int x = -1;
        int y = -1;

        for (int i = 0; i < this.getDimensions(); i++) {
            for (int j = 0; j < this.getDimensions(); j++) {
                if (this.board[i][j] == 0) {
                    this.board[i][j] = this.isFirstPlayer() ? -1 : 1;

                    int moveVal = miniMaxAlgorithm(0, !this.isFirstPlayer());
                    this.board[i][j] = 0;

                    if (moveVal > bestVal) {
                        y = i + 1;
                        x = j + 1;
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.println("BEST MOVE: " + bestVal);
        return new int[]{x, y};
    }

    private boolean checkDiags(int i) {
        int count = 0;
        for (int j = 0; j < this.getDimensions(); j++) {
            if (this.board[j][j] == i) {
                count++;
            }
        }

        if (count == this.getDimensions()) return true;
        count = 0;

        int row = 0;
        for (int j = this.getDimensions() - 1; j >= 0; j--) {
            if (this.board[row][j] == i) {
                count++;
            }
            row++;
        }
        return count == this.getDimensions();
    }

    private boolean checkGrid(int i, boolean horizontal) {
        int count = 0;
        for (int j = 0; j < this.getDimensions(); j++) {
            for (int k = 0; k < this.getDimensions(); k++) {
                if (horizontal) {
                    if (this.board[j][k] == i) {
                        count++;
                    }
                } else {
                    if (this.board[k][j] == i) {
                        count++;
                    }
                }

                if (count == this.getDimensions()) {
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

    public boolean didWin(boolean p1) {
        int val = p1 ? -1 : 1;
        return checkDiags(val) || checkGrid(val, true);
    }

    public boolean canMove() {
        return Arrays.stream(this.board)
                .flatMapToInt(Arrays::stream)
                .anyMatch(e -> e == 0);
    }

    @Override
    public int getInputX(Scanner scanner) {
        this.miniMax(0, this.isFirstPlayer());
        return this.bestMove[0];
    }

    @Override
    public int getInputY(Scanner scanner) {
        return this.bestMove[1];
    }
}
