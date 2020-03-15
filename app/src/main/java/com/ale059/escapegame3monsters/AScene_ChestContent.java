package com.ale059.escapegame3monsters;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AScene_ChestContent extends AScenePuzzle {

    public int LogoResID = R.drawable.puz_chestopened;
    protected AItem Item1 = null;
    protected ASprite Item1Sprite = null;
    protected AItem Item2 = null;
    protected ASprite Item2Sprite = null;
    protected AItem Item3 = null;
    protected ASprite Item3Sprite = null;


    public void putItem(String psID)
    {
        AItem oItem = app.Inventory.ListItems_All.get(psID);
        if (oItem == null)
            return;
        if (Item1 == null)
            Item1 = oItem;
        else if (Item2 == null)
            Item2 = oItem;
        else if (Item3 == null)
            Item3 = oItem;
    }

    public boolean containsItem(String psID)
    {
        if (psID == null)
            return false;
        if (Item1 != null)
            if (Item1.ID.equals(psID))
                return true;
        if (Item2 != null)
            if (Item2.ID.equals(psID))
                return true;
        if (Item3 != null)
            if (Item3.ID.equals(psID))
                return true;

        return false;
    }

    protected void UpdateWhat2Draw()
    {
        ASprite oSprite = null;
        if (mSprites2Draw.size() == 0) {
            // initial generate
            addSprite("bg", R.drawable.bg_black_shade, 0, 0, 1024, 1024, true, false);
            addSprite("", LogoResID, (ViewMain.SIZE_IN_MEMORY - 200)/2, 100, 200, 200, true, false);

            int nWidth = 160;
            int x = (ViewMain.SIZE_IN_MEMORY - 3*(nWidth+1) )/2;
            int y = 500;
            addSprite("", R.drawable.inv_frame, x, y, nWidth, nWidth, true, false);
            Item1Sprite = addSprite("item1", 0, x, y, nWidth, nWidth, true, true);
            x += nWidth + 1;
            addSprite("", R.drawable.inv_frame, x, y, nWidth, nWidth, true, false);
            Item2Sprite = addSprite("item2", 0, x, y, nWidth, nWidth, true, true);
            x += nWidth + 1;
            addSprite("", R.drawable.inv_frame, x, y, nWidth, nWidth, true, false);
            Item3Sprite = addSprite("item3", 0, x, y, nWidth, nWidth, true, true);
            x += nWidth + 1;

            addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
        }

        Item1Sprite.setResourceID( (Item1 != null ? Item1.ResID : 0) );
        Item2Sprite.setResourceID( (Item2 != null ? Item2.ResID : 0) );
        Item3Sprite.setResourceID( (Item3 != null ? Item3.ResID : 0) );

    }

    @Override
    public void DrawOnCanvas(Canvas cvs, Paint paint, int pnOffsetX, int pnOffsetY) {

        UpdateWhat2Draw();

        super.DrawOnCanvas(cvs, paint, pnOffsetX, pnOffsetY);
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("exit")) {
            app.egHidePuzzle();
        }
        else if (sID.equals("item1")) {
            if (Item1 != null)
            {
                app.egAddToInventory( Item1.ID );
                Item1 = null;
            } else {
                // Item is empty
                AItem InvItem = app.Inventory.getSelectedItem();
                if (InvItem != null)
                {
                    app.Inventory.ItemsHold_Remove(InvItem.ID);
                    Item1 = InvItem;
                }
            }
        }
        else if (sID.equals("item2")) {
            if (Item2 != null)
            {
                app.egAddToInventory( Item2.ID );
                Item2 = null;
            } else {
                // Item is empty
                AItem InvItem = app.Inventory.getSelectedItem();
                if (InvItem != null)
                {
                    app.Inventory.ItemsHold_Remove(InvItem.ID);
                    Item2 = InvItem;
                }
            }

        }
        else if (sID.equals("item3")) {
            if (Item3 != null)
            {
                app.egAddToInventory( Item3.ID );
                Item3 = null;
            } else {
                // Item is empty
                AItem InvItem = app.Inventory.getSelectedItem();
                if (InvItem != null)
                {
                    app.Inventory.ItemsHold_Remove(InvItem.ID);
                    Item3 = InvItem;
                }
            }

        }
    }

}
