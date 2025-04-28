package main.java.com.ztydwz.gobang2022.Model;

import main.java.com.ztydwz.gobang2022.Controller.ChessController;

import javax.swing.*;

public class GameButton {
    GamePanel panel;
    JFrame frame;

    public GameButton(GamePanel panel, JFrame frame) {
        this.panel = panel;
        this.frame = frame;
        int xStart = 640;
        int yStart = 10;
        int width = 90;
        int height = 40;

        JButton start = new JButton("开始游戏");
        start.setBounds(xStart, yStart, width, height);          //设置按钮位置
        start.addActionListener((e) -> {
            Static.gameFlag = true;
        });

        JButton setting = new JButton("游戏设置");
        setting.setBounds(xStart, 60, width, height);
        setting.addActionListener((e) -> {
            GameDialog dialog = new GameDialog(frame);
            dialog.setTitle("游戏设置");
            dialog.setModal(true);                       // 对话框设置为模态的（阻塞模式）
            dialog.setSize(500, 500);
            dialog.setVisible(true);
        }); //lambda 表达式 setting监听器


        JButton instructions = new JButton("游戏说明");
        instructions.setBounds(xStart, 110, width, height);


        JButton retract = new JButton("悔棋");
        retract.setBounds(xStart, 160, width, height);
        retract.addActionListener((e) -> {
            new ChessController().restract();
        });

        panel.add(start);
        panel.add(setting);
        panel.add(instructions);
        panel.add(retract);
    }


}
