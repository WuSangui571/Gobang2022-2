package main.java.com.ztydwz.gobang2022.Model;


import main.java.com.ztydwz.gobang2022.Controller.DesignatedStartController;
import main.java.com.ztydwz.gobang2022.Controller.FreeStartController;
import main.java.com.ztydwz.gobang2022.Controller.TwoAiBattleController;
import main.java.com.ztydwz.gobang2022.Controller.TwoPlayerBattleController;
import main.java.com.ztydwz.gobang2022.Service.DrawService;

import javax.swing.*;
import java.awt.*;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;


public class GamePanel extends JPanel {

    public JFrame GameFrame;

    public GamePanel(JFrame frame) {
        Init();
        GameFrame = frame;
        gamePanel = this;
    }


    public void Init() {
        this.setLayout(null);             //取消默认的流式布局
        createPointers();
        createButton();

    }

    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);
        DrawService drawService = new DrawService();
        drawService.draw(graphics);
        repaint();


    }

    public void createListenMouseListener() {
        //指示器监听器
        if (gameMode == GameMode.freeStart) {
            FreeStartController freeStartController = new FreeStartController();
            this.addMouseListener(freeStartController);
            this.addMouseMotionListener(freeStartController);
        }
        if (gameMode == GameMode.designatedStart) {
            DesignatedStartController designatedStartController = new DesignatedStartController();
            this.addMouseListener(designatedStartController);
            this.addMouseMotionListener(designatedStartController);
        }

        if (gameMode == GameMode.twoPlayerBattle) {
            TwoPlayerBattleController twoPlayerBattleController = new TwoPlayerBattleController();
            this.addMouseListener(twoPlayerBattleController);
            this.addMouseMotionListener(twoPlayerBattleController);
            this.addMouseMotionListener(twoPlayerBattleController);
        }
        if (gameMode == GameMode.twoAiPlayerBattle) {
            TwoAiBattleController twoAiPlayerBattle = new TwoAiBattleController();
            this.addMouseListener(twoAiPlayerBattle);
            this.addMouseMotionListener(twoAiPlayerBattle);
            this.addMouseMotionListener(twoAiPlayerBattle);
        }


    }

    private void createButton() {                                    //创建按钮
        GameButton button = new GameButton(this, GameFrame);
    }


    private void createPointers() {                   //指示器数组填入数据
        Pointer pointer;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int x = j * distance + start;
                int y = i * 40 + start;
                pointer = new Pointer(x, y);
                pointers[i][j] = pointer;
            }
        }
    }


}
