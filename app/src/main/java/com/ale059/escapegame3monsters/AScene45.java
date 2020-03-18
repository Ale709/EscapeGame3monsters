package com.ale059.escapegame3monsters;

public class AScene45 extends AScene {

    public AScene45()
    {
        ASprite oSprite = null;

        addSprite("bg", R.drawable.s45_bg,       0,   0, true, false);

        addSprite("door", 0, 349, 404, 304, 351, true, true);

        ////addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        //addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
        addControlButtons("prev_scene,menu,hint");

    }

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
            //app.egMoveToScene(app.SCENE01);
        }
        else if (sID.equals("door")) {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_DOOR45 );
            app.egShowPuzzle( oPuzzle );

        }

        super.onSpriteTouch(poSprite, pX, pY);
    }


    public APuzzle CreateDoorPuzzle()
    {
        return new APuzzle_Door45();
    }


    public class APuzzle_Door45 extends APuzzle_Code {

        @Override
        public void Init(){
            AnswerCode = "52314";
            InitialCode = "11111";
            CurrentCode = InitialCode;
            LogoResID = R.drawable.small_door1;

            Solved = false;

            SymbolsSets = new String[ 5 ];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SymbolsSets[4] = "12345";
        }

        @Override
        protected int getResourceForChar(char ch) {
            switch (ch) {
                case '1': return R.drawable.spcloud_1;
                case '2': return R.drawable.spcloud_2;
                case '3': return R.drawable.spcloud_3;
                case '4': return R.drawable.spcloud_4;
                case '5': return R.drawable.spcloud_5;
            }
            return 0;
        }

        @Override
        protected void onPuzzleSolved()
        {
            this.Solved = true;
            app.egPlaySound( R.raw.snd_door_open );
            app.egSetProgressEventValue("s45_open_door", 1);
            app.egHidePuzzle();
            app.egMoveToScene( app.SCENE04 );
        }
    }
}
