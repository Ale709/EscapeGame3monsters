package com.ale059.escapegame3monsters;

public class AScene02 extends AScene {

    protected int mnTreeSequence = 0;
    protected ASprite mSpriteMushroom = null;

    public AScene02()
    {
        ASprite oSprite = null;

        addSprite("bg", R.drawable.s2_bg,       0,   0, true, false);

        addSprite("tree1", 0, 187, 682, 214,178,true, true);
        addSprite("tree2",0, 389,  685, 183, 121, true, true);
        addSprite("tree3",0, 599,  701, 171, 118, true, true);


        addSprite("branch", R.drawable.s2_branch, 62, 719, true, true);

        addSprite("chest", 0,   766, 713, 241, 200, true, true);
        addSprite("pit", R.drawable.s2_pit, 228, 871, false, true);
        addSprite("pit_ground", 0, 228, 871, 205, 116, true, true);


        //addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        //addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
        addControlButtons("next_scene,prev_scene");

        addSprite("under_tree_1",R.drawable.s2_under_tree_1, 0, 0, false, true);
        addSprite("under_tree_2",R.drawable.s2_under_tree_2, 0, 0, false, true);
        addSprite("under_tree_3",R.drawable.s2_under_tree_3, 0, 0, false, true);
        mSpriteMushroom = addSprite("mushroom",R.drawable.s2_mushroom, 476, 548, false, true);

        //addSprite("exit", R.drawable.inv_down, 422, 904, false, true);
        addControlButtons("exit,menu,hint");
        showAndHideSprites("", "exit", 0);

    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE03);
        }
        else if (sID.equals("next_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE01);
        }
        else if (sID.equals("branch")) {
            app.egAddToInventory( "branch" );
            app.egSetProgressEventValue("s2_branch_take", 1);
            poSprite.setVisible( false, ViewMain.TIME_ANIMATE );
        }
        else if (sID.equals("chest"))
        {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_CHEST1 );
            if (oPuzzle != null)
                if (oPuzzle.Solved == true)
                    app.egPlaySound( R.raw.snd_chest_open );
            app.egShowPuzzle(oPuzzle);
        }
        else if (sID.equals("pit_ground"))
        {
            if (app.egGetProgressEventValue("s5_banner_look")==1) {
                if (app.egIsSelectedItem("shovel")) {
                    showAndHideSprites("pit", "pit_ground", ViewMain.TIME_ANIMATE);
                    app.egSetProgressEventValue("s2_shovel_dig", 1);
                    app.egRemoveFromInventory("shovel");
                    //app.Inventory.ItemsHold_Remove("shovel");
                }
            }
        }
        else if (sID.equals("pit"))
        {
            app.egMoveToScene( app.SCENE02PIT );
        }
        else if (sID.equals("mushroom"))
        {
            app.egAddToInventory( "mushroom" );
            app.egSetProgressEventValue("s2_mushroom_take", 1);
            poSprite.setVisible( false, ViewMain.TIME_ANIMATE );
            APuzzle oPuzzle = app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null)
                oPuzzle.Solved = true;
        }
        else if (sID.equals("tree1"))
        {
            APuzzle_Sequence oPuzzle = (APuzzle_Sequence)app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null) if (oPuzzle.Solved == false) {
                oPuzzle.addSequenceChar("1");
                oPuzzle.trimToAnswerLength();
                if (app.egGetProgressEventValue("s2_mushroom_take")==0)
                    mSpriteMushroom.setVisible( oPuzzle.isAnswerCorrect(), 0);
            }
            showAndHideSprites("under_tree_1,exit", "under_tree_2,under_tree_3", 0);
        }
        else if (sID.equals("tree2"))
        {
            APuzzle_Sequence oPuzzle = (APuzzle_Sequence)app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null) if (oPuzzle.Solved == false) {
                oPuzzle.addSequenceChar("2");
                oPuzzle.trimToAnswerLength();
                if (app.egGetProgressEventValue("s2_mushroom_take")==0)
                    mSpriteMushroom.setVisible( oPuzzle.isAnswerCorrect(), 0);
            }
            showAndHideSprites("under_tree_2,exit", "under_tree_1,under_tree_3", 0);
        }
        else if (sID.equals("tree3"))
        {
            APuzzle_Sequence oPuzzle = (APuzzle_Sequence)app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null) if (oPuzzle.Solved == false) {
                oPuzzle.addSequenceChar("3");
                oPuzzle.trimToAnswerLength();
                if (app.egGetProgressEventValue("s2_mushroom_take")==0)
                    mSpriteMushroom.setVisible( oPuzzle.isAnswerCorrect(), 0);
            }
            showAndHideSprites("under_tree_3,exit", "under_tree_1,under_tree_2", 0);
        }
        else if (sID.equals("exit"))
        {
            showAndHideSprites("", "under_tree_1,under_tree_2,under_tree_3,exit,mushroom", 0);
        }

        super.onSpriteTouch(poSprite, pX, pY);
    }
}
