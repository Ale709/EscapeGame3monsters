package com.ale059.escapegame3monsters;


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

        SAVEID = "PuzzleChest1";
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
            //oScene.putItem( "potion" );
            //oScene.putItem( "ring" );
            return oScene;
        }
    }

    @Override
    protected void onPuzzleSolved()
    {
        if (app.egGetProgressEventValue("s5_banner_look")==1) {
            this.Solved = true;
            Scene = null;
            app.egSetProgressEventValue("s2_chest1_open", 1);
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
            mLocalScene.Item1 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item1", "shovel" )  );
            mLocalScene.Item2 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item2", "rope" )  );
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
