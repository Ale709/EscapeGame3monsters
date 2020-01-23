package com.ale059.escapegameplasticineworld1;

public class AScene_Pit extends AScene {

    public AScene_Pit()
    {
        ASprite oSprite = null;

        addSprite("bg", R.drawable.s2pit_bg,       0,   0, true, false);

        addSprite("bone", R.drawable.s2pit_bone, 175, 422, true, true);
        addSprite("worm",R.drawable.s2pit_worm, 705,  361, false, true);

        addSprite("exit", R.drawable.inv_down, 422, 904, true, true);

    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene"))
            ;//app.egMoveToScene( app.SCENE01 );
        else if (sID.equals("next_scene"))
            ;//app.egMoveToScene( app.SCENE03 );
        else if (sID.equals("bone")) {
            app.egAddToInventory( "bone" );
            poSprite.isVisible = false;
        }
        else if (sID.equals("worm"))
        {
            app.egAddToInventory( "worm" );
            poSprite.isVisible = false;
        }
        else if (sID.equals("exit"))
        {
            app.egMoveToScene( app.SCENE02 );
        }
    }
}
