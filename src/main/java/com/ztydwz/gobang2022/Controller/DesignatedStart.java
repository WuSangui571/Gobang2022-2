package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Model.Chess;
import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.Model.Pointer;
import main.java.com.ztydwz.gobang2022.View.Option;

import java.util.Random;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class DesignatedStart {
    public void aiDesignatedStart() {
        Random random = new Random();
        int choose = random.nextInt(26) + 1;
        String startName = "出现错误";
        switch (choose) {
            case 1: {
                type1();
                startName = "长星局";
                break;
            }
            case 2: {
                type2();
                startName = "峡月局";
                break;
            }
            case 3: {
                type3();
                startName = "恒星局";
                break;
            }
            case 4: {
                type4();
                startName = "水月局";
                break;
            }
            case 5: {
                type5();
                startName = "流星局";
                break;
            }
            case 6: {
                type6();
                startName = "云月局";
                break;
            }
            case 7: {
                type7();
                startName = "浦月局";
                break;
            }
            case 8: {
                type8();
                startName = "岚月局";
                break;
            }
            case 9: {
                type9();
                startName = "银月局";
                break;
            }
            case 10: {
                type10();
                startName = "明星局";
                break;
            }
            case 11: {
                type11();
                startName = "斜月局";
                break;
            }
            case 12: {
                type12();
                startName = "名月局";
                break;
            }
            case 13: {
                type13();
                startName = "彗星局";
                break;
            }
            case 14: {
                type14();
                startName = "寒星局";
                break;
            }
            case 15: {
                type15();
                startName = "溪月局";
                break;
            }
            case 16: {
                type16();
                startName = "疏星局";
                break;
            }
            case 17: {
                type17();
                startName = "花月局";
                break;
            }
            case 18: {
                type18();
                startName = "残月局";
                break;
            }
            case 19: {
                type19();
                startName = "雨月局";
                break;
            }
            case 20: {
                type20();
                startName = "金星局";
                break;
            }
            case 21: {
                type21();
                startName = "松月局";
                break;
            }
            case 22: {
                type22();
                startName = "丘月局";
                break;
            }
            case 23: {
                type23();
                startName = "新月局";
                break;
            }
            case 24: {
                type24();
                startName = "瑞星局";
                break;
            }
            case 25: {
                type25();
                startName = "山月局";
                break;
            }
            case 26: {
                type26();
                startName = "游星局";
                break;
            }
            default: {
                System.out.println("出现错误");
            }
        }
        new Option().createOption(startName);
    }


    public void type1() {           //长星局
        putChessByType(6, 8, 5, 9);
    }

    public void type2() {           //峡月局
        putChessByType(6, 8, 6, 9);
    }

    public void type3() {           //恒星局
        putChessByType(6, 8, 7, 9);
    }

    public void type4() {           // 水月局
        putChessByType(6, 8, 8, 9);
    }

    public void type5() {           // 流星局
        putChessByType(6, 8, 9, 9);
    }

    public void type6() {           // 云月局
        putChessByType(6, 8, 7, 8);
    }

    public void type7() {           // 浦月局
        putChessByType(6, 8, 8, 8);
    }

    public void type8() {           // 岚月局
        putChessByType(6, 8, 9, 8);
    }

    public void type9() {           // 银月局
        putChessByType(6, 8, 8, 7);
    }

    public void type10() {           // 明星局
        putChessByType(6, 8, 9, 7);
    }

    public void type11() {           // 斜月局
        putChessByType(6, 8, 8, 6);
    }

    public void type12() {           // 名月局
        putChessByType(6, 8, 9, 6);
    }

    public void type13() {           // 彗星局
        putChessByType(6, 8, 9, 5);
    }

    public void type14() {           //  寒星局
        putChessByType(6, 7, 5, 7);
    }

    public void type15() {           //  溪月局
        putChessByType(6, 7, 5, 8);
    }

    public void type16() {           //  疏星局
        putChessByType(6, 7, 5, 9);
    }

    public void type17() {           //  花月局
        putChessByType(6, 7, 6, 8);
    }

    public void type18() {           //  残月局
        putChessByType(6, 7, 6, 9);
    }

    public void type19() {           //  雨月局
        putChessByType(6, 7, 7, 8);
    }

    public void type20() {           //  金星局
        putChessByType(6, 7, 7, 9);
    }

    public void type21() {           //  松月局
        putChessByType(6, 7, 8, 7);
    }

    public void type22() {           //  丘月局
        putChessByType(6, 7, 8, 8);
    }

    public void type23() {           //  新月局
        putChessByType(6, 7, 8, 9);
    }

    public void type24() {           //  瑞星局
        putChessByType(6, 7, 9, 7);
    }

    public void type25() {           //   山月局
        putChessByType(6, 7, 9, 8);
    }

    public void type26() {           //   山月局
        putChessByType(6, 7, 9, 9);
    }


    public void putChessByType(int i1, int j1, int i2, int j2) {
        gameFlag = true;
        Map[7][7] = 1;
        Map[i1][j1] = 2;
        Map[i2][j2] = 1;
        Pointer pointer1 = pointers[7][7];
        Pointer pointer2 = pointers[i1][j1];
        Pointer pointer3 = pointers[i2][j2];
        pointer1.setHasChess(true);
        pointer2.setHasChess(true);
        pointer3.setHasChess(true);

        Chess chess1 = new Chess(pointer1.getX(), pointer1.getY(), ChessType.BLACK);
        Chess chess2 = new Chess(pointer2.getX(), pointer2.getY(), ChessType.White);
        Chess chess3 = new Chess(pointer3.getX(), pointer3.getY(), ChessType.BLACK);
        chessList.add(chess1);
        chessList.add(chess2);
        chessList.add(chess3);
    }

}
