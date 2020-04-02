package com.ale059.escapegame3monsters;


// test commit1
import android.graphics.Canvas;
import android.graphics.Paint;

// test comment 2
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AInventory extends AScene {

    protected final ArrayList<AItem> ListItems_Hold = new ArrayList<AItem>();
    public int Offset = 0;
    protected int SelectedIndex = -1;
    protected ASprite[] InvCellsFrame = new ASprite[6];
    protected ASprite[] InvCellsItem = new ASprite[6];

    protected HashMap<String, AItem> ListItems_All = new HashMap<String, AItem>();


    public AInventory()
    {
        Reset();
    }

    public void Reset()
    {
        ListItems_All.clear();
        ListItems_Hold.clear();
        Offset = 0;
        SelectedIndex = -1;
        //for (int i=0; i<InvCellsFrame.length; i++)
        //    InvCellsFrame[i] = null;
        //for (int i=0; i<InvCellsItem.length; i++)
        //    InvCellsItem[i] = null;


        AItem oItem = null;
        oItem = CreateItem("cheese_worm", R.drawable.item_cheese_worm)
                .addHotSpot(0, "worm", 345, 440, 58, 63);
        CreateItem("cheese", R.drawable.item_cheese);
        CreateItem("dagger", R.drawable.item_dagger);
        CreateItem("banner", R.drawable.item_banner)
                .addHotSpot(0, "banner2", 127, 96, 390, 446);
        CreateItem("worm", R.drawable.item_worm);
        //oItem.AllowMultiple = true;
        CreateItem("mushroom", R.drawable.item_mushroom);
        CreateItem("branch", R.drawable.item_branch);
        CreateItem("stick", R.drawable.item_stick);
        CreateItem("flag", R.drawable.item_flag)
                .addHotSpot(0, "banner", 109, 58, 420, 320);
        CreateItem("shovel", R.drawable.item_shovel);
        CreateItem("rope", R.drawable.item_rope);
        CreateItem("frod", R.drawable.item_frod);
        CreateItem("frodworm", R.drawable.item_frodworm);
        CreateItem("fish", R.drawable.item_fish);
        CreateItem("bone", R.drawable.item_bone);
        CreateItem("scroll", R.drawable.item_scroll);
        CreateItem("apple_b", R.drawable.item_apple_b);
        CreateItem("ring", R.drawable.item_ring);
        CreateItem("potion", R.drawable.item_potion);
        CreateItem("broom", R.drawable.item_broom);

    }

    public String toString()
    {
        StringBuilder sbRet = new StringBuilder();
        for (AItem oItem: ListItems_Hold)
        {
            if (oItem != null)
                sbRet.append( oItem.ID ).append( ',' );
        }
        return sbRet.toString();
    }

    public void fromString(String psItems)
    {
        ListItems_Hold.clear();
        Offset = 0;
        SelectedIndex = -1;

        if (psItems == null)
            return;

        String[] aItemsID = psItems.split(",");

        for (String sID: aItemsID)
            ItemsHold_Add(sID);
    }

    protected AItem CreateItem(String psID, int pnResID)
    {
        AItem oItem = new AItem();
        oItem.ID = psID;
        oItem.ResID = pnResID;

        ListItems_All.put(oItem.ID, oItem);

        return oItem;
    }

    protected void UpdateWhat2Draw()
    {
        ASprite oSprite = null;
        if (mSprites2Draw.size() == 0)
        {
            // initial generate
            //int nWidth = ViewMain.SIZE_INV-4;//oSprite.getBitmap().getWidth();
            int nPadding = 2;
            int nWidth = (ViewMain.SIZE_IN_MEMORY) / (InvCellsFrame.length+1) - nPadding;
            int nHeight = nWidth;
            int nLRwidth = (ViewMain.SIZE_IN_MEMORY - InvCellsFrame.length*(nWidth+nPadding))/2 - nPadding;
            int x = 0;
            int y = (ViewMain.SIZE_INV - nHeight)/2;
            oSprite = addSprite("inv_left", R.drawable.inv_left,x,y, nLRwidth, nHeight,true,true);
            //x += oSprite.getBitmap().getWidth()+1;
            x += nLRwidth+nPadding;
            for (int i=0; i<InvCellsFrame.length; i++)
            {
                InvCellsFrame[i] = oSprite = addSprite(null, R.drawable.inv_frame,x,y, nWidth, nHeight, true,true);
                InvCellsItem[i] = oSprite = addSprite(null, 0,x,y, nWidth, nHeight,true,false);
                //oSprite.Width = oSprite.Height = nWidth;
                x += nWidth+nPadding;
            }

            oSprite = addSprite("inv_right", R.drawable.inv_right,x,y, nLRwidth, nHeight, true,true);
            x += nLRwidth+1;
        }

        for (int i=0; i<InvCellsFrame.length; i++)
        {
            oSprite = InvCellsFrame[i];
            oSprite.setResourceID( (Offset+i==SelectedIndex ? R.drawable.inv_frame_sel : R.drawable.inv_frame ) );
        }

        for (int i=0; i<InvCellsItem.length; i++)
        {
            oSprite = InvCellsItem[i];
            int nItemIndex = Offset+i;
            int nNewResID = 0;
            //AItem oItem = null;
            if (nItemIndex<ListItems_Hold.size())
                nNewResID = ListItems_Hold.get(nItemIndex).ResID;
            oSprite.setResourceID( nNewResID );
        }

    }

    @Override
    public void DrawOnCanvas(Canvas cvs, Paint paint, int pnOffsetX, int pnOffsetY) {

        UpdateWhat2Draw();

        super.DrawOnCanvas(cvs, paint, pnOffsetX, pnOffsetY);
    }

    private long mPrevTouchTime = 0;
    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {

        long nCurTouchTime = System.currentTimeMillis();

        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("inv_left")) {
            Offset = Math.max(0, Offset-1);
        }
        else if (sID.equals("inv_right")) {
            Offset = Math.max(0, Math.min(Offset + 1, ListItems_Hold.size()-5));
        }
        else {
            for (int i=0; i<InvCellsFrame.length; i++) {
                if (poSprite==InvCellsFrame[i])
                {
                    int nNewPos = Offset + i;
                    if (nNewPos==SelectedIndex) {
                        if (nCurTouchTime-mPrevTouchTime <= 500)
                            app.SceneItemView.ShowItem( ListItems_Hold.get(SelectedIndex) );
                        else
                            SelectedIndex = -1;
                    }
                    else
                        SelectedIndex = (nNewPos<ListItems_Hold.size() ? nNewPos : -1 );

                    mPrevTouchTime = nCurTouchTime;
                }
            }

        }
    }

    public AItem ItemsHold_Add(String psItemID) {
        AItem oItem = ListItems_All.get(psItemID);
        if (oItem != null)
            //if (ListItems_Hold.contains(oItem) == false)
                ListItems_Hold.add(oItem);

        app.egWriteSingleSetting("Inventory", this.toString() );

        return oItem;
    }

    public AItem ItemsHold_Remove(String psItemID) {
        AItem oItem = getSelectedItem();
        if (oItem != null)
            if (oItem.ID.equals(psItemID)) {
                AItem oRet = ListItems_Hold.remove(this.SelectedIndex);
                app.egWriteSingleSetting("Inventory", this.toString() );
                return oRet;

            }

        oItem = ListItems_All.get(psItemID);
        if (oItem != null)
            if (ListItems_Hold.remove(oItem) == false) {
                app.egWriteSingleSetting("Inventory", this.toString() );
                return oItem;
            }

        return null;
    }

    public AItem ItemsHold_Replace(String psFromID, String psToID)
    {
        AItem oItem1 = ListItems_All.get(psFromID);
        if (oItem1 == null)
            return null;
        int nIndex = ListItems_Hold.indexOf(oItem1);

        oItem1 = ListItems_All.get(psToID);
        if (oItem1 == null)
            return null;
        ListItems_Hold.add(nIndex, oItem1);
        ListItems_Hold.remove(nIndex+1);

        app.egWriteSingleSetting("Inventory", this.toString() );

        return oItem1;
    }

    public AItem getSelectedItem()
    {
        if (SelectedIndex<0)
            return null;
        if (SelectedIndex>=ListItems_Hold.size())
            return null;

        return ListItems_Hold.get(SelectedIndex);
    }

    public AItem getItemByID(String psItemID)
    {
        return ListItems_All.get(psItemID);
    }

    public String getSelectedItemID()
    {
        if (SelectedIndex<0)
            return null;
        if (SelectedIndex>=ListItems_Hold.size())
            return null;
        AItem oItem = ListItems_Hold.get(SelectedIndex);
        if (oItem != null)
            return oItem.ID;

        return null;
    }

    public void onItemHotSpotTouch( String psLongID )
    {
        if (psLongID == null)
            return;

        AItem oNewItem = null;
        if (psLongID.equals("cheese_worm->worm"))
        {
            oNewItem = ItemsHold_Replace("cheese_worm", "cheese");
            if (oNewItem != null) {
                app.egAddToInventory("worm");
                app.egSetProgressEventValue("s2_worm_take", 1);
                app.egShowItem("worm");
                //ItemsHold_Add("worm");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
            }

        }
        else if (psLongID.equals("banner->banner2"))
        {
            oNewItem = getItemByID("banner");
            if (oNewItem != null) {
                if (oNewItem.ResID == R.drawable.item_banner) {
                    oNewItem.ResID = R.drawable.item_banner_back;
                    app.egSetProgressEventValue("s5_banner_look", 1);
                    app.egShowItem("banner");
                }
                else {
                    oNewItem.ResID = R.drawable.item_banner;
                    app.egShowItem("banner");
                }
            }
        }
        else if (psLongID.equals("flag->banner"))
        {
            oNewItem = ItemsHold_Replace("flag", "stick");
            if (oNewItem != null) {
                app.egAddToInventory("banner");
                app.egSetProgressEventValue("s3_flag_make", 0);
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("cheese+worm"))
        {
            oNewItem = ItemsHold_Replace("cheese", "cheese_worm");
            if (oNewItem != null) {
                ItemsHold_Remove("worm");
                app.egSetProgressEventValue("s2_worm_take", 0);
                app.egShowItem("cheese_worm");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("branch+dagger"))
        {
            oNewItem = ItemsHold_Replace("branch", "stick");
            if (oNewItem != null) {
                ItemsHold_Remove("branch");
                app.egSetProgressEventValue("s2_branch_cut", 1);
                app.egShowItem("stick");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("stick+banner"))
        {
            oNewItem = getItemByID("banner");
            if (oNewItem == null)
                return;
            if (oNewItem.ResID != R.drawable.item_banner)
                return;
            oNewItem = ItemsHold_Replace("stick", "flag");
            if (oNewItem != null) {
                ItemsHold_Remove("banner");
                app.egSetProgressEventValue("s3_flag_make", 1);
                app.egShowItem("flag");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("stick+rope"))
        {
            oNewItem = ItemsHold_Replace("stick", "frod");
            if (oNewItem != null) {
                ItemsHold_Remove("rope");
                app.egSetProgressEventValue("s3_frod_make", 1);
                app.egShowItem("frod");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("frod+worm"))
        {
            oNewItem = ItemsHold_Replace("frod", "frodworm");
            if (oNewItem != null) {
                ItemsHold_Remove("worm");
                app.egSetProgressEventValue("s3_frodworm_make", 1);
                app.egShowItem("frodworm");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
    }

}
