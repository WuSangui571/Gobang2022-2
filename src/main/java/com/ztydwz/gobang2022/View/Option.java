package main.java.com.ztydwz.gobang2022.View;

import javax.swing.*;

import static main.java.com.ztydwz.gobang2022.Model.Static.fiveDaNumber;
import static main.java.com.ztydwz.gobang2022.Model.Static.gameFrame;

public class Option {
    public int createExchange() {
        int option = JOptionPane.showConfirmDialog(gameFrame, "是否选择三手交换?");
        return option;
    }

    public void createFiveDaNumber() {
        JOptionPane.showMessageDialog(gameFrame, "电脑打点数为" + fiveDaNumber);
    }

    public void createFiveDaOption() {
        JOptionPane.showMessageDialog(gameFrame, "电脑进行打点,请点击要保留的子");
    }

    public void createInputFiveDaNumber() {
        String num = JOptionPane.showInputDialog(gameFrame, "请输入打点数");
        fiveDaNumber = Integer.parseInt(num);
    }

    public void createOption(String message) {
        JOptionPane.showMessageDialog(gameFrame, message);
    }

}
