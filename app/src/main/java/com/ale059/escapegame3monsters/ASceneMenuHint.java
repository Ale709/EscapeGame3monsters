package com.ale059.escapegame3monsters;

import android.graphics.Bitmap;

public class ASceneMenuHint extends AScene {

    protected int ResID = 0;
    protected ASprite ItemSprite = null;
    public AProgressEvent HintEvent = null;


    public ASceneMenuHint()
    {
        ASprite oSprite = null;
        oSprite = addSprite("bg", R.drawable.bg_black_shade,     0,   0, ViewMain.SIZE_IN_MEMORY, ViewMain.SIZE_IN_MEMORY+ViewMain.SIZE_INV, true, false);
        ItemSprite = addSprite("hint", 0, 0, 0, true, false);

        //addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
        int nOkBtnWidth = 320;
        int nOkBtnHeight = 160;
        int y0 = 782+ViewMain.SIZE_INV, spy=30;
        int x0 = (ViewMain.SIZE_IN_MEMORY-nOkBtnWidth)/2;

        //x0 += x0 + nOkBtnWidth;
        addSprite("btn_check", R.drawable.puz_btn_long, x0, y0, nOkBtnWidth, nOkBtnHeight, true, true);
        addSprite("icon_ok", R.drawable.icon_ok, x0+(nOkBtnWidth-(nOkBtnHeight-spy))/2, y0+spy/2, nOkBtnHeight-spy, nOkBtnHeight-spy, true, false);
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("btn_check"))
            app.egOpenMenuScene( 0 );

    }

    @Override
    public void onShow()
    {
        if (ItemSprite != null) {
            if (ItemSprite.Bmp == null) {
                Bitmap bmp = ItemSprite.getBitmap();
                if (bmp != null)
                {
                    ItemSprite.Width = bmp.getWidth();
                    ItemSprite.Height = bmp.getHeight();
                    ItemSprite.X = (ViewMain.SIZE_IN_MEMORY-ItemSprite.Width)/2;
                    ItemSprite.Y = (ViewMain.SIZE_IN_MEMORY-ItemSprite.Height)/2;
                }
            }
        }

        if (HintEvent != null)
            HintEvent.IsHintAvailable = true;

        super.onShow();
    }

    public void setHintEvent( AProgressEvent poEvent )
    {
        if (poEvent != null) {
            HintEvent = poEvent;
            ItemSprite.setResourceID(poEvent.HintResID);
        }
    }


}
