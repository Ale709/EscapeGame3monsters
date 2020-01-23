package com.ale059.escapegameplasticineworld1;

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


        addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);

        addSprite("under_tree_1",R.drawable.s2_under_tree_1, 0, 0, false, true);
        addSprite("under_tree_2",R.drawable.s2_under_tree_2, 0, 0, false, true);
        addSprite("under_tree_3",R.drawable.s2_under_tree_3, 0, 0, false, true);
        mSpriteMushroom = addSprite("mushroom",R.drawable.s2_mushroom, 476, 548, false, true);

        addSprite("exit", R.drawable.inv_down, 422, 904, false, true);

    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene"))
            app.egMoveToScene( app.SCENE01 );
        else if (sID.equals("next_scene"))
            app.egMoveToScene( app.SCENE03 );
        else if (sID.equals("branch")) {
            app.egAddToInventory( "branch" );
            poSprite.isVisible = false;
        }
        else if (sID.equals("chest"))
        {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_CHEST1 );
            app.egShowPuzzle(oPuzzle);
        }
        else if (sID.equals("pit_ground"))
        {
            if (app.egIsSelectedItem("shovel")) {
                showAndHideSprites("pit", "pit_ground");
                //app.Inventory.ItemsHold_Remove("shovel");
            }
        }
        else if (sID.equals("pit"))
        {
            app.egMoveToScene( app.SCENE02PIT );
        }
        else if (sID.equals("mushroom"))
        {
            app.egAddToInventory( "mushroom" );
            poSprite.isVisible = false;
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
                mSpriteMushroom.isVisible = oPuzzle.isAnswerCorrect();
            }
            showAndHideSprites("under_tree_1,exit", "under_tree_2,under_tree_3");
        }
        else if (sID.equals("tree2"))
        {
            APuzzle_Sequence oPuzzle = (APuzzle_Sequence)app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null) if (oPuzzle.Solved == false) {
                oPuzzle.addSequenceChar("2");
                oPuzzle.trimToAnswerLength();
                mSpriteMushroom.isVisible = oPuzzle.isAnswerCorrect();
            }
            showAndHideSprites("under_tree_2,exit", "under_tree_1,under_tree_3");
        }
        else if (sID.equals("tree3"))
        {
            APuzzle_Sequence oPuzzle = (APuzzle_Sequence)app.egGetPuzzle(app.PUZZLE_MUSHROOM);
            if (oPuzzle != null) if (oPuzzle.Solved == false) {
                oPuzzle.addSequenceChar("3");
                oPuzzle.trimToAnswerLength();
                mSpriteMushroom.isVisible = oPuzzle.isAnswerCorrect();
            }
            showAndHideSprites("under_tree_3,exit", "under_tree_1,under_tree_2");
        }
        else if (sID.equals("exit"))
        {
            showAndHideSprites("", "under_tree_1,under_tree_2,under_tree_3,exit,mushroom");
        }
    }
}
