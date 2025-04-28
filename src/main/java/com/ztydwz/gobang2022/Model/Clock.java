package main.java.com.ztydwz.gobang2022.Model;

import main.java.com.ztydwz.gobang2022.Service.ClockThread;


public class Clock {
    public static int BlackTime = 0;
    public static int WhiteTime = 0;
    public static ClockThread clockThread = new ClockThread();

    public Clock() {
    }
}
