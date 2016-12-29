package com.adrian.gomoku.ai;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

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
    private int winCount = 572; //胜的可能种数

    private int[][] playerScore;
    private int[][] aiScore;
    private int maxScore;
    private int u, v;

    public GomokuAI(int maxLineCount) {
        this.maxLineCount = maxLineCount;
        wins = new boolean[maxLineCount][maxLineCount][winCount];
        playerWin = new int[winCount];
        aiWin = new int[winCount];
        playerScore = new int[maxLineCount][maxLineCount];
        aiScore = new int[maxLineCount][maxLineCount];
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

    public Point getBestPoint(ArrayList whiteArray, ArrayList blackArray) {
        for (int i = 0; i < maxLineCount; i++) {
            for (int j = 0; j < maxLineCount; j++) {
                Point p = new Point(i, j);
                if (!(whiteArray.contains(p) || blackArray.contains(p))) { //查找空闲点
                    for (int k = 0; k < winCount; k++) {
                        if (wins[i][j][k]) {
                            //遍历玩家赢法数组，给空闲点赋分（计算机拦截）
                            if (playerWin[k] == 1) {
                                playerScore[i][j] += 200;
                            } else if (playerWin[k] == 2) {
                                playerScore[i][j] += 400;
                            } else if (playerWin[k] == 3) {
                                playerScore[i][j] += 2000;
                            } else if (playerWin[k] == 4) {
                                playerScore[i][j] += 10000;
                            }

                            //遍历AI赢法数组，给空闲点赋分（计算机进攻）
                            if (aiWin[k] == 1) {
                                aiScore[i][j] += 220;
                            } else if (aiWin[k] == 2) {
                                aiScore[i][j] += 420;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
