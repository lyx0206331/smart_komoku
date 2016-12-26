package com.adrian.gomoku.ai;

/**
 * Created by ranqing on 2016/12/27.
 */

public class GomokuAI {
    private int maxLineCount;
    private int count;
    private int maxCountInLine = 5;
    private boolean[][][] wins;

    public GomokuAI(int maxLineCount) {
        this.maxLineCount = maxLineCount;
        wins = new boolean[maxLineCount][maxLineCount][572];
    }

    private void initCount() {
        for (int i = 0; i < maxLineCount; i++) {
            for (int j = 0; j < maxCountInLine - maxCountInLine + 1; j++) {
                for (int k = 0; k < maxCountInLine; k++) {
                    wins[j + k][i][count] = true;
                }
                count++;
            }
        }
    }
}
