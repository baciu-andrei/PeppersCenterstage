package org.firstinspires.ftc.teamcode.utils;

public class MultiTickUpdater {
    private static int MaxTrashHold;
    private int ticks_passed;
    public MultiTickUpdater(int maxTrashHold){
        MaxTrashHold = maxTrashHold;
        ticks_passed = 0;
    }

    public void update(){
        ++ ticks_passed;
    }
    public void reset(){
        ticks_passed = 0;
    }

    public boolean getState(){
        return ticks_passed >= MaxTrashHold;
    }
}
