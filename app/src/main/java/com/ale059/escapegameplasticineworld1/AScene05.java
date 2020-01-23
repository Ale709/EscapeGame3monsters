package com.ale059.escapegameplasticineworld1;

public class AScene05 extends AScene {


    public AScene05()
    {
        addSprite("bg", R.drawable.s5_bg,       0,   0, true, false);
        addSprite("cheese", R.drawable.s5_cheese, 127, 621, true, true);
        addSprite("dagger", R.drawable.s5_dagger, 267, 643, true, true);
        addSprite("banner",R.drawable.s5_banner, 302,  91, true, true);

        addSprite("cat", R.drawable.s5_cat,   311, 704, false, false);
        addSprite("broom", R.drawable.s5_broom, 653, 449, true, false);

        addSprite("pinkwater",R.drawable.s5_pinkwater, 424, 571, false, true);

        addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene"))
            app.egMoveToScene( app.SCENE03 );
        else if (sID.equals("next_scene"))
            app.egMoveToScene( app.SCENE01 );
        else if (sID.equals("cheese")) {
            app.egAddToInventory( "cheese_worm" );
            poSprite.isVisible = false;
        }
        else if (sID.equals("dagger"))
        {
            app.egAddToInventory( "dagger" );
            poSprite.isVisible = false;
        }
        else if (sID.equals("banner"))
        {
            if (app.egIsSelectedItem("dagger") == true) {
                app.egAddToInventory("banner");
                poSprite.isVisible = false;
            }
        }
    }


}
