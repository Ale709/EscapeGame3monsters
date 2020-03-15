package com.ale059.escapegame3monsters;

public class APuzzle {

    protected AScenePuzzle Scene = null;
    protected String AnswerCode = "";
    protected String InitialCode = "";
    protected String CurrentCode = "";

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
}
