//package main.java.com.ztydwz.gobang2022.Service;
//
//import main.java.com.ztydwz.gobang2022.View.GameFrame;
//
//import javax.swing.*;
//
//public class FiveDa {
//    private void fiveda(GameFrame myFrame) {           //五手Nda
//
//        if (Aitype == 1) {
//            JOptionPane.showMessageDialog(myFrame, "电脑打点数为" + n);
//        }
//        num++;
//        myAI ai = new myAI();
//        int[] arr = ai.fiveNda(AI.Map, n);
//        for (int i = 0; i < arr.length; i += 2) {
//            Pointer pointer = pointers[arr[i]][arr[i + 1]];
//            Chess chess = new Chess(pointer.getX(), pointer.getY(), Aitype);
//            chess.num = GamePanel.num;
//            chesses.add(chess);
//        }
//        JOptionPane.showMessageDialog(myFrame, "电脑进行打点,请点击要保留的子");
//    }
//}
