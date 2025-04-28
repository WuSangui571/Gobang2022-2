package main.java.com.ztydwz.gobang2022.Model;

import main.java.com.ztydwz.gobang2022.View.Option;

import javax.swing.*;
import java.awt.*;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class GameDialog extends JDialog {
    Font font = new Font("宋体", Font.BOLD, 15);

    public GameDialog(Frame frame) {
        super(frame);

        JPanel dialogPanel = new JPanel();
        this.setLocationRelativeTo(null);  //居中
        dialogPanel.setLayout(null);             //取消默认的流式布局

        JLabel label1 = new JLabel("玩家先后手选择:");
        JRadioButton choose1 = new JRadioButton("先手");
        JRadioButton choose2 = new JRadioButton("后手");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(choose1);                 //按钮组，只有这样才能互斥
        group1.add(choose2);
        label1.setFont(font);
        label1.setBounds(0, 5, 100, 40);
        choose1.setBounds(150, 5, 100, 50);
        choose2.setBounds(250, 5, 100, 50);

        JLabel label2 = new JLabel("开局选择:");
        JRadioButton choose3 = new JRadioButton("人机指定开局");
        choose3.setSelected(true);
        JRadioButton choose4 = new JRadioButton("人机自由开局");
        JRadioButton choose5 = new JRadioButton("双人对战");
        JRadioButton choose501 = new JRadioButton("自动对战");

        ButtonGroup group2 = new ButtonGroup();
        group2.add(choose3);           //按钮组，只有这样才能互斥
        group2.add(choose4);
        group2.add(choose5);
        group2.add(choose501);
        label2.setFont(font);
        label2.setBounds(0, 60, 100, 40);           //这些都是自定义控件布局
        choose3.setBounds(150, 55, 100, 50);

        choose4.setBounds(250, 55, 100, 50);
        choose5.setBounds(350, 55, 100, 50);
        choose501.setBounds(425, 55, 100, 50);


        JLabel label3 = new JLabel("禁手检测:");
        JRadioButton choose6 = new JRadioButton("开启");
        choose6.setSelected(true);
        JRadioButton choose7 = new JRadioButton("关闭");
        ButtonGroup group3 = new ButtonGroup();
        group3.add(choose6);           // 按钮组，只有这样才能互斥
        group3.add(choose7);
        label3.setFont(font);
        label3.setBounds(0, 110, 100, 40);           //这些都是自定义控件布局
        choose6.setBounds(150, 105, 100, 50);
        choose6.setSelected(true);
        choose7.setBounds(250, 105, 100, 50);

        JLabel label4 = new JLabel("允许禁手:");
        JRadioButton choose8 = new JRadioButton("允许");
        choose6.setSelected(true);
        JRadioButton choose9 = new JRadioButton("拒绝");
        ButtonGroup group4 = new ButtonGroup();
        group4.add(choose8);           // 按钮组，只有这样才能互斥
        group4.add(choose9);
        label4.setFont(font);
        label4.setBounds(0, 160, 100, 40);           //这些都是自定义控件布局
        choose8.setBounds(150, 155, 100, 50);
        choose8.setSelected(true);
        choose9.setBounds(250, 155, 100, 50);


        JButton confirmation = new JButton("确定");     //确定按钮

        confirmation.addActionListener((e) -> {

            if (choose1.isSelected()) {     //先手
                first = whoFirst.player;
                playerType = ChessType.BLACK;
                aiType = ChessType.White;
                putChess = whoPutChess.playerPutChess;
            } else if (choose2.isSelected()) { //后手
                first = whoFirst.Ai;
                playerType = ChessType.White;
                aiType = ChessType.BLACK;
                putChess = whoPutChess.aiPutChess;
            } else if (!choose1.isSelected() && !choose2.isSelected()) {
                new Option().createOption("请选择先后手");
            }

            if (choose3.isSelected()) {
                gameMode = GameMode.designatedStart;
            } else if (choose4.isSelected()) {
                gameMode = GameMode.freeStart;
            } else if (choose5.isSelected()) {
                gameMode = GameMode.twoPlayerBattle;
            } else if (choose501.isSelected()) {
                gameMode = GameMode.twoAiPlayerBattle;
                aiType = ChessType.BLACK;
            }

            if (choose6.isSelected()) {
                ifForbiddenHandOpen = true;
            } else if (choose7.isSelected()) {
                ifForbiddenHandOpen = false;
            }

            if (choose8.isSelected()) {
                ifAllowForbiddenHandOpen = true;
                System.out.println("允许禁手");
            } else if (choose9.isSelected()) {
                ifAllowForbiddenHandOpen = false;
                System.out.println("拒绝禁手");
            }

            gamePanel.createListenMouseListener();
            this.setVisible(false);
        });

        confirmation.setBounds(165, 280, 60, 40);


        dialogPanel.add(label1);
        dialogPanel.add(choose1);
        dialogPanel.add(choose2);

        dialogPanel.add(label2);
        dialogPanel.add(choose3);
        dialogPanel.add(choose4);
        dialogPanel.add(choose5);
        dialogPanel.add(choose501);

        dialogPanel.add(label3);
        dialogPanel.add(choose6);
        dialogPanel.add(choose7);

        dialogPanel.add(label4);
        dialogPanel.add(choose8);
        dialogPanel.add(choose9);

        dialogPanel.add(confirmation);
        //add(dialog);
        this.add(dialogPanel);
    }

    public void View() {

    }
}

