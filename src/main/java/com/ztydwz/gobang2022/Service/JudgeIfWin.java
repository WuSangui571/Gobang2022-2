package main.java.com.ztydwz.gobang2022.Service;

import main.java.com.ztydwz.gobang2022.View.GameFrame;

import javax.swing.*;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;


public class JudgeIfWin {
    //
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

    public JudgeIfWin() {
        if (winFlag != -1 || ifForbiddenHand) {
            return;
        }
        this.victory(Map, gameFrame);
    }

    public void victory(int[][] map, GameFrame frame) {
//        int h;
//        int l;
//        int key;
//        String rtu;
//        for (h = 0; h < 15; ++h) {
//            for (l = 0; l <= 10; ++l) {
//                key = map[h][l];
//                if (key != 0 && map[h][l + 1] == key && map[h][l + 2] == key && map[h][l + 3] == key && map[h][l + 4] == key) {
//                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
//                    JOptionPane.showMessageDialog(frame, rtu);
//                    winFlag = key == 1 ? 1 : 2;
//                }
//            }
//        }
//
//        for (h = 0; h <= 10; ++h) {
//            for (l = 0; l < 15; ++l) {
//                key = map[h][l];
//                if (key != 0 && map[h + 1][l] == key && map[h + 2][l] == key && map[h + 3][l] == key && map[h + 4][l] == key) {
//                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
//                    JOptionPane.showMessageDialog(frame, rtu);
//                    winFlag = key == 1 ? 1 : 2;
//                    gameFlag = false;
//                }
//            }
//        }
//
//        for (h = 0; h <= 10; ++h) {
//            for (l = 0; l <= 10; ++l) {
//                key = map[h][l];
//                if (key != 0 && map[h + 1][l + 1] == key && map[h + 2][l + 2] == key && map[h + 3][l + 3] == key && map[h + 4][l + 4] == key) {
//                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
//                    JOptionPane.showMessageDialog(frame, rtu);
//                    winFlag = key == 1 ? 1 : 2;
//                    gameFlag = false;
//                }
//            }
//        }
//
//        for (h = 0; h <= 10; ++h) {
//            for (l = 4; l < 15; ++l) {
//                key = map[h][l];
//                if (key != 0 && map[h + 1][l - 1] == key && map[h + 2][l - 2] == key && map[h + 3][l - 3] == key && map[h + 4][l - 4] == key) {
//                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
//                    JOptionPane.showMessageDialog(frame, rtu);
//                    winFlag = key == 1 ? 1 : 2;
//                    gameFlag = false;
//                }
//            }
//        }
        int h;
        int l;
        int key;
        String rtu;
        for (h = 0; h < 15; ++h) {
            for (l = 0; l <= 10; ++l) {
                key = map[h][l];
                try {
                    if (key != 0 && map[h][l + 1] == key && map[h][l + 2] == key && map[h][l + 3] == key && map[h][l + 4] == key && map[h][l + 5] != key) {
                        rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                        winFlag = key == 1 ? 1 : 2;
                        JOptionPane.showMessageDialog(frame, rtu);
                        gameFlag = false;
                    }
                } catch (Exception e) {
                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                    winFlag = key == 1 ? 1 : 2;
                    JOptionPane.showMessageDialog(frame, rtu);
                    gameFlag = false;
                }

            }
        }

        for (h = 0; h <= 10; ++h) {
            for (l = 0; l < 15; ++l) {
                key = map[h][l];
                try {
                    if (key != 0 && map[h + 1][l] == key && map[h + 2][l] == key && map[h + 3][l] == key && map[h + 4][l] == key && map[h + 5][l] != key) {
                        rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                        winFlag = key == 1 ? 1 : 2;
                        JOptionPane.showMessageDialog(frame, rtu);
                        gameFlag = false;
                    }
                } catch (Exception e) {
                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                    winFlag = key == 1 ? 1 : 2;
                    JOptionPane.showMessageDialog(frame, rtu);
                    gameFlag = false;
                }

            }
        }

        for (h = 0; h <= 10; ++h) {
            for (l = 0; l <= 10; ++l) {
                key = map[h][l];
                try {
                    if (key != 0 && map[h + 1][l + 1] == key && map[h + 2][l + 2] == key && map[h + 3][l + 3] == key && map[h + 4][l + 4] == key && map[h + 5][l + 5] != key) {
                        rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                        winFlag = key == 1 ? 1 : 2;
                        JOptionPane.showMessageDialog(frame, rtu);
                        gameFlag = false;
                    }
                } catch (Exception e) {
                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                    winFlag = key == 1 ? 1 : 2;
                    JOptionPane.showMessageDialog(frame, rtu);
                    gameFlag = false;
                }

            }
        }

        for (h = 0; h <= 10; ++h) {
            for (l = 4; l < 15; ++l) {
                key = map[h][l];
                try {
                    if (key != 0 && map[h + 1][l - 1] == key && map[h + 2][l - 2] == key && map[h + 3][l - 3] == key && map[h + 4][l - 4] == key && map[h + 5][l - 5] != key) {
                        rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                        winFlag = key == 1 ? 1 : 2;
                        JOptionPane.showMessageDialog(frame, rtu);
                        gameFlag = false;
                    }
                } catch (Exception e) {
                    rtu = key == 1 ? "黑棋胜利" : "白棋胜利";
                    winFlag = key == 1 ? 1 : 2;
                    JOptionPane.showMessageDialog(frame, rtu);
                    gameFlag = false;
                }
            }
        }

    }

}
