package com.ale059.escapegameplasticineworld1;

public class AScene01 extends AScene {

    protected ATimer mTimer = new ATimer();

    protected int mnTalkCloudIndex = 0;
    protected ASprite[] maTalkCloudSpites = new ASprite[7];


    public AScene01()
    {
        addSprite("bg", R.drawable.s1_bg,     0,   0, true, false);
        addSprite("monster1_1", R.drawable.s1_m1_1,  90, 560, true, true);
        addSprite("monster2_1", R.drawable.s1_m2_1, 370, 370, true, true);
        addSprite("monster3_1", R.drawable.s1_m3_1, 545, 575, true, true);

        addSprite("monster1_2", R.drawable.s1_m1_2,  70, 630, false, true);
        addSprite("monster2_2", R.drawable.s1_m2_2, 325, 230, false, true);
        addSprite("monster3_2", R.drawable.s1_m3_2, 635, 405, false, true);


        //int nCode = app.EG_CODE_TREES;
        maTalkCloudSpites[0] = addSprite(null, R.drawable.s1_sp1_1,  35, 395, false, false);
        maTalkCloudSpites[2] = addSprite(null, R.drawable.s1_sp1_2, 136, 257, false, false);
        maTalkCloudSpites[4] = addSprite(null, R.drawable.s1_sp1_3, 464, 190, false, false);
        maTalkCloudSpites[1] = addSprite(null, R.drawable.s1_sp1_4, 626, 354, false, false);
        maTalkCloudSpites[3] = addSprite(null, R.drawable.s1_sp1_5, 827, 408, false, false);
        //maTalkCloudSpites[5] = maTalkCloudSpites[6] = null;

        addSprite("speech_1", R.drawable.s1_sp2_1,  45, 391, false, true);
        addSprite("speech_2", R.drawable.s1_sp2_2, 427, 205, false, true);
        addSprite("speech_3", R.drawable.s1_sp2_3, 686, 381, false, true);

        addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        addSprite( "prev_scene", R.drawable.arrow_left,   10, 890, true, true);

        switchTalkClouds1();
        mTimer.StartTimer(1500, onTimerCallback);

    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene")) {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_DOOR45 );
            if (oPuzzle.Solved)
                app.egMoveToScene(app.SCENE05);
            else
                app.egMoveToScene(app.SCENE45);
        }
        else if (sID.equals("next_scene"))
            app.egMoveToScene( app.SCENE02 );
        else if (sID.equals("speech_1")) {

        }
        else if (sID.equals("monster1_1")) {
            APuzzle oPuzzle = app.egGetPuzzle( app.PUZZLE_MTALK1 );
            app.egShowPuzzle(oPuzzle);

        }
    }

    protected void switchTalkClouds1()
    {
        for (int i=0; i<maTalkCloudSpites.length; i++)
        {
            ASprite oSprite = maTalkCloudSpites[i];
            if (oSprite == null)
                continue;
            oSprite.isVisible = (i==mnTalkCloudIndex);
        }

        mnTalkCloudIndex++;
        mnTalkCloudIndex %= maTalkCloudSpites.length;
    }

    ATimer.events onTimerCallback = new ATimer.events() {
        @Override
        public void onTimerEvent(ATimer poTimer) {
            switchTalkClouds1();
            //invalidate();
        }
    };

    public APuzzle CreateMTalkPuzzle(int pnNum)
    {
        if (pnNum==1)
            return new APuzzle_MTalk1();
        if (pnNum==2)
            return new APuzzle_MTalk2();
        if (pnNum==3)
            return new APuzzle_MTalk3();
        return null;
    }


    public class APuzzle_MTalk1 extends APuzzle_Code {

        @Override
        public void Init(){
            AnswerCode = "fish";
            InitialCode = "aaaa";
            CurrentCode = InitialCode;
            LogoResID = R.drawable.s1_sp2_1;

            SymbolsSets = new String[4];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SYMBOLS_SET_ABC;
        }

        @Override
        protected void onPuzzleSolved()
        {
            this.Solved = true;
            app.egHidePuzzle();
        }
    }

    public class APuzzle_MTalk2 extends APuzzle_Code {

        @Override
        public void Init(){
            AnswerCode = "bone";
            InitialCode = "aaaa";
            CurrentCode = InitialCode;
            LogoResID = R.drawable.s1_sp2_2;

            SymbolsSets = new String[4];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SYMBOLS_SET_ABC;
        }

        @Override
        protected void onPuzzleSolved()
        {
            this.Solved = true;
            app.egHidePuzzle();
        }
    }

    public class APuzzle_MTalk3 extends APuzzle_Code {

        @Override
        public void Init(){
            AnswerCode = "ring";
            InitialCode = "aaaa";
            CurrentCode = InitialCode;
            LogoResID = R.drawable.s1_sp2_3;

            SymbolsSets = new String[4];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SYMBOLS_SET_ABC;
        }

        @Override
        protected void onPuzzleSolved()
        {
            this.Solved = true;
            app.egHidePuzzle();
        }
    }
}
