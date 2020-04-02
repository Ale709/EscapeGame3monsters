package com.ale059.escapegame3monsters;

public class ASceneMenuAbout extends AScene {

    public ASceneMenuAbout()
    {
        addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);

        addSprite("bg_about", R.drawable.menu_about_bg,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);


        addSprite("btn_rate", R.drawable.icon_rate1, 315, 966, 142, 142, true, true);
        addSprite("btn_menu", R.drawable.icon_menu, 577, 966, 140, 156, true, true);
    }

    @Override
    public void onShow()
    {
        showAndHideSprites("heart_1", "", ViewMain.TIME_ANIMATE*2);
        showAndHideSprites("heart_5", "", ViewMain.TIME_ANIMATE);
    }

    @Override
    public void onAnimationShowFinished( ASprite poSprite ) {
        if (poSprite == null)
            return;
        String sSpriteID = poSprite.ID;
        if (sSpriteID == null)
            return;

        if (sSpriteID == "heart_1")
            showAndHideSprites("heart_2", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_2")
            showAndHideSprites("heart_3", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_3")
            showAndHideSprites("heart_4", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_4")
            showAndHideSprites("heart_5", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_5")
            showAndHideSprites("heart_6", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_6")
            showAndHideSprites("heart_7", sSpriteID, ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "heart_7")
            showAndHideSprites("heart_1", sSpriteID, ViewMain.TIME_ANIMATE*2);

    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("btn_rate")) {
            app.egRateApp();
            //app.egResetGame();
            //app.egOpenMenuScene(0);
            //app.SceneMenu = null;
        }
        else if (sID.equals("btn_menu"))
            app.egOpenMenuScene( app.SCENE_MENU_MAIN );


        super.onSpriteTouch(poSprite, pX, pY);
    }

}
