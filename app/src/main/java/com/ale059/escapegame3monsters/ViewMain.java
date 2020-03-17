package com.ale059.escapegame3monsters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        {
            Rect rcSrc = new Rect(0,0, this.getWidth(), this.getHeight());
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(rcSrc, paint);
        }

//        // create in-memory bitmap
//        Bitmap bInMemory = Bitmap.createBitmap(SIZE_IN_MEMORY, SIZE_IN_MEMORY, Bitmap.Config.RGB_565);
//        bInMemory.setDensity(Bitmap.DENSITY_NONE);
//        Canvas cvs = new Canvas(bInMemory);
//        Paint paint = new Paint();
//        paint.setFilterBitmap(true);
//        paint.setAntiAlias(true);
//
//        // load the original image
//        app.ActiveScene.DrawOnCanvas(cvs, paint);
//
//        if (app.ScenePuzzle != null)
//            app.ScenePuzzle.DrawOnCanvas(cvs, paint);
//
//        if (app.SceneItemView.Visible)
//            app.SceneItemView.DrawOnCanvas(cvs, paint);
//
//        //90,560
//
//        // scale it down and draw it
//        Rect rcSrc = new Rect(0,0, bInMemory.getWidth(), bInMemory.getHeight());
//        //DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
//        //int nScrWidth = dm.widthPixels, nScrHeight = dm.heightPixels;
//        int nScrWidth = this.getWidth(), nScrHeight = this.getHeight();
//        int nRectSize = Math.min( nScrWidth, nScrHeight );
//
//        int nInvBlockHeight = SIZE_INV*nRectSize/SIZE_IN_MEMORY;
//
//        mRectMain.left = mRectMain.top = 0;
//        mRectMain.right = mRectMain.bottom = nRectSize;
//        mRectMain.offsetTo((nScrWidth-nRectSize)/2, (nScrHeight-nRectSize+nInvBlockHeight)/2 );
//
//        //Bitmap scaledLogo = Bitmap.createScaledBitmap(logoBitmap, logoWidth, logoHeight, false);
//        canvas.drawBitmap(bInMemory, rcSrc, mRectMain, paint);
//
//
//        // inventory
//        bInMemory = Bitmap.createBitmap(SIZE_IN_MEMORY, SIZE_INV, Bitmap.Config.RGB_565);
//        bInMemory.setDensity(Bitmap.DENSITY_NONE);
//        cvs = new Canvas(bInMemory);
//
//        app.Inventory.DrawOnCanvas(cvs, paint);
//        //90,560
//
//        // scale it down and draw it
//        rcSrc = new Rect(0,0, bInMemory.getWidth(), bInMemory.getHeight());
//
//        mRectInventory.left = mRectInventory.top = 0;
//        mRectInventory.right = nRectSize;
//        mRectInventory.bottom = nInvBlockHeight;
//        mRectInventory.offsetTo((nScrWidth-nRectSize)/2, (nScrHeight-nRectSize-nInvBlockHeight)/2 );
//
//        canvas.drawBitmap(bInMemory, rcSrc, mRectInventory, paint);

        // create in-memory bitmap
        Bitmap bInMemory = Bitmap.createBitmap(SIZE_IN_MEMORY, SIZE_IN_MEMORY+SIZE_INV, Bitmap.Config.RGB_565);
        bInMemory.setDensity(Bitmap.DENSITY_NONE);
        Canvas cvs = new Canvas(bInMemory);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);

        // load the original image
        app.ActiveScene.DrawOnCanvas(cvs, paint, 0, SIZE_INV);

        if (app.ScenePuzzle != null)
            app.ScenePuzzle.DrawOnCanvas(cvs, paint, 0, SIZE_INV);

        if (app.SceneItemView.Visible)
            app.SceneItemView.DrawOnCanvas(cvs, paint, 0, SIZE_INV);

        app.Inventory.DrawOnCanvas(cvs, paint, 0, 0);

        if (app.SceneMenu != null)
            app.SceneMenu.DrawOnCanvas(cvs, paint, 0, 0);
        //90,560

        // scale it down and draw it
        Rect rcSrc = new Rect(0,0, bInMemory.getWidth(), bInMemory.getHeight());
        int nScrWidth = this.getWidth(), nScrHeight = this.getHeight();
        //int nRectSize = Math.min( nScrWidth, nScrHeight );
        float nRectScale = Math.min( ((float)nScrWidth)/bInMemory.getWidth(), ((float)nScrHeight)/bInMemory.getHeight() );

        //int nInvBlockHeight = SIZE_INV*nRectSize/SIZE_IN_MEMORY;
        int nInvBlockHeight = (int)(((float)SIZE_INV)*nRectScale);


        mRectMain.left = mRectMain.top = 0;
        //mRectMain.right = mRectMain.bottom = nRectSize;
        //mRectMain.offsetTo((nScrWidth-nRectSize)/2, (nScrHeight-nRectSize+nInvBlockHeight)/2 );
        mRectMain.right = (int)(((float)bInMemory.getWidth())*nRectScale);
        mRectMain.bottom = (int)(((float)bInMemory.getHeight())*nRectScale);
        mRectMain.offsetTo((nScrWidth-mRectMain.right)/2, (nScrHeight-mRectMain.bottom)/2 );

        //Bitmap scaledLogo = Bitmap.createScaledBitmap(logoBitmap, logoWidth, logoHeight, false);
        canvas.drawBitmap(bInMemory, rcSrc, mRectMain, paint);


        // inventory
//        bInMemory = Bitmap.createBitmap(SIZE_IN_MEMORY, SIZE_INV, Bitmap.Config.RGB_565);
//        bInMemory.setDensity(Bitmap.DENSITY_NONE);
//        cvs = new Canvas(bInMemory);
//
//        app.Inventory.DrawOnCanvas(cvs, paint);
//        //90,560
//
//        // scale it down and draw it
//        rcSrc = new Rect(0,0, bInMemory.getWidth(), bInMemory.getHeight());

        mRectInventory.set( mRectMain );
        //mRectInventory.offset(0, -nInvBlockHeight);
        mRectInventory.bottom = mRectInventory.top+nInvBlockHeight;


//        mRectInventory.left = mRectInventory.top = 0;
//        mRectInventory.right = (int)(((float)bInMemory.getWidth())*nRectScale);
//        mRectInventory.bottom = nInvBlockHeight;
//        mRectInventory.offsetTo((nScrWidth-nRectSize)/2, (nScrHeight-nRectSize-nInvBlockHeight)/2 );

        //canvas.drawBitmap(bInMemory, rcSrc, mRectInventory, paint);

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
