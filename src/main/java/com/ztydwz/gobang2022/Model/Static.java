package main.java.com.ztydwz.gobang2022.Model;

import main.java.com.ztydwz.gobang2022.View.GameFrame;

import java.util.ArrayList;
import java.util.List;

public class Static {
    public static final int ROWS = 15;  //15行
    public static final int COLS = 15;  //15列
    public static final int start = 30;      //初始坐标
    public static GamePanel gamePanel;
    public static GameFrame gameFrame;
    public static Pointer[][] pointers = new Pointer[ROWS][COLS];
    public static int distance = 40;//棋盘每条线的间距
    public static List<Chess> chessList = new ArrayList<Chess>(); //创建棋子列表
    public static ChessType aiType;
    public static ChessType playerType;
    public static int ROW = 15;
    public static int COL = 15;
    public static int[][] Map = new int[15][15];
    public static whoFirst first;
    public static boolean ifAiPutChess = true;
    public static whoPutChess putChess; //当前是谁下棋
    public static GameMode gameMode;  //游戏模式
    public static int fiveDaNumber = 2;  //打点数量
    public static List<Chess> sigleChessList = new ArrayList<>();
    public static List<Integer> dRFiveDaList = new ArrayList<>(); //对方五手N打的坐标集合
    public static int winFlag = -1; //
    public static boolean gameFlag = false; //
    public static boolean ifForbiddenHand = false; //是否有禁手
    public static boolean ifForbiddenHandOpen = true; //是否开启禁手检测
    public static boolean ifAllowForbiddenHandOpen = true; //是否允许程序下禁手

    public enum whoPutChess {
        aiPutChess, playerPutChess
    }

    public enum whoFirst {
        player, Ai
    }

    //人机模式自由开局和人机指定开局和双人对战
    public enum GameMode {
        freeStart, designatedStart, twoPlayerBattle, twoAiPlayerBattle
    }

}
