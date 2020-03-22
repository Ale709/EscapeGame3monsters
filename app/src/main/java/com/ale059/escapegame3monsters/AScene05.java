package com.ale059.escapegame3monsters;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AScene05 extends AScene {


    public AScene05()
    {
        addSprite("bg", R.drawable.s5_bg,       0,   0, true, false);
        addSprite("cheese", R.drawable.s5_cheese, 127, 621, true, true);
        addSprite("dagger", R.drawable.s5_dagger, 267, 643, true, true);
        addSprite("banner",R.drawable.s5_banner, 302,  91, true, true);
        addSprite("banner2",R.drawable.s5_banner2, 302,  91, false, false);

        addSprite("cat", R.drawable.s5_cat,   311, 704, false, true);
        addSprite("broom", R.drawable.s5_broom, 653, 449, false, true);

        addSprite("witch",0, 734, 430,178,428, true, true);
        addSprite("pinkwater",R.drawable.s5_pinkwater, 424, 571, false, true);
        addSprite("cauldron",0, 410, 577,234,230, true, true);
        addSprite("potion",R.drawable.s5_potion, 850, 731,60,99, false, true);

        addSprite("spcloud",R.drawable.s5_spcloud, 361, 245, false, false);
        addSprite("spcloud2",R.drawable.s5_spcloud2, 361, 245, false, false);

        ////addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        //addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
        addControlButtons("prev_scene");

        addSprite("cat_medal",R.drawable.s5_cat_medal, 0, 0, false, true);
        //addSprite("exit", R.drawable.inv_down, 422, 904, false, true);
        addControlButtons("exit,menu,hint");
        showAndHideSprites("", "exit", 0);

//        String sToShow = "", sToHide = "", sSprite;
//        sSprite = "cheese"; if (app.egGetProgressEventValue("s5_cheese_take")==0) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "dagger"; if (app.egGetProgressEventValue("s5_dagger_take")==0) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "banner"; if (app.egGetProgressEventValue("s5_banner_take")==0) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "cat"; if (app.egGetProgressEventValue("s5_cat_appear")==1) sToShow+=sSprite+","; else sToHide += sSprite+",";
//
//        boolean bSpcloud2 = false;
//        boolean bPinkWater = false;
//        boolean bPotion = false;
//        boolean bBroom = false;
//
//        if (app.egGetProgressEventValue("s5_scroll_give")==1) {
//            bSpcloud2 = true;
//            bPinkWater = true;
//            if (app.egGetProgressEventValue("s5_ingridients")==1) {
//                bSpcloud2 = false;
//                if (app.egGetProgressEventValue("s5_potion_appear")==1) {
//                    bPotion = true;
//                    if (app.egGetProgressEventValue("s5_potion_take")==1) {
//                        bPotion = false;
//                        if (app.egGetProgressEventValue("s5_cat_appear")==1) {
//                            bBroom = true;
//                            if (app.egGetProgressEventValue("s5_broom_take") == 1) {
//                                bBroom = false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        sSprite = "spcloud2"; if (bSpcloud2) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "pinkwater"; if (bPinkWater) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "potion"; if (bPotion) sToShow+=sSprite+","; else sToHide += sSprite+",";
//        sSprite = "broom"; if (bBroom) sToShow+=sSprite+","; else sToHide += sSprite+",";
//
//        showAndHideSprites(sToShow, sToHide, 0);

    }

    @Override
    public void onShow() {

        if (app.egGetProgressEventValue("s5_ingridients")==1)
            if (app.egGetProgressEventValue("s5_potion_appear")==0) {
                this.showAndHideSprites("potion", "spcloud", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s5_potion_appear", 1);
            }

        if (app.egGetProgressEventValue("s1_fish_give")==1)
            if (app.egGetProgressEventValue("s5_cat_appear")==0) {
                this.showAndHideSprites("cat", "", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s5_cat_appear", 1);
            }

        super.onShow();
    }

    @Override
    public void onAnimationShowFinished( ASprite poSprite ) {
        if (poSprite == null)
            return;
        String sSpriteID = poSprite.ID;
        if (sSpriteID == null)
            return;

        if (sSpriteID == "spcloud2")
            showAndHideSprites("", "spcloud2",ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "spcloud")
            showAndHideSprites("", "spcloud",ViewMain.TIME_ANIMATE*2);
        else if (sSpriteID == "cat")
            showAndHideSprites("broom", "",ViewMain.TIME_ANIMATE);
        else if (sSpriteID == "banner2")
            showAndHideSprites("banner", "banner2", ViewMain.TIME_ANIMATE/2);

    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE04);
        }
        else if (sID.equals("next_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            //app.egMoveToScene(app.SCENE01);
        }
        else if (sID.equals("cat")) {
            showAndHideSprites("cat_medal,exit", "", 0);
            // TODO:
            //app.egSetProgressEventValue("s3_chest2_hint", 1);
        }
        else if (sID.equals("exit")) {
            showAndHideSprites("", "cat_medal,exit", 0);
        }
        else if (sID.equals("cheese")) {
            app.egAddToInventory( "cheese_worm" );
            app.egSetProgressEventValue("s5_cheese_take", 1);
            poSprite.setVisible(false, ViewMain.TIME_ANIMATE);
        }
        else if (sID.equals("dagger"))
        {
            app.egAddToInventory( "dagger" );
            app.egSetProgressEventValue("s5_dagger_take", 1);
            poSprite.setVisible(false, ViewMain.TIME_ANIMATE);
        }
        else if (sID.equals("banner"))
        {
            if (app.egIsSelectedItem("dagger") == true) {
                app.egAddToInventory("banner");
                app.egSetProgressEventValue("s5_banner_take", 1);
                poSprite.setVisible(false, ViewMain.TIME_ANIMATE);
            }
            else
            {
                showAndHideSprites("banner2", "banner", ViewMain.TIME_ANIMATE/2);
            }
        }
        else if (sID.equals("potion"))
        {
            app.egAddToInventory( "potion" );
            app.egSetProgressEventValue("s5_potion_take", 1);
            poSprite.setVisible(false, ViewMain.TIME_ANIMATE);
        }
        else if (sID.equals("witch"))
        {
            if (app.egIsSelectedItem("scroll") == true) {
                app.egPlaySound( R.raw.snd_boilering );
                app.egRemoveFromInventory("scroll");
                app.egSetProgressEventValue("s5_scroll_give", 1);
                showAndHideSprites("spcloud,pinkwater", "", ViewMain.TIME_ANIMATE*2 );
            } else {
                if ((app.egGetProgressEventValue("s5_scroll_give")==1)
                        &&(app.egGetProgressEventValue("s5_ingridients")==0))
                    showAndHideSprites("spcloud", "", ViewMain.TIME_ANIMATE*2 );
                else if (app.egGetProgressEventValue("s5_cat_appear")==1)
                    ; // dont' show cat speak cloud
                else
                    showAndHideSprites("spcloud2","", ViewMain.TIME_ANIMATE*2);
            }
        }
        else if (sID.equals("cauldron"))
        {
            APuzzle oPuzzle = app.egGetPuzzle(app.PUZZLE_CAULDRON);
            if (oPuzzle == null)
                return;
            if (app.egGetProgressEventValue("s5_scroll_give")==1)
                if (oPuzzle.Solved == false)
                    app.egShowPuzzle(oPuzzle);
        }
        else if (sID.equals("broom"))
        {
            if (app.egGetProgressEventValue("s5_cat_appear") == 1) {
                app.egAddToInventory("broom");
                showAndHideSprites("", "broom", ViewMain.TIME_ANIMATE );
                app.egSetProgressEventValue("s5_broom_take", 1);
            }
        }

        super.onSpriteTouch(poSprite, pX, pY);
    }


}
