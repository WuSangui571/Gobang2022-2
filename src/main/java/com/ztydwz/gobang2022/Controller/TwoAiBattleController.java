package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Model.Chess;
import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.Model.Pointer;
import main.java.com.ztydwz.gobang2022.Service.AI;
import main.java.com.ztydwz.gobang2022.Service.JudgeIfWin;
import main.java.com.ztydwz.gobang2022.View.Option;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class TwoAiBattleController extends MouseAdapter {

    ChessController chessController = new ChessController();
    boolean ifFiveDa = false; //是否再进行五手N打
    boolean ifDRFiveDa = false;

    public TwoAiBattleController() {
        new DesignatedStart().aiDesignatedStart();
        putChess = whoPutChess.aiPutChess;
        playerType = ChessType.BLACK;

    }

    @Override
    public void mouseClicked(MouseEvent e) {


        if (!gameFlag) {
            return;
        }

        FiveDa();

        if (ifDRFiveDa) {
            chooseDRChess();
            ifDRFiveDa = false;
        }
        if (putChess == whoPutChess.aiPutChess) {
            whiteAiController();
            if (winFlag == -1) {
                new JudgeIfWin();
            }
            putChess = whoPutChess.playerPutChess;
        } else {
            blackAiController();
            if (winFlag == -1) {
                new JudgeIfWin();
            }
            putChess = whoPutChess.aiPutChess;
        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameFlag) {
            return;
        }
    }


    public void FiveDa() {

        if (chessList.size() == 4 && !ifFiveDa) {
            new Option().createFiveDaNumber();
            AI ai = new AI();
            aiType = ChessType.BLACK;
            int[] arr = ai.fiveDa(Map, fiveDaNumber);
            for (int i = 0; i < arr.length; i += 2) {
                Pointer pointer = pointers[arr[i]][arr[i + 1]];
                dRFiveDaList.add(arr[i]);
                dRFiveDaList.add(arr[i + 1]);
                Chess chess = new Chess(pointer.getX(), pointer.getY(), aiType);
                sigleChessList.add(chess);
                putChess = whoPutChess.playerPutChess;
            }
            ifFiveDa = true;
            ifDRFiveDa = true;
            new Option().createFiveDaOption();
        }
    }




    public void chooseDRChess() {
        int[] dRFiveDaChess = new int[fiveDaNumber * 2];
        for (int i = 0; i < dRFiveDaList.size(); i++) {
            dRFiveDaChess[i] = dRFiveDaList.get(i);
        }
        AI ai = new AI();
        int[] rtu = ai.drFiveDa(dRFiveDaChess);
        new Option().createOption("电脑选择留下坐标为" + (char) (rtu[1] + 1 + 64) + "," + (15 - rtu[0] + "的点"));
        chooseFiveDa();
        Pointer pointer = pointers[rtu[0]][rtu[1]];
        pointer.setHasChess(true);
        Map[rtu[0]][rtu[1]] = 1;
        Chess chess = new Chess(pointer.getX(), pointer.getY(), ChessType.BLACK);
        chessList.add(chess);
        ifDRFiveDa = false;
        putChess = whoPutChess.aiPutChess;
    }

    public void blackAiController() {         //假定为玩家
        aiType = ChessType.BLACK;
        aiPutChess();
    }

    public void whiteAiController() {
        aiType = ChessType.White;
        aiPutChess();
    }

    public void chooseFiveDa() {
        sigleChessList = new ArrayList<>();
    }

    public void aiPutChess() {
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
        if (aiType == ChessType.BLACK) {
            new AI().isForbiddenHand(i, j);
        }

    }


}
