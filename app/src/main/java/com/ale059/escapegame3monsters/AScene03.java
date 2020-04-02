package com.ale059.escapegame3monsters;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AScene03 extends AScene {

    //protected ATimer mTimer = new ATimer();

    public AScene03()
    {
        ASprite oSprite = null;
        addSprite("bg", R.drawable.s3_bg,       0,   0, true, false);
        addSprite("ship", R.drawable.s3_ship, 32, 438, false, false);
        addSprite("skeleton_fishing", R.drawable.s3_skeleton_fishing, 163, 234, false, false);
        addSprite("skeleton_sit2",R.drawable.s3_skeleton_hat, 559,  365, false, false);
        addSprite("hat", 0, 581,  643,207,143, false, true);
        addSprite("skeleton_sit",R.drawable.s3_skeleton_sit, 559,  316, false, true);

        addSprite("chair", R.drawable.s3_chair,   580, 678, true, false);
        addSprite("chest", R.drawable.s3_chest, 99, 699, false, false);

        addSprite("flag",R.drawable.s3_flag, 450, 582, false, false);

        addSprite("delay_hat",0, 0, 0, false, false);
        addSprite("delay_fishing",0, 0, 0, false, false);

        //addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        ////addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
        addControlButtons("next_scene");

        addSprite("hat_bg",R.drawable.s3hat_bg, 0, 0, false, true);
        //addSprite("exit", R.drawable.inv_down, 422, 904, false, true);
        addControlButtons("exit,menu,hint");
        showAndHideSprites("", "exit", 0);

    }

    @Override
    public void onShow() {

        if (app.egGetProgressEventValue("s1_bone_give")==1)
            if (app.egGetProgressEventValue("s3_skeleton_appear")==0) {
                app.egPlaySound( R.raw.snd_bones );
                this.showAndHideSprites("skeleton_sit", "chair", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s3_skeleton_appear", 1);
            }

        super.onShow();
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY) {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            //app.egMoveToScene(app.SCENE02);
        }
        else if (sID.equals("next_scene"))
        {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE02);
        }
        else if (sID.equals("hat")) {
            showAndHideSprites("hat_bg,exit", "", 0);
            app.egSetProgressEventValue("s3_chest2_hint", 1);
        }
        else if (sID.equals("exit")) {
            //app.egPlaySound( R.raw.snd_tap );
            showAndHideSprites("", "hat_bg,exit", 0);
        }
        else if (sID.equals("chair"))
        {
            //this.showAndHideSprites("skeleton_sit", "chair", ViewMain.TIME_ANIMATE);
        }
        else if (sID.equals("skeleton_sit"))
        {
            if (app.egIsSelectedItem("frodworm")) {
                this.showAndHideSprites("skeleton_fishing", "skeleton_sit", ViewMain.TIME_ANIMATE);
                this.showAndHideSprites("delay_fishing", "skeleton_sit", 3000);
                app.egRemoveFromInventory("frodworm");
            }
            else if (app.egIsSelectedItem("flag")) {
                this.showAndHideSprites("flag", "", ViewMain.TIME_ANIMATE);
                app.egRemoveFromInventory("flag");
            }
            else {
                //this.setTouchableSprites("", "flag");
                this.showAndHideSprites("skeleton_sit2,hat", "skeleton_sit", ViewMain.TIME_ANIMATE);
            }
            //mTimer.StartTimer(5000, onTimerSkeletonHat);
        }
        else if (sID.equals("flag"))
        {
            app.egAddToInventory("flag");
            this.showAndHideSprites("", "flag,ship", ViewMain.TIME_ANIMATE);
        }
        else if (sID.equals("chest"))
        {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_CHEST2 );
            if (oPuzzle != null)
                if (oPuzzle.Solved == true)
                    app.egPlaySound( R.raw.snd_chest_open );
            app.egShowPuzzle(oPuzzle);
        }

        super.onSpriteTouch(poSprite, pX, pY);
    }

    @Override
    public void onAnimationShowFinished( ASprite poSprite ) {
        if (poSprite == null)
            return;
        String sSpriteID = poSprite.ID;
        if (sSpriteID == null)
            return;

        if (sSpriteID.equals("skeleton_sit2"))
            showAndHideSprites("delay_hat", "", 3000);
        else if (sSpriteID.equals("delay_hat"))
            showAndHideSprites("skeleton_sit", "delay_hat,skeleton_sit2,hat", ViewMain.TIME_ANIMATE);
        else if (sSpriteID.equals("delay_fishing")) {
            showAndHideSprites("skeleton_sit", "delay_fishing,skeleton_fishing", ViewMain.TIME_ANIMATE);

            app.egAddToInventory("stick");
            app.egAddToInventory("fish");
            app.egSetProgressEventValue("s3_skeleton_fish", 1);
            //app.egShowItem("fish");
            //if (app.SceneItemView.Visible)
            //    app.SceneItemView.ShowItem( oNewItem );
            //SelectedIndex = ListItems_Hold.indexOf( oNewItem );
        }
        else if (sSpriteID.equals("flag")) {
            showAndHideSprites("ship", "", ViewMain.TIME_ANIMATE*2);
        }
        else if (sSpriteID.equals("ship")) {
            if (app.egGetProgressEventValue("s3_flag_put")==0)
                showAndHideSprites("chest", "", ViewMain.TIME_ANIMATE);
            else
            {
                //showAndHideSprites("", "ship", ViewMain.TIME_ANIMATE*2);
                this.setTouchableSprites("chest,flag", "");
            }

        }
        else if (sSpriteID.equals("chest")) {
            showAndHideSprites("", "ship", ViewMain.TIME_ANIMATE*2);
            this.setTouchableSprites("chest,flag", "");
            app.egSetProgressEventValue("s3_flag_put", 1);
        }
    }

}
