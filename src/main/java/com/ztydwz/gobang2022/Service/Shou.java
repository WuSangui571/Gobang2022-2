package main.java.com.ztydwz.gobang2022.Service;//

import java.util.Arrays;

import static main.java.com.ztydwz.gobang2022.Model.Static.Map;
import static main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen;

/**
 * @author 寿豪泽
 */
public class Shou {
    /**
     * 已完成编写
     *
     * @param map 保存棋盘信息的数组，有0，1，2 三种值，1代表黑棋，2代表白棋，0代表空
     * @param MAX_ROW 一般为15行
     * @param MAX_COLUMN 一般为15列
     * @param enemyColor 对方执棋颜色，有1和2两个值，1代表黑，2代表白
     * @param aiColor 我方执棋颜色，有1和2两个值，1代表黑，2代表白
     * @return 返回一个坐标，即我发打出的棋的位置
     */
    int deep = 4;
    static boolean isDoForbidden = false;

    public int[] getAnswer(int[][] map, int MAX_ROW, int MAX_COLUMN, int enemyColor, int aiColor) {
        //return minmax(deep,map,aiColor,Integer.MIN_VALUE,Integer.MAX_VALUE);

        int[] answer = new int[2];

        //正常的五元组算法
        /*int[] temp = getMaxGradeAndPlace(map, aiColor);*/

        // hhh
        int[] alpha= new int[3];
        alpha[0] = Integer.MIN_VALUE;
        int[] beta = new int[3];
        beta[0] = Integer.MAX_VALUE;
        int[] temp = alpha_beta(0,map,aiColor,alpha,beta);


        /*int i = temp[1];
        int j = temp[2];
        int liveThree = this.newLiveThree(map, aiColor,i,j);
        int jumpLiveThree = this.jumpLiveThree(map,aiColor,i,j);
        int liveFour = this.liveFour(map,aiColor,i,j);
        int longSix = this.longSix(map,aiColor,i,j);
        int rushFour = this.rushFour(map,aiColor,i,j);
        int jumpFour = this.jumpFour(map,aiColor,i,j);
        boolean flag = (longSix == 1) || (liveThree + jumpLiveThree == 1 && rushFour + jumpFour + liveFour == 2)
                || (liveThree + jumpLiveThree == 2 && rushFour + jumpFour + liveFour == 1) ||
                (liveThree + jumpLiveThree == 2) || (liveFour + rushFour + jumpFour == 2);
        if (flag && aiColor == 1){
            int[][] tempMap = new int[map.length][];
            for (int k = 0; k < map.length; k++) {
                tempMap[k] = map[k].clone();
            }
            tempMap[i][j] = 999;
            temp = getMaxGradeAndPlace(tempMap,aiColor);
            System.out.println("禁手规避了！");
        }*/


        // 高级算法，不完善
        //int[] temp = getScopeMaxGradeAndPlace(map,aiColor);


        // 极大极小值算法，基于正常的五元组算法
        //int[] temp = f(map,aiColor);


        answer[0] = temp[1];
        answer[1] = temp[2];
        return answer;
    }

    public int[] fiveNda(int[][] map, int N) {
        return null;
    }

    public int[] enemyFiveNda(int[] arr) {

        return null;
    }

