package com.ale059.escapegameplasticineworld1;

public class AScene03 extends AScene {

    protected ATimer mTimer = new ATimer();

    protected ASprite mSpriteShip = null;
    protected ASprite mSpriteChest = null;
    protected ASprite mSpriteSk_Fishing = null;
    protected ASprite mSpriteSk_Sit = null;
    protected ASprite mSpriteSk_Hat = null;
    protected ASprite mSpriteSk_Head = null;
    protected ASprite mSpriteSk_Flag = null;

    public AScene03()
    {
        ASprite oSprite = null;
        addSprite("bg", R.drawable.s3_bg,       0,   0, true, false);
        mSpriteShip = addSprite("ship", R.drawable.s3_ship, 32, 438, false, false);
        mSpriteSk_Fishing = addSprite("skeleton_fishing", R.drawable.s3_skeleton_fishing, 163, 234, false, false);
        mSpriteSk_Hat = addSprite("skeleton_hat",R.drawable.s3_skeleton_hat, 559,  365, false, false);
        mSpriteSk_Head = addSprite("skeleton_head",R.drawable.s3_bg, 626,  378, false, true);
        mSpriteSk_Head.Width = 135; mSpriteSk_Head.Height = 64;
        mSpriteSk_Sit = addSprite("skeleton_sit",R.drawable.s3_skeleton_sit, 559,  316, false, true);

        addSprite("chair", R.drawable.s3_chair,   580, 678, true, true);
        mSpriteChest = addSprite("chest", R.drawable.s3_chest, 99, 699, false, true);

        mSpriteSk_Flag = addSprite("flag",R.drawable.s3_flag, 450, 582, false, false);


        addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
        addSprite("prev_scene", R.drawable.arrow_left,   10, 890, true, true);
    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY) {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("prev_scene"))
            app.egMoveToScene(app.SCENE02);
        else if (sID.equals("next_scene"))
        {
            APuzzle oPuzzle = app.egGetPuzzle(app.PUZZLE_DOOR45);
            if (oPuzzle.Solved)
                app.egMoveToScene(app.SCENE05);
            else
                app.egMoveToScene(app.SCENE45);
        }
        else if (sID.equals("chair")) {
            poSprite.isVisible = false;
            mSpriteSk_Sit.isVisible = true;
        }
        else if (sID.equals("skeleton_sit"))
        {
            poSprite.isVisible = false;
            mSpriteSk_Hat.isVisible = true;
            mSpriteSk_Head.isVisible = true;

            mTimer.StartTimer(3000, onTimerSkeletonHat);
        }
        else if (sID.equals("skeleton_head"))
        {
            app.egAddToInventory("worm");
        }
    }

    ATimer.events onTimerSkeletonHat = new ATimer.events() {
        @Override
        public void onTimerEvent(ATimer poTimer) {
            mSpriteSk_Sit.isVisible = true;
            mSpriteSk_Hat.isVisible = false;
            mSpriteSk_Head.isVisible = false;

            poTimer.StopTimer();
        }
    };

}
