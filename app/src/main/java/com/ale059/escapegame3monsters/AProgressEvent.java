package com.ale059.escapegame3monsters;

public class AProgressEvent {

    public String ID = "";
    public int InitialValue = 0;
    public int Value = 0;
    public int HintResID = 0;
    public boolean IsHintAvailable = false;

    public AProgressEvent(String psID, int pnInitValue)
    {
        ID = psID;
        InitialValue = Value = pnInitValue;
    }

    public AProgressEvent addHint( int pnResID )
    {
        HintResID = pnResID;

        return this;
    }
}
