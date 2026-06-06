package main.java.com.ztydwz.gobang2022.Service;

import main.java.com.ztydwz.gobang2022.Model.ChessType;
import main.java.com.ztydwz.gobang2022.Model.Clock;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;
import static main.java.com.ztydwz.gobang2022.Model.Static.whoPutChess.aiPutChess;
import static main.java.com.ztydwz.gobang2022.Model.Static.whoPutChess.playerPutChess;

public class ClockThread extends Thread {


    @Override
    public void run() {
        while (true) {
            boolean changed = false;
            if (putChess == playerPutChess && gameFlag) {
                if (playerType == ChessType.BLACK) {
                    Clock.BlackTime++;
                } else {
                    Clock.WhiteTime++;
                }
                changed = true;
            } else if (putChess == aiPutChess && gameFlag) {
                if (aiType == ChessType.BLACK) {
                    Clock.BlackTime++;
                } else {
                    Clock.WhiteTime++;
                }
                changed = true;
            }
            if (changed && gamePanel != null) {
                gamePanel.repaint();
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var2) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

