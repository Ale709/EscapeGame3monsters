package com.ale059.escapegame3monsters;

public class APuzzle_Cauldron extends APuzzle {

    protected AScene_CauldronContent mLocalScene = null;

    public APuzzle_Cauldron() {
        SAVEID = "PuzzleCauldron";
    }

    @Override
    protected AScenePuzzle createScene()
    {
        if (mLocalScene == null) {
            mLocalScene = new AScene_CauldronContent();
            // TODO:
            mLocalScene.LogoResID = R.drawable.small_cauldron;
            //oScene.putItem( "shovel" );
            //oScene.putItem( "rope" );
        }
        return mLocalScene;
    }

    public boolean isAnswerCorrect()
    {
        if (mLocalScene==null)
            return false;
        if (mLocalScene.containsItem("mushroom"))
            if (mLocalScene.containsItem("apple_b"))
                if (mLocalScene.containsItem("worm"))
                    return true;

        return false;
    }

    @Override
    protected void onPuzzleSolved()
    {
        //if (app.egGetProgressEventValue("s5_banner_look")==1) {
            this.Solved = true;
            Scene = null;
            app.egPlaySound( R.raw.snd_boilering );
            app.egSetProgressEventValue("s5_ingridients", 1);
            app.egHidePuzzle();
            if (app.ActiveScene != null)
                app.ActiveScene.onShow();

        //}
    }

    @Override
    public void RestoreFromSaved()
    {
        Solved = (1==app.egReadSingleSettingInt( SAVEID+"_Solved", (Solved?1:0) ));
        AnswerCode = app.egReadSingleSettingString( SAVEID+"_AnswerCode", AnswerCode );
        CurrentCode = app.egReadSingleSettingString( SAVEID+"_CurrentCode", CurrentCode );
        createScene();
        if (mLocalScene != null) {
            mLocalScene.Item1 = app.Inventory.getItemByID( app.egReadSingleSettingString( SAVEID+"_Item1", "" )  );
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
        if (mLocalScene != null) {
            AItem oItem;
            oItem = mLocalScene.Item1;
            app.egWriteSingleSetting( SAVEID+"_Item1", (oItem==null?"":oItem.ID) );
            oItem = mLocalScene.Item2;
            app.egWriteSingleSetting( SAVEID+"_Item2", (oItem==null?"":oItem.ID) );
            oItem = mLocalScene.Item3;
            app.egWriteSingleSetting( SAVEID+"_Item3", (oItem==null?"":oItem.ID) );
        }
    }


    public class AScene_CauldronContent extends AScene_ChestContent {

        @Override
        protected void UpdateWhat2Draw()
        {
            int n = mSprites2Draw.size();

            super.UpdateWhat2Draw();

            if (n == 0) {
                //int nWidth = 160;
                //int x = (ViewMain.SIZE_IN_MEMORY - 3*(nWidth+1) )/2;
                //int y = 500;
                //addSprite("btn_check", R.drawable.puz_btn_long, x, y + 2*nWidth, 3*nWidth+2, 80, true, true);

                int nOkBtnWidth = 320;
                int nOkBtnHeight = 160;
                int y0 = 782, spy=30;
                int x0 = (ViewMain.SIZE_IN_MEMORY-nOkBtnWidth*2)/3;
                ASprite oSprite;
                if (null != (oSprite = this.getSpriteByID("exit")))
                    oSprite.X = x0;
                if (null != (oSprite = this.getSpriteByID("icon_cancel")))
                    oSprite.X = x0+(nOkBtnWidth-(nOkBtnHeight-spy))/2;

                x0 += x0 + nOkBtnWidth;
                addSprite("btn_check", R.drawable.puz_btn_long, x0, y0, nOkBtnWidth, nOkBtnHeight, true, true);
                addSprite("icon_ok", R.drawable.icon_ok, x0+(nOkBtnWidth-(nOkBtnHeight-spy))/2, y0+spy/2, nOkBtnHeight-spy, nOkBtnHeight-spy, true, false);
            }
        }

        @Override
        public void onSpriteTouch(ASprite poSprite, int pX, int pY)
        {
            if (poSprite == null)
                return;

            super.onSpriteTouch(poSprite, pX, pY);

            String sID = poSprite.ID;
            if (sID.equals("btn_check")) {
                if (isAnswerCorrect() == true)
                    onPuzzleSolved();
                else
                    onPuzzleFailed();
            }

        }

    }

}
