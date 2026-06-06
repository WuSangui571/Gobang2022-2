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
        instructions.addActionListener((e) -> {
            JOptionPane.showMessageDialog(frame,
                "五子棋人机对弈程序\n\n"
                + "1. 点击「游戏设置」选择先后手和游戏模式\n"
                + "2. 点击「开始游戏」开始对弈\n"
                + "3. 鼠标点击棋盘交叉点落子\n"
                + "4. 悔棋按钮可撤销上一步\n"
                + "5. 游戏结束后可通过菜单导出棋谱",
                "游戏说明",
                JOptionPane.INFORMATION_MESSAGE);
        });


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
