package com.ale059.escapegame3monsters;

public class APuzzle_Sequence extends APuzzle {

    public APuzzle_Sequence(String psSequence)
    {
        AnswerCode = psSequence;
    }
    public void addSequenceChar(String s)
    {
        CurrentCode += s;
    }
    public void trimToAnswerLength()
    {
        int nDstLen = AnswerCode.length();
        int nCurLen = CurrentCode.length();
        if (nCurLen > nDstLen)
        {
            CurrentCode = CurrentCode.substring(nCurLen-nDstLen);
        }
    }

}
