package main.java.com.ztydwz.gobang2022.Model;

//棋子类
public class Chess {
    private final int h = 36;    //高度
    public ChessType type; //1为黑棋，2为白棋
    private int x = 0;   //x坐标
    private int y = 0;   //y坐标

    public Chess(int x, int y, ChessType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
