package com.ale059.escapegame3monsters;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AScene04 extends AScene {

    public AScene04()
    {
        addSprite("bg", R.drawable.s4_bg,       0,   0, true, false);
        addSprite("picture", R.drawable.s4_picture, 809, 288, true, true);

        addSprite("bookcase", R.drawable.s4_bookcase, 166, 214, true, true);
        addSprite("bookcase_m", R.drawable.s4_bookcase_m, 465, 206, false, true);
        addSprite("apple", R.drawable.s4_apple, 662, 590, true, true);
        addSprite("cheese",R.drawable.s4_cheese, 662,  597, false, true);

        addSprite("scroll_up",R.drawable.s4_scroll_up, 269,  200, true, false);
        addSprite("scroll_down",R.drawable.s4_scroll_down, 287,  754, false, true);

        addSprite("owl_1",R.drawable.s4_owl_1, 371,  48, true, false);
        addSprite("owl_2",R.drawable.s4_owl_2, 332,  55, false, false);
        addSprite("owl_3",R.drawable.s4_owl_3, 680,  390, false, false);
        addSprite("mouse_1",R.drawable.s4_mouse_1, 849,  607, false, false);
        addSprite("mouse_2",R.drawable.s4_mouse_2, 655,  561, false, false);

        addSprite("princess",R.drawable.s4_princess, 0,  508, false, false);
        addSprite("princess_m",R.drawable.s4_princess_m, 0,  376, false, false);

        addSprite("door_1",R.drawable.s4_door_1, 166,  228, false, true);
        addSprite("door_2",R.drawable.s4_door_2, 205,  293, false, true);
        addSprite("door_3t",0, 200,  315, 240, 415, false, true);
        addSprite("door_3",R.drawable.s4_door_3, 0,  0, false, false);

        addSprite("table", 0, 641,  628, 217, 87, false, true);

//        addSprite("cat", R.drawable.s5_cat,   311, 704, false, false);
//        addSprite("broom", R.drawable.s5_broom, 653, 449, true, false);

//        addSprite("pinkwater",R.drawable.s5_pinkwater, 424, 571, false, true);

        //addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        //addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
        addControlButtons("next_scene,prev_scene");


        addSprite("s4_books",R.drawable.s4books_bg, 0, 0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY, false, true);
        addSprite("s4_picture",R.drawable.s4picture_bg, 0, 0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY, false, true);

        //addSprite("exit", R.drawable.inv_down, 422, 904, false, true);
        addControlButtons("exit,menu,hint");
        showAndHideSprites("", "exit", 0);

    }


    protected boolean mIsCheeseWithWorm = false;

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE01);
        }
        else if (sID.equals("next_scene")) {
            app.egPlaySound( R.raw.snd_tap );
            app.egMoveToScene(app.SCENE05);
        }
        else if (sID.equals("bookcase") || sID.equals("bookcase_m")) {
            showAndHideSprites("s4_books,exit", "s4_picture", 0);
            app.egSetProgressEventValue("s4_door_hint", 1);
        }
        else if (sID.equals("picture")) {
            showAndHideSprites("s4_picture,exit", "s4_books", 0);
        }
        else if (sID.equals("exit")) {
            showAndHideSprites("", "s4_books,s4_picture,exit", 0);
        }
        else if (sID.equals("cheese")) {
            if (mIsCheeseWithWorm == true)
                app.egAddToInventory( "cheese_worm" );
            else
                app.egAddToInventory( "cheese" );
            poSprite.setVisible( false, ViewMain.TIME_ANIMATE );
            showAndHideSprites("table", "", ViewMain.TIME_ANIMATE );
        }
        else if (sID.equals("apple")) {
            app.egAddToInventory( "apple_b" );
            app.egSetProgressEventValue("s4_apple_take", 1);
            poSprite.setVisible( false, ViewMain.TIME_ANIMATE );
            showAndHideSprites("table", "", ViewMain.TIME_ANIMATE );
        }
        else if (sID.equals("table"))
        {
            if (app.egIsSelectedItem("cheese") == true) {
                mIsCheeseWithWorm = false;
                app.egRemoveFromInventory("cheese");
                showAndHideSprites("cheese", "table", ViewMain.TIME_ANIMATE );
                setTouchableSprites( "", "cheese,table");
            }
            else if (app.egIsSelectedItem("cheese_worm") == true) {
                mIsCheeseWithWorm = true;
                app.egRemoveFromInventory("cheese_worm");
                showAndHideSprites("cheese", "table", ViewMain.TIME_ANIMATE );
                setTouchableSprites( "", "cheese,table");
            }
            else if (app.egIsSelectedItem("apple_b") == true) {
                mIsCheeseWithWorm = true;
                app.egRemoveFromInventory("apple_b");
                showAndHideSprites("apple", "table", ViewMain.TIME_ANIMATE );
            }
        }
        else if (sID.equals("scroll_down"))
        {
            app.egAddToInventory( "scroll" );
            app.egSetProgressEventValue("s4_scroll_take", 1);
            poSprite.setVisible( false, ViewMain.TIME_ANIMATE );

        }
        else if (sID.equals("door_1")) {
            if (app.egIsSelectedItem("broom") == true) {
                app.egPlaySound( R.raw.snd_web_wipe );
                app.egRemoveFromInventory("broom");
                showAndHideSprites("door_2", "door_1", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s4_broom_use", 1);
                app.egSetProgressEventValue("s4_door_hint", 0); // to trigger hint
            }
        }
        else if (sID.equals("door_2")) {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_DOOR2 );
            app.egShowPuzzle( oPuzzle );
        }
        else if (sID.equals("door_3t")) {
            app.egPlaySound( R.raw.snd_success );
            app.egOpenMenuScene( app.SCENE_MENU_THEEND );

        }

        super.onSpriteTouch(poSprite, pX, pY);
    }

    @Override
    public void onShow() {

        if (app.egGetProgressEventValue("s4_princess_appear")==1) {
            if (app.egGetProgressEventValue("s4_bookcase_moved") == 0) {
                showAndHideSprites("princess_m", "princess", ViewMain.TIME_ANIMATE);
            }
        }


        if (app.egGetProgressEventValue("s1_ring_give")==1) {
            if (app.egGetProgressEventValue("s4_princess_appear") == 0) {
                app.egPlaySound( R.raw.snd_princess );
                this.showAndHideSprites("princess", "", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s4_princess_appear", 1);
            }
        }

        super.onShow();
    }

    @Override
    public void onAnimationShowFinished( ASprite poSprite )
    {
        if (poSprite == null)
            return;
        String sSpriteID = poSprite.ID;
        if (sSpriteID == null)
            return;

        if (sSpriteID == "cheese")
        {
            if (app.egGetProgressEventValue("s4_mouse_owl")==0) {
                app.egPlaySound( R.raw.snd_mouse );
                showAndHideSprites("mouse_1", "", ViewMain.TIME_ANIMATE * 2);
            }
            else
                setTouchableSprites( "cheese,table", "");
        }
        else if (sSpriteID == "mouse_1")
        {
            showAndHideSprites("mouse_2", "mouse_1", ViewMain.TIME_ANIMATE*2 );
        }
        else if (sSpriteID == "mouse_2")
        {
            app.egPlaySound( R.raw.snd_owl );
            showAndHideSprites("owl_2", "owl_1", ViewMain.TIME_ANIMATE*2 );
        }
        else if (sSpriteID == "owl_2")
        {
            app.egPlaySound( R.raw.snd_mouse );
            showAndHideSprites("owl_3,scroll_down", "owl_2,scroll_up,mouse_2", ViewMain.TIME_ANIMATE*2 );
        }
        else if (sSpriteID == "owl_3")
        {
            showAndHideSprites("", "owl_3", ViewMain.TIME_ANIMATE*2 );
        }
        else if (sSpriteID == "princess")
        {
            if (app.egGetProgressEventValue("s4_bookcase_moved")==0) {
                showAndHideSprites("princess_m", "princess", ViewMain.TIME_ANIMATE);
            }
        }
        else if (sSpriteID == "princess_m")
        {
            app.egPlaySound( R.raw.snd_bookcase_move );
            app.egSetProgressEventValue("s4_bookcase_moved", 1);
            showAndHideSprites("bookcase_m,door_1", "bookcase", ViewMain.TIME_ANIMATE*3);
        }
        else if (sSpriteID == "bookcase_m")
        {
            showAndHideSprites("princess", "princess_m", ViewMain.TIME_ANIMATE);
            //app.egSetProgressEventValue("s4_princess_appear", 1);
        }

    }

    @Override
    public void onAnimationHideFinished( ASprite poSprite ) {
        if (poSprite == null)
            return;
        String sSpriteID = poSprite.ID;
        if (sSpriteID == null)
            return;

        if (sSpriteID == "owl_3")
        {
            setTouchableSprites( "cheese,table", "");
            //showAndHideSprites("cheese", "", 0 );
            app.egSetProgressEventValue("s4_mouse_owl", 1);

        }


    }


    public APuzzle CreateDoorPuzzle()
    {
        return new APuzzle_Door2();
    }


    public class APuzzle_Door2 extends APuzzle_Code {

        @Override
        public void Init(){
            AnswerCode = "xekor";
            InitialCode = "books";
            CurrentCode = InitialCode;
            LogoResID = R.drawable.small_door2;

            SymbolsSets = new String[5];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SymbolsSets[4] = APuzzle_Code.SYMBOLS_SET_ABC;
            //SymbolsSets[4] = APuzzle.SYMBOLS_SET_123;

            SAVEID = "PuzzleDoor2";
        }

        @Override
        protected AScenePuzzle createScene()
        {
            return new AScenePuzzle_Code(this);
        }

        @Override
        protected void onPuzzleSolved()
        {
            if (app.egGetProgressEventValue("s4_door_hint")==1) {
                this.Solved = true;
                Scene = null;
                app.egPlaySound( R.raw.snd_door_open );

                app.egHidePuzzle();
                showAndHideSprites("door_3,door_3t", "door_2", ViewMain.TIME_ANIMATE);
                app.egSetProgressEventValue("s4_door_open", 1);
            }
        }

    }

}
