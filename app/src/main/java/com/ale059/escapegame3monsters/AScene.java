package com.ale059.escapegame3monsters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Calendar;

public class AScene {

    protected final ArrayList<ASprite> mSprites2Draw = new ArrayList<ASprite>();
    //protected final Rect mRectMain = new Rect();

    public ASprite addSprite(String psID, int pnResID, int pnX, int pnY, boolean pbIsVisible, boolean pbIsTouchable)
    {
        ASprite oSprite = new ASprite();
        oSprite.ID = psID;
        if (oSprite.ID == null)
            oSprite.ID = "";
        oSprite.ResID = pnResID;
        oSprite.X = pnX;
        oSprite.Y = pnY;
        //oSprite.isVisible = pbIsVisible;
        oSprite.setVisible(pbIsVisible,0);
        oSprite.isTouchable = pbIsTouchable;

        mSprites2Draw.add( oSprite );

        return oSprite;
    }

    public ASprite addSprite(String psID, int pnResID, int pnX, int pnY, int pnW, int pnH, boolean pbIsVisible, boolean pbIsTouchable)
    {
        ASprite oSprite = addSprite(psID, pnResID, pnX, pnY, pbIsVisible, pbIsTouchable);
        if (oSprite != null)
        {
            oSprite.Width = pnW;
            oSprite.Height = pnH;
        }

        return oSprite;
    }

    public void DrawOnCanvas(Canvas cvs, Paint paint, int pnOffsetX, int pnOffsetY) {

        long nCurTime = Calendar.getInstance().getTimeInMillis();

        for (ASprite oSprite : mSprites2Draw) {
            if (oSprite == null)
                continue;
            //if (oSprite.isVisible == false)
            //    continue;
            int nVisibility = oSprite.calcVisibility(nCurTime);
            if (oSprite.AnimationJustFinishedFlag == true)
                if (nVisibility==0)
                    onAnimationHideFinished( oSprite );
                else
                    onAnimationShowFinished( oSprite );
            if (nVisibility == 0)
                continue;
            paint.setAlpha( nVisibility );

            Bitmap bmp1 = oSprite.getBitmap();
            //bmp1.setDensity(Bitmap.DENSITY_NONE);
            if (bmp1 != null) {
                if (bmp1.getWidth()==oSprite.Width && bmp1.getHeight()==oSprite.Height)
                    cvs.drawBitmap(bmp1, oSprite.X+pnOffsetX, oSprite.Y+pnOffsetY, paint);
                else
                {
                    // rescale
                    Rect src = new Rect(0,0,bmp1.getWidth(),bmp1.getHeight());
                    Rect dst = new Rect(0,0,oSprite.Width,oSprite.Height);
                    dst.offsetTo(oSprite.X+pnOffsetX,oSprite.Y+pnOffsetY);
                    cvs.drawBitmap(bmp1, src, dst, paint);
                }
            }
        }
    }

    public void onShow()
    {
    }

    public void onAnimationShowFinished( ASprite poSprite )
    {
    }

    public void onAnimationHideFinished( ASprite poSprite )
    {
    }

    public ASprite getSpriteByID(String psID) {

        if (psID == null)
            return null;

        for (ASprite oSprite : mSprites2Draw) {
            if (oSprite == null)
                continue;
            if (psID.equals(oSprite.ID) == true)
                return oSprite;
        }

        return null;
    }

    public ASprite getSpriteByXY(int nX, int nY) {

        ASprite oRet = null;
        for (ASprite oSprite : mSprites2Draw) {
            if (oSprite == null)
                continue;
            if (oSprite.canTouch() == false)
                continue;
            //if (oSprite.isTouchable == false)
            //    continue;
            if (oSprite.containsXY(nX, nY) == true) {
                oRet = oSprite;
            }
        }

        return oRet;
    }

    public void showAndHideSprites(String ps2Show, String ps2Hide, int pnTime)
    {
        String aToShow[] = ps2Show.split(",");
        String aToHide[] = ps2Hide.split(",");

        for (ASprite oSprite: mSprites2Draw) {
            if (oSprite == null)
                continue;
            String sSpriteId = oSprite.ID;
            if (sSpriteId == null)
                continue;

            for (String sId1: aToShow)
                if (sSpriteId.equals(sId1)) {
                    oSprite.setVisible(true, pnTime );
                }

            for (String sId1: aToHide)
                if (sSpriteId.equals(sId1))
                    oSprite.setVisible(false, pnTime );
        }

    }

    public void setTouchableSprites(String ps2Show, String ps2Hide)
    {
        String aToShow[] = ps2Show.split(",");
        String aToHide[] = ps2Hide.split(",");

        for (ASprite oSprite: mSprites2Draw) {
            if (oSprite == null)
                continue;
            String sSpriteId = oSprite.ID;
            if (sSpriteId == null)
                continue;

            for (String sId1: aToShow)
                if (sSpriteId.equals(sId1)) {
                    oSprite.isTouchable = true;
                }

            for (String sId1: aToHide)
                if (sSpriteId.equals(sId1))
                    oSprite.isTouchable = false;
        }

    }

    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("menu")) {
            //app.SceneMenu = app.ListScenes.get( app.SCENE_MENU_MAIN );
            app.egOpenMenuScene( app.SCENE_MENU_MAIN );
            //app.egPlaySound( R.raw.snd_bones );
        }
        else if (sID.equals("hint")) {
            //app.egOpenMenuScene( app.SCENE_MENU_ASK_HINT );
            app.egCheckShowAdsBeforeHint();
        }

    }

    public void addControlButtons(String psButtons)
    {
        String aButtons[] = psButtons.split(",");

        for (String sButtonId: aButtons) {
            if (sButtonId.equals("next_scene"))
                addSprite("next_scene", R.drawable.arrow_right, 890, 890, true, true);
            else if (sButtonId.equals("prev_scene"))
                addSprite( "prev_scene", R.drawable.arrow_left,   10, 890, true, true);
            else if (sButtonId.equals("menu"))
                addSprite( "menu", R.drawable.icon_menu,   10, 176-160, true, true);
            else if (sButtonId.equals("hint"))
                addSprite( "hint", R.drawable.icon_hint,   154, 176-160, true, true);
            else if (sButtonId.equals("exit"))
                addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
                //addSprite( "close_scene", R.drawable.arrow_left,   10, 890, true, true);

        }

    }

}
