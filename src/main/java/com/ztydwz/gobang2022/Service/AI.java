package main.java.com.ztydwz.gobang2022.Service;


import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.View.Option;

import java.util.Random;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;


public class AI implements AiInterface {
    static int AiType;
    static int direnType;


    @Override
    public boolean ifChange() {
        return false;
    } //是否三手交换

    @Override
    public int[] getLocation() {
        Shou shou = new Shou();
        return shou.getAnswer(Map, ROW, COL, direnType, AiType);

    }

    @Override
    public int[] fiveDa(int[][] map, int N) {
        int[][] tempMap = new int[15][15];
        int[] arr = new int[N * 2];
        Random rand = new Random();
        int index = 0;
        int scNum = 0;

        while (scNum < N) {
            int h = rand.nextInt(3) + 6;
            int l = rand.nextInt(3) + 6;
            if (map[h][l] == 0 && tempMap[h][l] == 0) {
                arr[index] = h;
                ++index;
                arr[index] = l;
                ++scNum;
                ++index;
                tempMap[h][l] = 1;
            }
        }

        return arr;
    }

    @Override
    public int[] drFiveDa(int[] arr) {
        int size = arr.length;//获取数组长度
        Random ran = new Random();
        //生成偶数
        int i = ran.nextInt(size);
        while (i % 2 != 0) {
            i = ran.nextInt(size);
        }
        //返回其中一个点的行列值
        int[] temparr = {arr[i], arr[i + 1]};
//        System.out.println("i=" + i + "ai.arr[i]=" + arr[i] + "ai.arr[i+1]=" + arr[i + 1]);
//        System.out.println("temparr[0]=" + temparr[0] + "temparr[1]=" + temparr[1]);
        return temparr;
    }

    @Override
    public boolean isForbiddenHand(int i, int j) {
        int myType;
        if (playerType == ChessType.BLACK) {
            myType = 1;
            Shou shou = new Shou();
            int liveThree = shou.newLiveThree(Map, myType, i, j);
            int jumpLiveThree = shou.jumpLiveThree(Map, myType, i, j);
            int liveFour = shou.liveFour(Map, myType, i, j);
            int longSix = shou.longSix(Map, myType, i, j);
            int rushFour = shou.rushFour(Map, myType, i, j);
            //int jumpFour = shou.jumpFour(Map,myType,i,j);

            if (longSix == 1) {

                JudgeIfWin judgeIfWin = new JudgeIfWin();
                judgeIfWin.victory(Map, gameFrame);
                System.out.println("i:" + i + ",j:" + j + "，新增活三数：" + liveThree +
                        "，新增跳活三数：" + jumpLiveThree + "，新增活四数：" + liveFour +
                        "，新增长连数：" + longSix + "，新增冲四+跳四数：" + rushFour);
                System.out.println("禁手！");
                new Option().createOption("长连禁手");
                winFlag = 3 - myType;
                new Option().createOption(winFlag == 1 ? "黑棋获胜" : "白棋获胜");
                gameFlag = false;
                return true;
            }
            /*if (liveThree + jumpLiveThree == 1 && rushFour + jumpFour + liveFour == 2){
                JudgeIfWin judgeIfWin = new JudgeIfWin();
                judgeIfWin.victory(Map,gameFrame);
                System.out.println("禁手！");
                new Option().createOption("四四、三禁手");
                winFlag = 3 - myType;
                new Option().createOption(winFlag == 1?"黑棋获胜":"白棋获胜");
                gameFlag = false;
                return true;
            }
            if (liveThree + jumpLiveThree == 2 && rushFour + jumpFour + liveFour == 1){
                JudgeIfWin judgeIfWin = new JudgeIfWin();
                judgeIfWin.victory(Map,gameFrame);
                System.out.println("禁手！");
                new Option().createOption("四三、三禁手");
                winFlag = 3 - myType;
                new Option().createOption(winFlag == 1?"黑棋获胜":"白棋获胜");
                gameFlag = false;
                return true;
            }*/
            //这个不确定
            if (liveThree + jumpLiveThree >= 2) {
                JudgeIfWin judgeIfWin = new JudgeIfWin();
                judgeIfWin.victory(Map, gameFrame);
                System.out.println("i:" + i + ",j:" + j + "，新增活三数：" + liveThree +
                        "，新增跳活三数：" + jumpLiveThree + "，新增活四数：" + liveFour +
                        "，新增长连数：" + longSix + "，新增冲四+跳四数：" + rushFour);
                System.out.println("禁手！");
                new Option().createOption("三、三禁手");
                winFlag = 3 - myType;
                new Option().createOption(winFlag == 1 ? "黑棋获胜" : "白棋获胜");
                gameFlag = false;
                return true;
            }
            if (liveFour + rushFour >= 2) {
                //if (liveFour + rushFour + jumpFour >= 2){
                JudgeIfWin judgeIfWin = new JudgeIfWin();
                judgeIfWin.victory(Map, gameFrame);
                System.out.println("i:" + i + ",j:" + j + "，新增活三数：" + liveThree +
                        "，新增跳活三数：" + jumpLiveThree + "，新增活四数：" + liveFour +
                        "，新增长连数：" + longSix + "，新增冲四+跳四数：" + rushFour);
                System.out.println("禁手！");
                new Option().createOption("四、四禁手");
                winFlag = 3 - myType;
                new Option().createOption(winFlag == 1 ? "黑棋获胜" : "白棋获胜");
                gameFlag = false;
                return true;
            }
            System.out.println("i:" + i + ",j:" + j + "，新增活三数：" + liveThree +
                    "，新增跳活三数：" + jumpLiveThree + "，新增活四数：" + liveFour +
                    "，新增长连数：" + longSix + "，新增冲四+跳四数：" + rushFour);
            /*System.out.println("i:" + i + ",j:" + j + "，新增活三数：" + liveThree +
                    "，新增跳活三数：" + jumpLiveThree + "，新增活四数：" + liveFour +
                    "，新增长连数：" + longSix + "，新增冲四数：" + rushFour + "，新增跳四数：" +
                    jumpFour);*/
        }
        return false;
    }

    public void setAiType(int AIType) {
        AiType = AIType;
        if (AiType == 1) {
            direnType = 2;
        } else if (AiType == 2) {
            direnType = 1;
        }
    }
}