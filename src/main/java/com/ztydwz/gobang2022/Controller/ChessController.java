package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Model.Chess;
import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.Model.Pointer;
import main.java.com.ztydwz.gobang2022.Model.Static;
import main.java.com.ztydwz.gobang2022.Service.AI;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class ChessController {

    public static boolean putFiveDaChess(int x, int y) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Pointer pointer = pointers[i][j];

                if (pointer.isShow(x, y) && !pointer.isHasChess()) {
                    dRFiveDaList.add(i);
                    dRFiveDaList.add(j);
                    Chess chess = new Chess(pointer.getX(), pointer.getY(), playerType);
                    sigleChessList.add(chess);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean PlayerPutChess(int x, int y) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Pointer pointer = pointers[i][j];
                if (pointer.isShow(x, y) && !pointer.isHasChess()) {
                    if (ifForbiddenHandOpen) {
                        Static.ifForbiddenHand = new AI().isForbiddenHand(i, j);
                    }
                    Chess chess = new Chess(pointer.getX(), pointer.getY(), playerType);
                    if (playerType == ChessType.BLACK) {
                        Map[i][j] = 1;
                    } else {
                        Map[i][j] = 2;
                    }
                    chessList.add(chess);
                    pointer.setHasChess(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void AiPutChess() {
        AI ai = new AI();
        if (aiType == ChessType.BLACK) {
            ai.setAiType(1);
        } else {
            ai.setAiType(2);
        }

        int[] arr = ai.getLocation();
        int i = arr[0];
        int j = arr[1];
        Pointer pointer = pointers[i][j];
        pointer.setHasChess(true);
        if (aiType == ChessType.BLACK) {
            Map[i][j] = 1;
        } else {
            Map[i][j] = 2;
        }

        Chess chess = new Chess(pointer.getX(), pointer.getY(), aiType);
        chessList.add(chess);
    }

    public void restract() {                                    //悔棋
        //撤销上一次下的子
        Chess lastChess = chessList.get(chessList.size() - 1);
        int LastX = lastChess.getX();
        int LastY = lastChess.getY();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (LastX == pointers[i][j].getX() && (LastY == pointers[i][j].getY())) {
                    pointers[i][j].setHasChess(false);
                    Map[i][j] = 0;
                }
            }
        }
        chessList.remove(chessList.size() - 1);
    }

}
