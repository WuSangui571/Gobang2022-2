package main.java.com.ztydwz.gobang2022.Service;


import main.java.com.ztydwz.gobang2022.Model.*;

import java.awt.*;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;


public class DrawService {
    private final int distance = 40;//棋盘每条线的间距
    private final int lineNumber = 15;//棋盘的线个数
    private final int start = 30;//初始距离
    private final int cell = 40;

    public DrawService() {

    }

    public static void drawClock(Graphics g) {
        g.setFont(new Font("宋体", 1, 20));
        g.setColor(Color.BLACK);
        int BlackHour = Clock.BlackTime / 3600;
        int BlackMinute = Clock.BlackTime / 60 - Clock.BlackTime / 3600 * 60;
        int BlackSecond = Clock.BlackTime - Clock.BlackTime / 60 * 60;
        String BlackMessage = String.format("%02d", BlackHour) + ":" + String.format("%02d", BlackMinute) + ":" + String.format("%02d", BlackSecond);
        g.drawString("黑方用时:" + BlackMessage, 20, 690);
        int WhiteHour = Clock.WhiteTime / 3600;
        int WhiteMinute = Clock.WhiteTime / 60 - Clock.WhiteTime / 3600 * 60;
        int WhiteSecond = Clock.WhiteTime - Clock.WhiteTime / 60 * 60;
        String WhiteMessage = String.format("%02d", WhiteHour) + ":" + String.format("%02d", WhiteMinute) + ":" + String.format("%02d", WhiteSecond);
        g.drawString("白方用时:" + WhiteMessage, 400, 690);
    }

    public void draw(Graphics graphics) {
        ImageValue.init();
        drawGrid(graphics);
        drawFivePoint(graphics);
        drawNumber(graphics);
        drawPoint(graphics);
        drawChess(graphics);
        drawSingleChess(graphics);
        drawClock(graphics);
    }

    public void drawGrid(Graphics graphics) { //绘制棋盘
        int x1 = start; //初始距离
        int y1 = start; //初始距离
        int x2 = start + distance * (lineNumber - 1);
        int y2 = start;
        for (int i = 0; i < 15; i++) {

            graphics.drawLine(x1, start + i * distance, x2, start + i * distance); //绘制横线
            graphics.drawLine(start + i * distance, y1, start + i * distance, start + distance * (lineNumber - 1));//绘制竖线
        }
    }

    public void drawFivePoint(Graphics graphics) {   //绘制棋盘五个点
        int diameter = 10; //直径
        //中心点
        graphics.fillOval(start + 7 * cell - diameter / 2, start + 7 * cell - diameter / 2, diameter, diameter);//以椭圆左上角的点画圆形，所有要减去一半
        //左上
        graphics.fillOval(start + 3 * cell - diameter / 2, start + 3 * cell - diameter / 2, diameter, diameter);
        //左下
        graphics.fillOval(start + 3 * cell - diameter / 2, start + 11 * cell - diameter / 2, diameter, diameter);
        //右上
        graphics.fillOval(start + 11 * cell - diameter / 2, start + 3 * cell - diameter / 2, diameter, diameter);
        //右下
        graphics.fillOval(start + 11 * cell - diameter / 2, start + 11 * cell - diameter / 2, diameter, diameter);
    }

    public void drawNumber(Graphics graphics) {  //绘制棋盘数字
        graphics.setFont(new Font("微软雅黑", 1, 15));
        char character = 'A';
        for (int i = 0; i < 15; i++) {
            graphics.drawString(String.valueOf(character++), i * distance + start - 5, start + distance * (lineNumber - 1) + 15);
            if (i < 6) {
                graphics.drawString(String.valueOf(15 - i), start - 23, i * distance + start + 5);
            } else {
                graphics.drawString(String.valueOf(15 - i), start - 15, i * distance + start + 5);
            }
        }
    }

    public void drawPoint(Graphics graphics) {
        Pointer pointer;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pointer = pointers[i][j];

                if (pointer.getShow()) {
                    //绘制指示器

                    int x = pointer.getX();

                    int y = pointer.getY();

                    graphics.drawImage(ImageValue.pointerImage, x - cell / 2, y - cell / 2, cell, cell, null);
                }
            }
        }

    }

    public void drawSingleChess(Graphics graphics) {
        if (sigleChessList.size() == 0) {
            return;
        }
        int height = cell - 5;
        for (int i = 0; i < sigleChessList.size(); i++) {
            Chess chess = sigleChessList.get(i);
            int x = chess.getX();             //棋子的横坐标
            int y = chess.getY();             //棋子的纵坐标
            ChessType type = chess.type;
            if (type == ChessType.White) {                                                       //当棋子为白棋时
                /**
                 * @param int x
                 * @param int y
                 * @param int weight
                 * @param int height
                 * @param  java.awt.image.ImageObserver observer
                 */
                graphics.drawImage(ImageValue.whiteChess, x - height / 2, y - height / 2, height, height, null);
            } else if (type == ChessType.BLACK) {                                                                //当棋子为黑棋时
                graphics.drawImage(ImageValue.blackChess, x - height / 2, y - height / 2, height, height, null);
            }
        }

    }

    public void drawChess(Graphics graphics) {
        int height = cell - 5;
        for (int i = 0; i < chessList.size(); i++) {
            Chess chess = chessList.get(i);
            int x = chess.getX();             //棋子的横坐标
            int y = chess.getY();             //棋子的纵坐标
            ChessType type = chess.type;
            if (type == ChessType.White) {                                                       //当棋子为白棋时
                /**
                 * @param int x
                 * @param int y
                 * @param int weight
                 * @param int height
                 * @param  java.awt.image.ImageObserver observer
                 */
                graphics.drawImage(ImageValue.whiteChess, x - height / 2, y - height / 2, height, height, null);
                graphics.setColor(Color.BLACK);
                drawChessNumber(graphics, i, x, y);

            } else if (type == ChessType.BLACK) {                                                                //当棋子为黑棋时
                graphics.drawImage(ImageValue.blackChess, x - height / 2, y - height / 2, height, height, null);
                graphics.setColor(Color.WHITE);
                drawChessNumber(graphics, i, x, y);
            }
        }
    }

    public void drawChessNumber(Graphics graphics, int number, int x, int y) {
        if (number == chessList.size() - 1) {
            graphics.setColor(Color.RED);
        }
        graphics.setFont(new Font("宋体", Font.BOLD, 15));
        if (number < 10) {
            x -= 5;                          //经实践得出的坐标计算方法
        } else {                            //经实践得出的坐标计算方法
            x -= 9;
        }
        y += 3;
        graphics.drawString(String.valueOf(number + 1), x, y);
    }
}
