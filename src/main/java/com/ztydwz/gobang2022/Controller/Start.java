package main.java.com.ztydwz.gobang2022.Controller;

import main.java.com.ztydwz.gobang2022.Model.Clock;
import main.java.com.ztydwz.gobang2022.View.GameFrame;

import java.awt.*;

public class Start {
    public static void main(String[] args) {
        Clock.clockThread.start();
        EventQueue.invokeLater(() -> {
            new GameFrame();
        });
    }
}
