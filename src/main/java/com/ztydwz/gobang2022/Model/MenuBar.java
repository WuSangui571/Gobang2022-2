package main.java.com.ztydwz.gobang2022.Model;


import main.java.com.ztydwz.gobang2022.Service.ExportRecord;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        JMenu Menu1 = new JMenu("游戏");
        JMenu Menu2 = new JMenu("帮助");
        JMenu Menu3 = new JMenu("关于");

        //游戏
        JMenuItem Menu1_item1 = new JMenuItem("新游戏");
        JMenuItem Menu1_item2 = new JMenuItem("退出");
        JMenuItem Menu1_item3 = new JMenuItem("导出棋谱");
        Menu1_item3.addActionListener(e -> {
            new ExportRecord();
        });


        JMenuItem Menu2_item1 = new JMenuItem("操作帮助");
        JMenuItem Menu2_item2 = new JMenuItem("胜利条件");

        JMenuItem Menu3_item1 = new JMenuItem("开发人员选项");
        JMenuItem Menu3_item2 = new JMenuItem("版本控制");

        Menu1.add(Menu1_item1);
        Menu1.add(Menu1_item2);
        Menu1.add(Menu1_item3);

        Menu2.add(Menu2_item1);
        Menu2.add(Menu2_item2);

        Menu3.add(Menu3_item1);
        Menu3.add(Menu3_item2);


        this.add(Menu1);
        this.add(Menu2);
        this.add(Menu3);

    }

    public void addMenu(String name) {
        JMenu jMenu = new JMenu(name);
        this.add(jMenu);
    }
}
