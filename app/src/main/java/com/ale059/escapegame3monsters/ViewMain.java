package com.ale059.escapegame3monsters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewMain extends View {

    public static int TIME_ANIMATE = 500;
    public static int SIZE_IN_MEMORY = 1024;
    public static int SIZE_INV = 160;
    protected final Rect mRectMain = new Rect();
    protected final Rect mRectInventory = new Rect();

    //protected AScene mScene = new AScene05();

    public ViewMain(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }


    private Paint mPaint = new Paint();
    private Bitmap mBmpInMemory = null;
    private Canvas mCvsBmpInMemory = null;

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        if (mBmpInMemory == null)
        {
            // create in-memory bitmap
            mBmpInMemory = Bitmap.createBitmap(SIZE_IN_MEMORY, SIZE_IN_MEMORY+SIZE_INV, Bitmap.Config.RGB_565);
            mBmpInMemory.setDensity(Bitmap.DENSITY_NONE);

            mCvsBmpInMemory = new Canvas(mBmpInMemory);

            mPaint.setFilterBitmap(true);
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLACK);

        }
            int nScrWidth = this.getWidth(), nScrHeight = this.getHeight();
            //float nRectScale = Math.min( ((float)nScrWidth)/ mBmpInMemory.getWidth(), ((float)nScrHeight)/ mBmpInMemory.getHeight() );
            float nRectScale = ((float)nScrWidth)/ mBmpInMemory.getWidth();
            this.setMinimumHeight( (int)((float)mBmpInMemory.getHeight()*nRectScale) );

            //int nInvBlockHeight = SIZE_INV*nRectSize/SIZE_IN_MEMORY;
            int nInvBlockHeight = (int)(((float)SIZE_INV)*nRectScale);


            mRectMain.left = mRectMain.top = 0;
            mRectMain.right = (int)(((float) mBmpInMemory.getWidth())*nRectScale);
            mRectMain.bottom = (int)(((float) mBmpInMemory.getHeight())*nRectScale);
            mRectMain.offsetTo((nScrWidth-mRectMain.right)/2, (nScrHeight-mRectMain.bottom)/2 );

            mRectInventory.set( mRectMain );
            mRectInventory.bottom = mRectInventory.top+nInvBlockHeight;


        Rect rcSrc = new Rect(0,0, this.getWidth(), this.getHeight());
        canvas.drawRect(rcSrc, mPaint);

        rcSrc.right = mBmpInMemory.getWidth();
        rcSrc.bottom = mBmpInMemory.getHeight();
        mCvsBmpInMemory.drawRect(rcSrc, mPaint);

        // load the original image
        app.ActiveScene.DrawOnCanvas(mCvsBmpInMemory, mPaint, 0, SIZE_INV);

        if (app.ScenePuzzle != null)
            app.ScenePuzzle.DrawOnCanvas(mCvsBmpInMemory, mPaint, 0, SIZE_INV);

        if (app.SceneItemView.Visible)
            app.SceneItemView.DrawOnCanvas(mCvsBmpInMemory, mPaint, 0, SIZE_INV);

        app.Inventory.DrawOnCanvas(mCvsBmpInMemory, mPaint, 0, 0);

        if (app.SceneMenu != null)
            app.SceneMenu.DrawOnCanvas(mCvsBmpInMemory, mPaint, 0, 0);
        //90,560

        // scale it down and draw it
        //Rect rcSrc = new Rect(0,0, mBmpInMemory.getWidth(), mBmpInMemory.getHeight());
        canvas.drawBitmap(mBmpInMemory, rcSrc, mRectMain, mPaint);




        invalidate();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int nX = Float.valueOf( event.getX() ).intValue();
        int nY = Float.valueOf( event.getY() ).intValue();

        /*if (event.getAction() != MotionEvent.ACTION_UP)
            ;
        else */if (mRectInventory.contains(nX, nY))
        {
            nX = Float.valueOf( (event.getX()-mRectInventory.left)/mRectInventory.width()*SIZE_IN_MEMORY ).intValue();
            nY = Float.valueOf( (event.getY()-mRectInventory.top)/mRectInventory.height()*SIZE_INV).intValue();

            ASprite oSprite = app.Inventory.getSpriteByXY(nX, nY);
            if (oSprite != null)
                app.Inventory.onSpriteTouch( oSprite, nX-oSprite.X, nY-oSprite.Y );

        }
        else if (mRectMain.contains(nX, nY))
        {
            nX = Float.valueOf( (event.getX()-mRectMain.left)/mRectMain.width()*SIZE_IN_MEMORY ).intValue();
            nY = Float.valueOf( (event.getY()-mRectMain.top)/mRectMain.height()*(SIZE_IN_MEMORY+SIZE_INV) ).intValue()-SIZE_INV;

            ASprite oSprite = null;
            if (app.SceneMenu != null) {
                oSprite = app.SceneMenu.getSpriteByXY(nX, nY+SIZE_INV);
                if (oSprite != null)
                    app.SceneMenu.onSpriteTouch(oSprite, nX - oSprite.X, nY+SIZE_INV - oSprite.Y);

            } else if (app.SceneItemView.Visible) {
                oSprite = app.SceneItemView.getSpriteByXY(nX, nY);
                if (oSprite != null)
                    app.SceneItemView.onSpriteTouch(oSprite, nX - oSprite.X, nY - oSprite.Y);

            } else if (app.ScenePuzzle != null) {
                oSprite = app.ScenePuzzle.getSpriteByXY(nX, nY);
                if (oSprite != null)
                    app.ScenePuzzle.onSpriteTouch( oSprite, nX-oSprite.X, nY-oSprite.Y );

            } else {
                oSprite = app.ActiveScene.getSpriteByXY(nX, nY);
                if (oSprite != null)
                    app.ActiveScene.onSpriteTouch( oSprite, nX-oSprite.X, nY-oSprite.Y );

            }

            //if (oSprite != null) {
            //    oSprite.isVisible = false;
            //    //this.invalidate();
            //}

        }

        return super.onTouchEvent(event);
    }
}
