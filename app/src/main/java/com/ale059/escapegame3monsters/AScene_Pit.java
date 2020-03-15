package com.ale059.escapegame3monsters;

public class AScene_Pit extends AScene {

    public AScene_Pit()
    {
        ASprite oSprite = null;

        addSprite("bg", R.drawable.s2pit_bg,       0,   0, true, false);

        addSprite("bone", R.drawable.s2pit_bone, 175, 422, true, true);
        addSprite("worm",R.drawable.s2pit_worm, 705,  361, true, true);

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
            app.egSetProgressEventValue("s2_bone_take", 1);
            poSprite.setVisible(false, ViewMain.TIME_ANIMATE );
        }
        else if (sID.equals("worm"))
        {
            app.egAddToInventory( "worm" );
            app.egSetProgressEventValue("s2_worm_take", 1);
            poSprite.setVisible(false, ViewMain.TIME_ANIMATE );
        }
        else if (sID.equals("exit"))
        {
            app.egMoveToScene( app.SCENE02 );
        }
    }
}
