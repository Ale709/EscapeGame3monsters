package com.ale059.escapegame3monsters;

public class AProgressEvent {

    public String ID = "";
    public int InitialValue = 0;
    public int Value = 0;

    public AProgressEvent(String psID, int pnInitValue)
    {
        ID = psID;
        InitialValue = Value = pnInitValue;
    }

}
