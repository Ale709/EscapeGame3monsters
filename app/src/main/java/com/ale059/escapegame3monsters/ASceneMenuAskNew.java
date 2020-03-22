package com.ale059.escapegame3monsters;

public class ASceneMenuAskNew extends AScene {

    public ASceneMenuAskNew()
    {
        addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);
        //addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);
        addSprite("board", R.drawable.board,  113, 373, 793, 447, true, false);
        addSprite("text", R.drawable.text_ask_new, 113, 373, 793, 447, true, false);
        addSprite("btn_ok", R.drawable.icon_ok, 290+248, 624, 166, 157, true, true);
        addSprite("btn_cancel", R.drawable.icon_cancel, 290, 624, 166, 157, true, true);

        //addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("btn_ok")) {
            app.egResetGame();
            app.egOpenMenuScene(0);
            //app.SceneMenu = null;
        }
        else if (sID.equals("btn_cancel"))
            app.egOpenMenuScene( app.SCENE_MENU_MAIN );


        super.onSpriteTouch(poSprite, pX, pY);
    }

}