    private int[] f(int[][] board, int aiColor) {
        int begin = 0;
        int end = board.length;
        int[] answer = new int[2];
        int grade1 = Integer.MIN_VALUE;
        int grade2 = Integer.MAX_VALUE;
        int grade3 = Integer.MIN_VALUE;
        int grade4 = Integer.MAX_VALUE;

        for (int i = begin; i < end; i++) {
            for (int j = begin; j < end; j++) {
                if (isEmpty(board, i, j)) {
                    int[][] tempMap = new int[board.length][];
                    for (int k = 0; k < end; k++) {
                        tempMap[k] = board[k].clone();
                    }
                    tempMap[i][j] = aiColor;

                    //对方下棋-----
                    for (int k = begin; k < end; k++) {
                        for (int l = begin; l < end; l++) {
                            if (isEmpty(tempMap, k, l)) {
                                int[][] tempMap2 = new int[board.length][];
                                for (int m = 0; m < board.length; m++) {
                                    tempMap2[m] = board[m].clone();
                                }
                                tempMap2[k][l] = 3 - aiColor;

                                // 我方下棋---
                                for (int m = begin; m < end; m++) {
                                    for (int n = begin; n < end; n++) {
                                        if (isEmpty(tempMap2, m, n)) {
                                            int[][] tempMap3 = new int[board.length][];
                                            for (int o = 0; o < board.length; o++) {
                                                tempMap3[o] = board[o].clone();
                                            }
                                            tempMap3[m][n] = aiColor;

                                            // 对方下棋begin---
                                            for (int o = begin; o < end; o++) {
                                                for (int p = begin; p < end; p++) {
                                                    if (isEmpty(tempMap3, o, p)) {
                                                        int[][] tempMap4 = new int[board.length][];
                                                        for (int q = 0; q < board.length; q++) {
                                                            tempMap4[q] = board[q].clone();
                                                        }
                                                        int temp = getGrade(tempMap4, 3 - aiColor, o, p);
                                                        System.out.println("玩命计算中...");
                                                        if (temp < grade4) {
                                                            grade4 = temp;
                                                        }
                                                    }
                                                }
                                            }
                                            // 对方下棋over---
                                            if (grade4 > grade3) {
                                                grade3 = grade4;
                                            }
                                        }
                                    }
                                }
                                // 我方下棋over---
                                if (grade3 < grade2) {
                                    grade2 = grade3;
                                }
                            }
                        }
                    }
                    //对方下棋over-----
                    if (grade2 > grade1) {
                        grade1 = grade3;
                        answer[0] = i;
                        answer[1] = j;
                    }
                }
            }

        }
        return answer;
    }

    private int[] minmax(int[][] board, int aiColor, int depth, int alpha, int beta) {
        if (depth == 0) {

        }
        return null;
    }

