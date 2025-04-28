package main.java.com.ztydwz.gobang2022.View;

import main.java.com.ztydwz.gobang2022.Model.GameDialog;
import main.java.com.ztydwz.gobang2022.Model.GamePanel;
import main.java.com.ztydwz.gobang2022.Model.MenuBar;
import main.java.com.ztydwz.gobang2022.Model.Static;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        Static.gameFrame = this;
        GamePanel gamePanel = new GamePanel(this);
        add(gamePanel);
        GameDialog gameDialog = new GameDialog(this);
        this.setJMenuBar(new MenuBar());
        setTitle("黑白连珠-3.00");
        setSize(800, 800);
        setVisible(true);
        setLocationRelativeTo(null);  //居中
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置关闭模式
    }
}
