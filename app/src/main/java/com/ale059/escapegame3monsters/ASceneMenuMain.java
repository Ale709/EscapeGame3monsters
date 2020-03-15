package com.ale059.escapegame3monsters;

public class ASceneMenuMain extends AScene {

    public ASceneMenuMain()
    {
        addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);
        //addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);
        addSprite("logo", R.drawable.logo,  0, 0, true, false);
        addSprite("menu_new", R.drawable.menu_new, 326, 440, true, true);
        addSprite("menu_continue", R.drawable.menu_continue, 217, 595, true, true);
        addSprite("menu_continue_d", R.drawable.menu_continue_d, 217, 595, false, false);
        addSprite("menu_about", R.drawable.menu_about, 278, 762, true, true);
        addSprite("menu_music", R.drawable.icon_music, 593, 981, true, true);
        addSprite("menu_music_off", R.drawable.icon_off, 565, 966, true, true);
        addSprite("menu_sound", R.drawable.icon_sound, 311, 977, true, true);
        addSprite("menu_sound_off", R.drawable.icon_off, 284, 966, true, true);

        //addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("menu_new"))
            app.SceneMenu = null;
        else if (sID.equals("speech_1"))
            ;

    }

}
