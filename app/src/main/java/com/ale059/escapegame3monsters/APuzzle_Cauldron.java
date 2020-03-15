package com.ale059.escapegame3monsters;

public class APuzzle_Cauldron extends APuzzle {

    protected AScene_CauldronContent mLocalScene = null;

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
        //}
    }

    public class AScene_CauldronContent extends AScene_ChestContent {

        @Override
        protected void UpdateWhat2Draw()
        {
            int n = mSprites2Draw.size();

            super.UpdateWhat2Draw();

            if (n == 0) {
                int nWidth = 160;
                int x = (ViewMain.SIZE_IN_MEMORY - 3*(nWidth+1) )/2;
                int y = 500;

                addSprite("btn_check", R.drawable.puz_btn_long, x, y + 2*nWidth, 3*nWidth+2, 80, true, true);
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
