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
        while (true) {
            String num = JOptionPane.showInputDialog(gameFrame, "请输入打点数 (2~5)");
            if (num == null) {
                return;
            }
            try {
                int value = Integer.parseInt(num);
                if (value < 2 || value > 5) {
                    JOptionPane.showMessageDialog(gameFrame, "打点数必须在 2~5 之间", "输入错误", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                fiveDaNumber = value;
                return;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(gameFrame, "请输入有效的数字", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void createOption(String message) {
        JOptionPane.showMessageDialog(gameFrame, message);
    }

}
