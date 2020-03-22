package com.ale059.escapegame3monsters;

public class APuzzle {

    protected AScenePuzzle Scene = null;
    protected String AnswerCode = "";
    protected String InitialCode = "";
    protected String CurrentCode = "";

    protected String SAVEID = "Puzzle";


    public boolean Solved = false;


    public AScenePuzzle getScene()
    {
        if (Scene != null)
            return Scene;

        Scene = createScene();

        return Scene;
    }

    protected AScenePuzzle createScene()
    {
        return null;
    }

    public int getCodeLength()
    {
        return AnswerCode.length();
    }

    public boolean isAnswerCorrect()
    {
        return CurrentCode.equals(AnswerCode);
    }

    protected void onPuzzleSolved()
    {
    }

    protected void onPuzzleFailed()
    {
        app.egPlaySound( R.raw.snd_code_wrong );
    }


    public void RestoreFromSaved()
    {
        Solved = (1==app.egReadSingleSettingInt( SAVEID+"_Solved", (Solved?1:0) ));
        AnswerCode = app.egReadSingleSettingString( SAVEID+"_AnswerCode", AnswerCode );
        CurrentCode = app.egReadSingleSettingString( SAVEID+"_CurrentCode", CurrentCode );
    }

    public void SaveToSaved()
    {
        app.egWriteSingleSetting( SAVEID+"_Solved", (this.Solved?1:0) );
        app.egWriteSingleSetting( SAVEID+"_AnswerCode", AnswerCode );
        app.egWriteSingleSetting( SAVEID+"_CurrentCode", CurrentCode );
    }

}

