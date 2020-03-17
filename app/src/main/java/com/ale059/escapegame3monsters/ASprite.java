package com.ale059.escapegame3monsters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Calendar;

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

    //public boolean isVisible = true;
    public boolean isTouchable = true;

    protected int mCurVisibility = 0; // 255-visible, 0-invisible
    protected int mDstVisibility = 255; // 255-visible, 0-invisible
    protected long mDstVisibilityTimeS = 0; // start time
    protected long mDstVisibilityTimeE = 0; // end time

    public boolean AnimationJustFinishedFlag = false;

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

    public void setVisible(boolean pbIsVisible, long pnTime)
    {
        long nCurTime = Calendar.getInstance().getTimeInMillis();
        int nDstVisibility = (pbIsVisible == true ? 255 : 0); // 255-visible, 0-invisible

        if (pnTime==0)
        {
            mCurVisibility = mDstVisibility = nDstVisibility;
            mDstVisibilityTimeS = mDstVisibilityTimeE = nCurTime;

        } else  if (mDstVisibilityTimeE < nCurTime) {
            if (mCurVisibility!=nDstVisibility || mDstVisibility!=nDstVisibility) {
                mDstVisibility = nDstVisibility;
                mDstVisibilityTimeS = nCurTime;
                mDstVisibilityTimeE = mDstVisibilityTimeS + pnTime;
            }

        }

        //isVisible = true;

    }

    public int calcVisibility(long pnCurTime)
    {
        if (mDstVisibilityTimeE==mDstVisibilityTimeS)
        {
            mCurVisibility = mDstVisibility;
            AnimationJustFinishedFlag = false;
        }
        else if (mCurVisibility!=mDstVisibility) {
            long nDelta = (255*(pnCurTime-mDstVisibilityTimeS))/(mDstVisibilityTimeE-mDstVisibilityTimeS);
            nDelta = Math.min(255, Math.max(0, nDelta));
            if (mDstVisibility == 0)
                mCurVisibility = 255-(int)nDelta;
            else
                mCurVisibility = (int)nDelta;

            AnimationJustFinishedFlag = (mCurVisibility==mDstVisibility);

        }
        else
            AnimationJustFinishedFlag = false;
        //if (mCurVisibility==0)
        //    isVisible = false;
        //if (mCurVisibility==255)
        //    isVisible = true;
        return mCurVisibility;
    }

    public boolean canTouch()
    {
        if (isTouchable == false)
            return false;
        if (mCurVisibility==0)
            return false;
        if (mCurVisibility>=255)
            if (mDstVisibility>=255)
                return true;
        return false;
    }

}
