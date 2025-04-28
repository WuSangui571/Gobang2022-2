package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.Service.JudgeIfWin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class TwoPlayerBattleController extends MouseAdapter {
    PointerController pointerController = new PointerController();
    ChessController chessController = new ChessController();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameFlag) {
            return;
        }

        if (chessController.PlayerPutChess(e.getX(), e.getY())) {   //确保玩家棋子已经下了
            if (winFlag == -1) {
                new JudgeIfWin();
            }
            if (playerType == ChessType.BLACK) {
                playerType = ChessType.White;
            } else {
                playerType = ChessType.BLACK;
            }
        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameFlag) {
            return;
        }

        pointerController.changePointerShow(e.getX(), e.getY());
    }
}
