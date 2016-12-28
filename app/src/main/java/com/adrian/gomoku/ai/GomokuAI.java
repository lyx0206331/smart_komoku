package com.adrian.gomoku.ai;

import android.graphics.Point;
import android.util.Log;

/**
 * Created by ranqing on 2016/12/27.
 */

public class GomokuAI {
    private static final String TAG = "AI";

    private int maxLineCount;
    private int count;
    private int maxCountInLine = 5;
    private boolean[][][] wins;
    private int[] playerWin;
    private int[] aiWin;
    private int winCount = 572; //赢和可能种数

    public GomokuAI(int maxLineCount) {
        this.maxLineCount = maxLineCount;
        wins = new boolean[maxLineCount][maxLineCount][winCount];
        playerWin = new int[winCount];
        aiWin = new int[winCount];
        initCount();
    }

    /**
     * 以count为序列，列出可能的成功排列
     * wins[x][y][count]表示第count种成功排列，xy表示对应的一个点坐标
     */
    private void initCount() {
        for (int i = 0; i < maxLineCount; i++) {    //横向
            for (int j = 0; j < maxLineCount - maxCountInLine + 1; j++) {
                for (int k = 0; k < maxCountInLine; k++) {
                    wins[j + k][i][count] = true;
                }
                count++;
            }
        }

        for (int i = 0; i < maxLineCount; i++) {    //纵向
            for (int j = 0; j < maxLineCount - maxCountInLine + 1; j++) {
                for (int k = 0; k < maxCountInLine; k++) {
                    wins[i][j + k][count] = true;
                }
                count++;
            }
        }

        for (int i = 0; i < maxLineCount - maxCountInLine + 1; i++) {   //右斜
            for (int j = 0; j < maxLineCount - maxCountInLine + 1; j++) {
                for (int k = 0; k < maxCountInLine; k++) {
                    wins[i + k][j + k][count] = true;
                }
                count++;
            }
        }

        for (int i = 0; i < maxLineCount - maxCountInLine + 1; i++) {   //左斜
            for (int j = maxLineCount - 1; j > maxCountInLine - 2; j--) {
                for (int k = 0; k < maxCountInLine; k++) {
                    wins[i + k][j - k][count] = true;
                }
                count++;
            }
        }
        Log.e(TAG, "win count:" + count);
    }

    /**
     * 是否玩家胜
     *
     * @param playerPoint
     * @return
     */
    public boolean isPlayerWin(Point playerPoint) {
        for (int i = 0; i < count; i++) {
            if (wins[playerPoint.x][playerPoint.y][i]) {
                playerWin[i]++;
                aiWin[i] = maxCountInLine + 1;
                if (playerWin[i] == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否ai胜
     * @param aiPoint
     * @return
     */
    public boolean isAiWin(Point aiPoint) {
        for (int i = 0; i < count; i++) {
            if (wins[aiPoint.x][aiPoint.y][i]) {
                aiWin[i]++;
                playerWin[i] = maxCountInLine + 1;
                if (aiWin[i] == maxCountInLine) {
                    return true;
                }
            }
        }
        return false;
    }
}