    private int getGrade(int[][] board, int aiColor) {
        int count = 0;

        // .....
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        //.
        //.
        //.
        //.
        //.
        for (int i = 0; i < board.length - 4; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i + k][j];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        //.
        // .
        //  .
        //   .
        //    .
        for (int i = 0; i < board.length - 4; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i + k][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }
        //    .
        //   .
        //  .
        // .
        //.
        for (int i = 4; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i - k][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        return count;
    }

    /**
     * 这个方法是为了求某一个点位的分数
     *
     * @param board   此时的棋盘,1代表黑棋，2代表白棋，0代表空
     * @param aiColor 我方执棋颜色，有1和2两个值，1代表黑，2代表白
     * @param x       是本次下棋的行数
     * @param y       是本次下棋的列数
     * @return 返回该点位的分数
     */
    public int getGrade(int[][] board, int aiColor, int x, int y) {
        int enemyColor = 3 - aiColor;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }

        tempMap[x][y] = aiColor;



        int count = 0;

        // .....
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = tempMap[i][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        //.
        //.
        //.
        //.
        //.
        for (int i = 0; i < board.length - 4; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = tempMap[i + k][j];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        //.
        // .
        //  .
        //   .
        //    .
        for (int i = 0; i < board.length - 4; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = tempMap[i + k][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }
        //    .
        //   .
        //  .
        // .
        //.
        for (int i = 4; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = tempMap[i - k][j + k];
                }
                count += getFiveGrade(aiColor, nums);
            }
        }

        return count;
    }
    /*private int getRangeGrade(int[][] board,int aiColor,int[] range){
        // 5×5 (0,0)(4,4)/(4,5)
        // 9×9 (0,0)(8×8)
        int x1 = range[0];
        int y1 = range[1];
        int x2 = range[2];
        int y2 = range[3];
        int count = 0;
        // .....
        for (int i = x1; i <= x2 ; i++) {
            for (int j = y1; j <= y2 - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i][j + k];
                }
                count += getFiveGrade(aiColor,nums);
            }
        }

        //.
        //.
        //.
        //.
        //.
        for (int i = x1; i <= x2 - 4; i++) {
            for (int j = y1; j <= y2; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i + k][j];
                }
                count += getFiveGrade(aiColor,nums);
            }
        }

        //.
        // .
        //  .
        //   .
        //    .
        for (int i = x1; i <= x1 - 4; i++) {
            for (int j = y1; j <= y2 - 4; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i + k][j + k];
                }
                count += getFiveGrade(aiColor,nums);
            }
        }
        //    .
        //   .
        //  .
        // .
        //.
        for (int i = x1; i < x2 - 4; i++) {
            for (int j = y1 + 4; j <= y2; j++) {
                int[] nums = new int[5];
                for (int k = 0; k < 5; k++) {
                    nums[k] = board[i + k][j - k];
                }
                count += getFiveGrade(aiColor,nums);
            }
        }

        return count;
    }*/

    private int getFiveGrade(int aiColor, int[] nums) {
        float key = 1.2F;
        int enemyColor = 3 - aiColor;
        boolean existAi = false;
        int countAi = 0;
        boolean existEnemy = false;
        int countEnemy = 0;
        int empty = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == aiColor) {
                existAi = true;
                countAi++;
            } else if (nums[i] == enemyColor) {
                existEnemy = true;
                countEnemy++;
            } else {
                empty++;
            }
        }
        if (existAi && existEnemy) {
            return 0;
        }
        if (empty == 5) {
            return 0;
        }
        if (existAi) {
            return pow(10, countAi);
        }

