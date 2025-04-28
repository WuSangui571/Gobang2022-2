package main.java.com.ztydwz.gobang2022.Model;

public class Pointer {
    private final int i = 0; //棋盘数组行下标x
    private final int j = 0; //棋盘数组列下标x
    private final int cell = 40;
    private int x = 0; //
    private int y = 0;
    private boolean ifShow = false;
    private boolean hasChess = false;  // 0 没旗,1黑棋,2白棋

    public Pointer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHasChess() {
        return hasChess;
    }

    public void setHasChess(boolean hasChess) {
        this.hasChess = hasChess;
    }

    public boolean isShow(int x, int y) {
        //左上角
        int x1 = this.x - cell / 2;
        int y1 = this.y - cell / 2;
        //右下角
        int x2 = this.x + cell / 2;
        int y2 = this.y + cell / 2;
        return x > x1 && y > y1 && x < x2 && y < y2;
    }

    public void hasChess() {

    }

    public boolean getShow() {
        return ifShow;
    }

    public void setShow(boolean show) {
        ifShow = show;
    }
}
