package com.adrian.gomoku.ai

import android.graphics.Point
import android.util.Log
import com.adrian.gomoku.BuildConfig

/**
 * author:RanQing
 * date:2020/1/27 0027 0:17
 * description:
 **/
class KGomokuAI(private val maxLineCount: Int = 15) {

    companion object {
        const val TAG = "GomokuAi"
    }

    private var maxCountInLine = 5
    private var winCount = 0
    private var playerWin: IntArray
    private var aiWin: IntArray
    private var wins = Array(maxLineCount) { Array(maxLineCount) { BooleanArray(572) } }

    init {
        calcMaxWinCount()
        playerWin = IntArray(winCount)
        aiWin = IntArray(winCount)
    }

    /**
     * 以count为序列，列出可能的成功排列
     * wins[x][y][count]表示第count种成功排列，xy表示对应的一个点坐标
     */
    private fun calcMaxWinCount() {
        winCount = 0
        //横向获胜计数
        for (i in 0 until maxLineCount) {
            for (j in 0..maxLineCount - maxCountInLine) {
                for (k in 0 until maxCountInLine) {
                    wins[j + k][i][winCount] = true
                }
                winCount++
            }
        }
        //纵向获胜计数
        for (i in 0 until maxLineCount) {
            for (j in 0..maxLineCount - maxCountInLine) {
                for (k in 0 until maxCountInLine) {
                    wins[i][j + k][winCount] = true
                }
                winCount++
            }
        }
        //右斜获胜计数
        for (i in 0..maxLineCount - maxCountInLine) {
            for (j in 0..maxLineCount - maxCountInLine) {
                for (k in 0 until maxCountInLine) {
                    wins[i + k][j + k][winCount] = true
                }
                winCount++
            }
        }
        //左斜获胜计数
        for (i in 0..maxLineCount - maxCountInLine) {
            for (j in maxLineCount - 1 downTo maxCountInLine - 1) {
                for (k in 0 until maxCountInLine) {
                    wins[i + k][j - k][winCount] = true
                }
                winCount++
            }
        }

        logE("winCount:$winCount")
    }

    fun restart() {
        playerWin = IntArray(winCount)
        aiWin = IntArray(winCount)
    }

    /**
     * 是否玩家胜
     *
     * @param playerPoint
     * @return
     */
    fun isPlayerWin(playerPoint: Point): Boolean {
        for (i in 0 until winCount) {
            if (wins[playerPoint.x][playerPoint.y][i]) {
                playerWin[i]++
                aiWin[i] = maxCountInLine + 1
                if (playerWin[i] == maxCountInLine) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 是否ai胜
     * @param aiPoint
     * @return
     */
    fun isAiWin(aiPoint: Point): Boolean {
        for (i in 0 until winCount) {
            if (wins[aiPoint.x][aiPoint.y][i]) {
                aiWin[i]++
                playerWin[i] = maxCountInLine + 1
                if (aiWin[i] == maxCountInLine) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 获取最优落点
     *
     * @param whiteArray
     * @param blackArray
     * @return
     */
    fun getBestPoint(whiteArray: ArrayList<Point>, blackArray: ArrayList<Point>): Point {
        val playerScore = Array(maxLineCount) { IntArray(maxLineCount) }
        val aiScore = Array(maxLineCount) { IntArray(maxLineCount) }
        var maxScore = 0
        var u = 0
        var v = 0
        for (i in 0 until maxLineCount) {
            for (j in 0 until maxLineCount) {
                val p = Point(i, j)
                //查找空闲点
                if (!(whiteArray.contains(p) || blackArray.contains(p))) {
                    for (k in 0 until winCount) {
                        if (wins[i][j][k]) {
                            //遍历玩家赢法数组，给空闲点赋分（计算机拦截）
                            when (playerWin[k]) {
                                1 -> playerScore[i][j] += 200
                                2 -> playerScore[i][j] += 400
                                3 -> playerScore[i][j] += 2000
                                4 -> playerScore[i][j] += 10000
                            }

                            //遍历AI赢法数组，给空闲点赋分（计算机进攻）
                            when (aiWin[k]) {
                                1 -> aiScore[i][j] += 220
                                2 -> aiScore[i][j] += 420
                                3 -> aiScore[i][j] += 3000
                                4 -> aiScore[i][j] += 20000
                            }
                        }
                    }
                }
            }
        }

        for (i in 0 until maxLineCount) {
            for (j in 0 until maxLineCount) {
                if (playerScore[i][j] > maxScore) {
                    maxScore = playerScore[i][j]
                    u = i
                    v = j
                } else if (playerScore[i][j] == maxScore) {
                    if (aiScore[i][j] > aiScore[u][v]) {
                        u = i
                        v = j
                    }
                }
                if (aiScore[i][j] > maxScore) {
                    maxScore = aiScore[i][j]
                    u = i
                    v = j
                } else if (aiScore[i][j] == maxScore) {
                    if (playerScore[i][j] > playerScore[u][v]) {
                        u = i
                        v = j
                    }
                }
            }
        }
        return Point(u, v)
    }

    fun logE(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg)
        }
    }
}