        int enemyGrade = pow(10, countEnemy);
        return (int) -(key * enemyGrade);
    }

    private int pow(int a, int n) {
        int temp = a;
        while (n != 1) {
            a *= temp;
            n--;
        }
        return a;
    }

    /**
     * @param deep    深度，为偶数
     * @param board   此时的棋盘,1代表黑棋，2代表白棋，0代表空
     * @param aiColor 我方执棋颜色，有1和2两个值，1代表黑，2代表白
     * @return 返回经过deep层计算后的，某一点的分数
     */
    private int[] minmax(int deep, int[][] board, int aiColor, int alpha, int beta) {
        if (deep == 0) {
            return getMaxGradeAndPlace(board, aiColor);
        }
        // Max层
        if (deep % 2 == 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    int[][] tempBoard = board.clone();
                    if (isEmpty(tempBoard, i, j)) {
                        deep -= 1;
                        minmax(deep, tempBoard, aiColor, alpha, beta);
                    }
                }
            }

            // Min层
        } else {

        }

        return null;
    }

    /**
     * 查询棋盘中某一点是否为空（已完成编写）
     *
     * @param board 此时的棋盘,1代表黑棋，2代表白棋，0代表空
     * @param x     是本次下棋的行数
     * @param y     是本次下棋的列数
     * @return 返回这个点位是否有棋
     */
    private boolean isEmpty(int[][] board, int x, int y) {
        return board[x][y] == 0;
    }

    /**
     * 查询整个棋盘中最高分数的点及其坐标(已完成编写)
     *
     * @param board   此时的棋盘,1代表黑棋，2代表白棋，0代表空
     * @param aiColor 我方执棋颜色，有1和2两个值，1代表黑，2代表白
     * @return 返回这个棋盘中，最优的一个点的分数和坐标,[0]是最优分数，[1][2]是坐标
     */
    private int[] getMaxGradeAndPlace(int[][] board, int aiColor) {
        int[] answer = new int[3];
        int max = Integer.MIN_VALUE;
        int temp;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (isEmpty(board, i, j)) {
                    temp = getGrade(board, aiColor, i, j);
                    if (!ifAllowForbiddenHandOpen) {
                        int liveThree = this.newLiveThree(Map, aiColor, i, j);
                        int jumpLiveThree = this.jumpLiveThree(Map, aiColor, i, j);
                        int liveFour = this.liveFour(Map, aiColor, i, j);
                        int longSix = this.longSix(Map, aiColor, i, j);
                        int rushFour = this.rushFour(Map, aiColor, i, j);
                        if (longSix == 1 || liveThree + jumpLiveThree >= 2 || liveFour + rushFour >= 2) {
                            temp = Integer.MIN_VALUE;
                        }
                    }
                    if (temp > max) {
                        max = temp;
                        answer[1] = i;
                        answer[2] = j;
                    }
                }
            }
        }
        return answer;
    }
    /**
     * 获取小的影响范围(已完成)
     * @param x x坐标
     * @param y y坐标
     * @return 数组4个值，即两个坐标点
     */
    /*
    private int[] getScope(int x,int y){
        int[] answer = new int[4];

        int x1 = x - 4;
        if (x1 < 0){
            x1 = 0;
        }
        int y1 = y -4;
        if (y1 < 0){
            y1 = 0;
        }
        int x2 = x + 4;
        if (x2 > 14){
            x2 = 14;
        }
        int y2 = y + 4;
        if (y2 > 14){
            y2 = 14;
        }

        answer[0] = x1;
        answer[1] = y1;
        answer[2] = x2;
        answer[3] = y2;
        return answer;
    }*/
    /*private int[] getScopeMaxGradeAndPlace(int[][] board,int aiColor){
        int[] answer = new int[3];
        int max = Integer.MIN_VALUE;
        int temp;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (isEmpty(board,i,j)){
                    int[] range = getScope(i, j);
                    temp = getRangeGrade(board,aiColor,range);
                    if (temp > max){
                        max = temp;
                        answer[1] = i;
                        answer[2] = j;
                    }
                }
            }
        }
        return answer;
    }*/

    /**
     * @param board   棋盘 1黑2白0空
     * @param aiColor ai颜色
     * @param x       横坐标
     * @param y       纵坐标
     * @return 下了这个点之后产生的活三数 [0,4]
     */
    public int liveThree(int[][] board, int aiColor, int x, int y) {
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0...0
         *
         */
        {
            int a = y - 3;
            if (a < 0) {
                a = 0;
            }
            int b = y + 3;
            if (b > 14) {
                b = 14;
            }
            int k = y - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == 0) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 3;
            if (a < 0) {
                a = 0;
            }
            int b = x + 3;
            if (b > 14) {
                b = 14;
            }
            int k = x - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == 0) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 3; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 3; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == 0) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }

                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 3; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 3; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == 0) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                y1--;
            }
        }
        return count;
    }

    /**
     * @param board   棋盘 1黑2白0空
     * @param aiColor ai颜色
     * @param x       横坐标
     * @param y       纵坐标
     * @return 下了这个点之后产生的活四数 [0,4]
     */
    public int liveFour(int[][] board, int aiColor, int x, int y) {
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0....0
         */
        {
            int a = y - 4;
            if (a < 0) {
                a = 0;
            }
            int b = y + 4;
            if (b > 14) {
                b = 14;
            }
            int k = y - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }

            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 4;
            if (a < 0) {
                a = 0;
            }
            int b = x + 4;
            if (b > 14) {
                b = 14;
            }
            int k = x - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                y1--;
            }
        }
        return count;
    }

    public int jumpLiveThree(int[][] board, int aiColor, int x, int y) {
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0....0
         */
        {
            int a = y - 4;
            if (a < 0) {
                a = 0;
            }
            int b = y + 4;
            if (b > 14) {
                b = 14;
            }
            int k = y - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == 0) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == 0) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 4;
            if (a < 0) {
                a = 0;
            }
            int b = x + 4;
            if (b > 14) {
                b = 14;
            }
            int k = x - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == 0) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == 0) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == 0) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == 0) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == 0) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }

                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == 0) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                y1--;
            }
        }
        return count;
    }

    public int newLiveThree(int[][] board, int aiColor, int x, int y) {
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0...0
         *
         */
        {
            int a = y - 4;
            if (a < 0) {
                a = 0;
            }
            int b = y + 4;
            if (b > 14) {
                b = 14;
            }
            for (int i = a; i <= y; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == 0) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == 0) {
                                            if (tempMap[x][i + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 4;
            if (a < 0) {
                a = 0;
            }
            int b = x + 4;
            if (b > 14) {
                b = 14;
            }
            for (int i = a; i <= x; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == 0) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == 0) {
                                            if (tempMap[i + 6][y] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == 0) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == 0) {
                                            if (tempMap[i + 6][i + y - x + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == 0) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == 0) {
                                            if (tempMap[i + 6][y1 - 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                y1--;
            }
        }
        return count;
    }

    public int longSix(int[][] board, int aiColor, int x, int y) {
        int count = 1;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * ......
         */
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 9; j++) {
                if (tempMap[i][j] == aiColor) {
                    if (tempMap[i][j + 1] == aiColor) {
                        if (tempMap[i][j + 2] == aiColor) {
                            if (tempMap[i][j + 3] == aiColor) {
                                if (tempMap[i][j + 4] == aiColor) {
                                    if (tempMap[i][j + 5] == aiColor) {
                                        return count;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * .
         * .
         * .
         * .
         * .
         * .
         */
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 9; j++) {
                if (tempMap[j][i] == aiColor) {
                    if (tempMap[j + 1][i] == aiColor) {
                        if (tempMap[j + 2][i] == aiColor) {
                            if (tempMap[j + 3][i] == aiColor) {
                                if (tempMap[j + 4][i] == aiColor) {
                                    if (tempMap[j + 5][i] == aiColor) {
                                        return count;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int rushFour(int[][] board, int aiColor, int x, int y) {
        int enemyColor = 3 - aiColor;
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0....0
         */
        {
            int a = y - 4;
            if (a < 0) {
                a = 0;
            }
            int b = y + 4;
            if (b > 14) {
                b = 14;
            }
            int k = y - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == enemyColor) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[x][i] == enemyColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 4;
            if (a < 0) {
                a = 0;
            }
            int b = x + 4;
            if (b > 14) {
                b = 14;
            }
            int k = x - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == enemyColor) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[i][y] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][i + y - x] == enemyColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == enemyColor) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][y1] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == enemyColor) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                }
                y1--;
            }
        }

        // 冲四的另一种棋形 哈哈哈哈
        /**
         * 0....0
         */
        {
            int a = y - 4;
            if (a < 0) {
                a = 0;
            }
            int b = y + 4;
            if (b > 14) {
                b = 14;
            }
            for (int i = a; i <= y; i++) {
                if (tempMap[x][i] == aiColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == 0) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = a; i <= y; i++) {
                if (tempMap[x][i] == aiColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == 0) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = a; i <= y; i++) {
                if (tempMap[x][i] == aiColor) {
                    try {
                        if (tempMap[x][i + 1] == 0) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 4;
            if (a < 0) {
                a = 0;
            }
            int b = x + 4;
            if (b > 14) {
                b = 14;
            }
            for (int i = a; i <= x; i++) {
                if (tempMap[i][y] == aiColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == 0) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = a; i <= x; i++) {
                if (tempMap[i][y] == aiColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == 0) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = a; i <= x; i++) {
                if (tempMap[i][y] == aiColor) {
                    try {
                        if (tempMap[i + 1][y] == 0) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][i + y - x] == aiColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == 0) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][i + y - x] == aiColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == 0) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][i + y - x] == aiColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == 0) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 4; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 4; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][y1] == aiColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == 0) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                y1--;
            }
            y1 += (x - x1 + 1);
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][y1] == aiColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == 0) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                y1--;
            }
            y1 += (x - x1 + 1);
            for (int i = x1; i <= x; i++) {
                if (tempMap[i][y1] == aiColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == 0) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                y1--;
            }
        }
        return count;
    }

    public int jumpFour(int[][] board, int aiColor, int x, int y) {
        int enemyColor = 3 - aiColor;
        if (x == 0 && y == 0) {
            return 0;
        }
        if (x == 0 && y == 14) {
            return 0;
        }
        if (x == 14 && y == 0) {
            return 0;
        }
        if (x == 14 && y == 14) {
            return 0;
        }

        int count = 0;
        int[][] tempMap = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempMap[i] = board[i].clone();
        }
        tempMap[x][y] = aiColor;
        /**
         * 0....0
         */
        {
            int a = y - 5;
            if (a < 0) {
                a = 0;
            }
            int b = y + 5;
            if (b > 14) {
                b = 14;
            }
            int k = y - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == 0) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == 0) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[x][i] == 0) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == 0) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                if (tempMap[x][i] == enemyColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == 0) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[x][i] == enemyColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == aiColor) {
                                if (tempMap[x][i + 3] == 0) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[x][i] == enemyColor) {
                    try {
                        if (tempMap[x][i + 1] == aiColor) {
                            if (tempMap[x][i + 2] == 0) {
                                if (tempMap[x][i + 3] == aiColor) {
                                    if (tempMap[x][i + 4] == aiColor) {
                                        if (tempMap[x][i + 5] == aiColor) {
                                            if (tempMap[x][i + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 0
         * .
         * .
         * .
         * 0
         */
        {
            int a = x - 5;
            if (a < 0) {
                a = 0;
            }
            int b = x + 5;
            if (b > 14) {
                b = 14;
            }
            int k = x - 1;
            for (int i = a; i <= k; i++) {
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == 0) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == 0) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y] == 0) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == 0) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == 0) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == 0) {
                                    if (tempMap[i + 4][y] == aiColor) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y] == aiColor) {
                            if (tempMap[i + 2][y] == aiColor) {
                                if (tempMap[i + 3][y] == aiColor) {
                                    if (tempMap[i + 4][y] == 0) {
                                        if (tempMap[i + 5][y] == aiColor) {
                                            if (tempMap[i + 6][y] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 0
         *  .
         *   .
         *    .
         *     0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 5; i++) {
                x1--;
                y1--;
                if (x1 < 0 || y1 < 0) {
                    x1++;
                    y1++;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 5; i++) {
                x2++;
                y2++;
                if (x2 > 14 || y2 > 14) {
                    x2--;
                    y2--;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][i + y - x] == enemyColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == 0) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][i + y - x] == enemyColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == 0) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][i + y - x] == enemyColor) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == 0) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == 0) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == aiColor) {
                                if (tempMap[i + 3][i + y - x + 3] == 0) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                if (tempMap[i][i + y - x] == 0) {
                    try {
                        if (tempMap[i + 1][i + y - x + 1] == aiColor) {
                            if (tempMap[i + 2][i + y - x + 2] == 0) {
                                if (tempMap[i + 3][i + y - x + 3] == aiColor) {
                                    if (tempMap[i + 4][i + y - x + 4] == aiColor) {
                                        if (tempMap[i + 5][i + y - x + 5] == aiColor) {
                                            if (tempMap[i + 6][i + y - x + 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            }
        }


        /**
         *      0
         *     .
         *    .
         *   .
         * 0
         */
        {
            int x1 = x;
            int y1 = y;
            for (int i = 1; i <= 5; i++) {
                x1--;
                y1++;
                if (x1 < 0 || y1 > 14) {
                    x1++;
                    y1--;
                    break;
                }
            }
            int x2 = x;
            int y2 = y;
            for (int i = 1; i <= 5; i++) {
                x2--;
                y2--;
                if (x2 < 0 || y2 < 0) {
                    x2++;
                    y2++;
                    break;
                }
            }
            int k = x - 1;
            for (int i = x1; i <= k; i++) {
                if (tempMap[i][y1] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == 0) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y1] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == 0) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y1] == enemyColor) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == 0) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == 0) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == aiColor) {
                                if (tempMap[i + 3][y1 - 3] == 0) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (tempMap[i][y1] == 0) {
                    try {
                        if (tempMap[i + 1][y1 - 1] == aiColor) {
                            if (tempMap[i + 2][y1 - 2] == 0) {
                                if (tempMap[i + 3][y1 - 3] == aiColor) {
                                    if (tempMap[i + 4][y1 - 4] == aiColor) {
                                        if (tempMap[i + 5][y1 - 5] == aiColor) {
                                            if (tempMap[i + 6][y1 - 6] == enemyColor) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                y1--;
            }
        }
        return count;
    }

    public int[] alpha_beta(int depth, int[][] board,int aiColor, int[] alpha, int[] beta) {
        if (depth == 4){
            int[] min = new int[3];
            min[0] = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (isEmpty(board, i, j) && !canExclude(board,i,j)) {
                        int temp = getGrade(board,aiColor,i,j);
                        if (temp < min[0]){
                            min[0] = temp;
                            min[1] = i;
                            min[2] = j;
                        }
                    }

                }
            }
            return min;
        }
        int[][] temp = myCopyArray(board);
        for (int p = 0; p < board.length; p++) {
            for (int q = 0; q < board.length; q++) {
                if (isEmpty(temp, p, q) && !canExclude(temp,p,q)) {
                    temp[p][q] = aiColor;
                    int[] answer = alpha_beta(depth + 1, temp, 3 - aiColor, alpha, beta);
                    temp = myCopyArray(board);
                    if (depth % 2 == 0) {
                        // MAX
                        if (answer[0] > alpha[0]) {
                            alpha = answer;
                        }
                        if (alpha[0] >= beta[0]) {
                            return alpha;
                        }
                    } else {
                        // MIN
                        if (answer[0] < beta[0]) {
                            beta = answer;
                        }
                        if (alpha[0] >= beta[0]) {
                            return beta;
                        }
                    }
                }
            }
        }
        return depth % 2 == 0 ? alpha : beta;
    }
    public int[][] myCopyArray(int[][] a){
        int[][] temp = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                temp[i][j] = a[i][j];
            }
        }
        return temp;
    }
    public boolean canExclude(int[][] board,int x,int y){
        if (x == 0 || y == 0 || x == 14 || y ==14){
            return true;
        }
        if (x >=1 && y>= 1 && x<=14 && y<=14){
            try {
                if(board[x-1][y-1]!=0 ){
                    return false;
                }
            } catch (Exception e) {

            }
            try {
                if(board[x-1][y]!=0 ){
                    return false;
                }
            } catch (Exception e) {

            }
            try {
                if(board[x-1][y+1]!=0 ){
                    return false;
                }
            } catch (Exception e) {

            }
            try {
                if(board[x][y-1]!=0 ){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(board[x][y+1]!=0 ){
                    return false;
                }
            } catch (Exception e) {

            }
            try {
                if(board[x+1][y-1]!=0 ){
                    return false;
                }
            } catch (Exception e) {
            }
            try {
                if(board[x+1][y]!=0 ){
                    return false;
                }
            } catch (Exception e) {
            }
            try {
                if(board[x+1][y+1]!=0 ){
                    return false;
                }
            } catch (Exception e) {

            }
        }else {
            return false;
        }

        return true;

    }
}
