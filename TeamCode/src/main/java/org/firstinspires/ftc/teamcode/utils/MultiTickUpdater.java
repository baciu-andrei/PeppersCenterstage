package org.firstinspires.ftc.teamcode.utils;

public class MultiTickUpdater {
    private int MaxTrashHold;
    private int ticks_passed;
    public MultiTickUpdater(int maxTrashHold){
        MaxTrashHold = maxTrashHold;
        ticks_passed = 0;
    }

    public void update(){
        if(ticks_passed <= MaxTrashHold + 3)
            ++ ticks_passed;
    }
    public void reset(){
        ticks_passed = 0;
    }

    public boolean getState(){
        return ticks_passed >= MaxTrashHold;
    }
    public int getTicks(){return ticks_passed;}
}
