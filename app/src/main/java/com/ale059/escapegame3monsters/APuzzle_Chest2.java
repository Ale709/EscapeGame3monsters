package com.ale059.escapegame3monsters;

public class APuzzle_Chest2 extends APuzzle_Code {

    @Override
    public void Init(){
        AnswerCode = "rbgyp";
        InitialCode = "ggggg";
        CurrentCode = InitialCode;
        LogoResID = R.drawable.s3_chest;

        SymbolsSets = new String[5];
        SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SymbolsSets[4] = "rygpb";
        //SymbolsSets[4] = APuzzle.SYMBOLS_SET_123;

        SAVEID = "PuzzleChest2";
    }

    @Override
    protected int getResourceForChar(char ch) {
        switch (ch) {
            case 'r': return R.drawable.ball_r;
            case 'y': return R.drawable.ball_y;
            case 'g': return R.drawable.ball_g;
            case 'p': return R.drawable.ball_p;
            case 'b': return R.drawable.ball_b;
        }
        return 0;
    }

    @Override
    protected AScenePuzzle createScene()
    {
        if (Solved == false)
            return new AScenePuzzle_Code(this);
        else {
            AScene_ChestContent oScene = new AScene_ChestContent();
            oScene.putItem( "ring" );
            return oScene;
        }
    }

    @Override
    protected void onPuzzleSolved()
    {
        if (app.egGetProgressEventValue("s3_chest2_hint")==1) {
            this.Solved = true;
            Scene = null;
            app.egSetProgressEventValue("s3_chest2_open", 1);
            //app.egHidePuzzle();
            app.egShowPuzzle( this ); // to switch view

            app.egPlaySound( R.raw.snd_chest_open );
        }
    }

    @Override
    public void RestoreFromSaved()
    {
        Solved = (1==app.egReadSingleSettingInt( SAVEID+"_Solved", (Solved?1:0) ));
        AnswerCode = app.egReadSingleSettingString( SAVEID+"_AnswerCode", AnswerCode );
        CurrentCode = app.egReadSingleSettingString( SAVEID+"_CurrentCode", CurrentCode );
        if (Solved)
        {
            AScene_ChestContent mLocalScene = (AScene_ChestContent)this.getScene();
            mLocalScene.Item1 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item1", "ring" )  );
            mLocalScene.Item2 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item2", "" )  );
            mLocalScene.Item3 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item3", "" )  );
        }
    }

    @Override
    public void SaveToSaved()
    {
        app.egWriteSingleSetting( SAVEID+"_Solved", (this.Solved?1:0) );
        app.egWriteSingleSetting( SAVEID+"_AnswerCode", AnswerCode );
        app.egWriteSingleSetting( SAVEID+"_CurrentCode", CurrentCode );
        if (Solved) {
            AScene_ChestContent mLocalScene = (AScene_ChestContent)this.getScene();

            AItem oItem;
            oItem = mLocalScene.Item1;
            app.egWriteSingleSetting( SAVEID+"_Item1", (oItem==null?"":oItem.ID) );
            oItem = mLocalScene.Item2;
            app.egWriteSingleSetting( SAVEID+"_Item2", (oItem==null?"":oItem.ID) );
            oItem = mLocalScene.Item3;
            app.egWriteSingleSetting( SAVEID+"_Item3", (oItem==null?"":oItem.ID) );
        }
    }
}
