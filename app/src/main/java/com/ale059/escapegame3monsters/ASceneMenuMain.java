package com.ale059.escapegame3monsters;

import android.content.Intent;

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
        addSprite("menu_music_off", R.drawable.icon_off, 565, 966, false, false);
        addSprite("menu_sound", R.drawable.icon_sound, 311, 977, true, true);
        addSprite("menu_sound_off", R.drawable.icon_off, 284, 966, false, false);

        //addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
    }

    @Override
    public void onShow()
    {
        if (0==app.IsMusicOn)
            showAndHideSprites("menu_music_off", "", 0);
        if (0==app.IsSoundOn)
            showAndHideSprites("menu_sound_off", "", 0);

        if (app.egGetProgressEventValue("have_game_progress")==1)
            showAndHideSprites("menu_continue", "menu_continue_d", 0);
        else
            showAndHideSprites("menu_continue_d", "menu_continue", 0);

        super.onShow();
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("menu_new")) {
            //app.egResetGame();
            //app.egOpenMenuScene(0);
            if (app.egGetProgressEventValue("have_game_progress")==1)
                app.egOpenMenuScene( app.SCENE_MENU_ASK_NEW );
            else
            {
                app.egResetGame();
                app.egOpenMenuScene(0);
            }

        }
        else if (sID.equals("menu_continue"))
            app.egOpenMenuScene( 0 );
            //app.SceneMenu = null;
        else if (sID.equals("menu_about"))
            app.egOpenMenuScene( app.SCENE_MENU_THEEND );

        else if (sID.equals("menu_music"))
        {
            app.IsMusicOn = (app.IsMusicOn==0 ? 1 : 0);
            if (app.IsMusicOn==0) {
                showAndHideSprites("menu_music_off", "", 0);
                ABgMusicService.StopPlayback();
            }
            else if (app.IsMusicOn==1) {
                showAndHideSprites("", "menu_music_off", 0);
                ABgMusicService.StartPlayback();
            }
            app.egWriteSingleSetting("Setting_Music_On", app.IsMusicOn );
        }
        else if (sID.equals("menu_sound"))
        {
            app.IsSoundOn = (app.IsSoundOn==0 ? 1 : 0);
            if (app.IsSoundOn==0) {
                showAndHideSprites("menu_sound_off", "", 0);
            }
            else if (app.IsSoundOn==1) {
                showAndHideSprites("", "menu_sound_off", 0);
            }
            app.egWriteSingleSetting("Setting_Sound_On", app.IsSoundOn );
        }

    }

}
