package com.ale059.escapegameplasticineworld1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

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
        oSprite.isVisible = pbIsVisible;
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

    public void DrawOnCanvas(Canvas cvs, Paint paint) {

        for (ASprite oSprite : mSprites2Draw) {
            if (oSprite == null)
                continue;
            if (oSprite.isVisible == false)
                continue;
            Bitmap bmp1 = oSprite.getBitmap();
            //bmp1.setDensity(Bitmap.DENSITY_NONE);
            if (bmp1 != null) {
                if (bmp1.getWidth()==oSprite.Width && bmp1.getHeight()==oSprite.Height)
                    cvs.drawBitmap(bmp1, oSprite.X, oSprite.Y, paint);
                else
                {
                    // rescale
                    Rect src = new Rect(0,0,bmp1.getWidth(),bmp1.getHeight());
                    Rect dst = new Rect(0,0,oSprite.Width,oSprite.Height);
                    dst.offsetTo(oSprite.X,oSprite.Y);
                    cvs.drawBitmap(bmp1, src, dst, paint);
                }
            }
        }
    }

    public ASprite getSpriteByXY(int nX, int nY) {

        ASprite oRet = null;
        for (ASprite oSprite : mSprites2Draw) {
            if (oSprite == null)
                continue;
            if (oSprite.isVisible == false)
                continue;
            if (oSprite.isTouchable == false)
                continue;
            if (oSprite.containsXY(nX, nY) == true) {
                oRet = oSprite;
            }
        }

        return oRet;
    }

    public void showAndHideSprites(String ps2Show, String ps2Hide)
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
                if (sSpriteId.equals(sId1))
                    oSprite.isVisible = true;

            for (String sId1: aToHide)
                if (sSpriteId.equals(sId1))
                    oSprite.isVisible = false;
        }

    }

    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
    }

}
