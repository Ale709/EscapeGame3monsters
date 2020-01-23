package com.ale059.escapegameplasticineworld1;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class ASprite {


    public final static BitmapFactory.Options mBmpOpts = new BitmapFactory.Options();

    public String ID = null;
    public int ResID = 0;
    public Bitmap Bmp = null;
    //public Rect rect;
    public int X = 0;
    public int Y = 0;
    public int Width = -1;
    public int Height = -1;

    public boolean isVisible = true;
    public boolean isTouchable = true;

    static {
        mBmpOpts.inScaled = false;
        mBmpOpts.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public void setResourceID(int pnNewResID)
    {
        if (pnNewResID != ResID)
        {
            ResID = pnNewResID;
            if (Bmp != null)
                Bmp.recycle();
            Bmp = null;
        }
    }

    public Bitmap getBitmap()
    {
        if (Bmp != null)
            return Bmp;

        if (ResID != 0)
            Bmp = BitmapFactory.decodeResource(app.Resources(), ResID, mBmpOpts);

        if (Bmp != null)
        {
            if (Width == -1)
                Width = Bmp.getWidth();
            if (Height == -1)
                Height = Bmp.getHeight();
        }

        return Bmp;
    }

    public boolean containsXY(int  pnX, int pnY)
    {
        Bitmap bmp = getBitmap();
        //if (bmp == null)
        //    return false;
        if ((Width == -1) || (Height == -1))
            return false;
        if ((pnX<X) || (pnX>X+Width))
            return false;
        if ((pnY<Y) || (pnY>Y+Height))
            return false;
        return true;
    }

}
