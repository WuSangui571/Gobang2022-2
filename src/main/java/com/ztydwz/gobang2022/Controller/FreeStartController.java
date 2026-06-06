package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Service.JudgeIfWin;

import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class FreeStartController extends MouseAdapter {
    PointerController pointerController = new PointerController();
    ChessController chessController = new ChessController();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameFlag) {
            return;
        }
        if (putChess == whoPutChess.playerPutChess) {
            if (chessController.PlayerPutChess(e.getX(), e.getY())) {
                if (winFlag == -1)
                    new JudgeIfWin();
                putChess = whoPutChess.aiPutChess;
            }
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameFlag) {
            return;
        }

        pointerController.changePointerShow(e.getX(), e.getY());
        if (putChess == whoPutChess.aiPutChess && !aiThinking) {
            aiThinking = true;
            new Thread(() -> {
                try {
                    int[] move = chessController.getAiMove();
                    SwingUtilities.invokeLater(() -> {
                        if (chessController.applyAiMove(move[0], move[1])) {
                            putChess = whoPutChess.playerPutChess;
                            if (winFlag == -1) {
                                new JudgeIfWin();
                            }
                        } else {
                            gameFlag = false;
                            System.out.println("AI has no legal move; game stopped.");
                        }
                        aiThinking = false;
                    });
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        aiThinking = false;
                    });
                }
            }).start();
        }
    }
}
