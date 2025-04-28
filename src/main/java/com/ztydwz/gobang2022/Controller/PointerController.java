package main.java.com.ztydwz.gobang2022.Controller;


import main.java.com.ztydwz.gobang2022.Model.Pointer;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class PointerController {

    public PointerController() {


    }


    public void changePointerShow(int x, int y) {
        Pointer pointer;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pointer = pointers[i][j];
                pointer.setShow(pointer.isShow(x, y) && !pointer.isHasChess());//判断指示器位置是否正确
            }
        }
    }

}
