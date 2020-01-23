package com.ale059.escapegameplasticineworld1;


public class APuzzle_Chest1 extends APuzzle_Code {

    @Override
    public void Init(){
        AnswerCode = "6257";
        InitialCode = "0000";
        CurrentCode = InitialCode;
        LogoResID = R.drawable.s3_chest;

        SymbolsSets = new String[4];
        SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SYMBOLS_SET_123;
        //SymbolsSets[4] = APuzzle.SYMBOLS_SET_123;

    }

    @Override
    protected AScenePuzzle createScene()
    {
        if (Solved == false)
            return new AScenePuzzle_Code(this);
        else {
            AScene_ChestContent oScene = new AScene_ChestContent();
            oScene.putItem( "shovel" );
            oScene.putItem( "rope" );
            return oScene;
        }
    }

    @Override
    protected void onPuzzleSolved()
    {
        this.Solved = true;
        Scene = null;
        app.egHidePuzzle();
    }

    @Override
    protected void onPuzzleFailed()
    {

    }

}
