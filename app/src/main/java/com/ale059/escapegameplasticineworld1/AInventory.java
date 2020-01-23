package com.ale059.escapegameplasticineworld1;


// test commit1
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;

public class AInventory extends AScene {

    protected final ArrayList<AItem> ListItems_Hold = new ArrayList<AItem>();
    public int Offset = 0;
    protected int SelectedIndex = -1;
    protected ASprite[] InvCellsFrame = new ASprite[5];
    protected ASprite[] InvCellsItem = new ASprite[5];

    protected HashMap<String, AItem> ListItems_All = new HashMap<String, AItem>();


    public AInventory()
    {
        AItem oItem = null;
        oItem = CreateItem("cheese_worm", R.drawable.item_cheese_worm)
            .addHotSpot(0, "worm", 345, 440, 58, 63);
        CreateItem("cheese", R.drawable.item_cheese);
        CreateItem("dagger", R.drawable.item_dagger);
        CreateItem("banner", R.drawable.item_banner);
        CreateItem("worm", R.drawable.item_worm);
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
            int x = 0;
            oSprite = addSprite("inv_left", R.drawable.inv_left,x,0,true,true);
            x += oSprite.getBitmap().getWidth()+1;
            for (int i=0; i<InvCellsFrame.length; i++)
            {
                InvCellsFrame[i] = oSprite = addSprite(null, R.drawable.inv_frame,x,0,true,true);
                int nWidth = oSprite.getBitmap().getWidth();
                InvCellsItem[i] = oSprite = addSprite(null, 0,x,0,true,false);
                oSprite.Width = oSprite.Height = nWidth;

                x += nWidth+1;
            }

            oSprite = addSprite("inv_right", R.drawable.inv_right,x,0,true,true);
            x += oSprite.getBitmap().getWidth()+1;
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
    public void DrawOnCanvas(Canvas cvs, Paint paint) {

        UpdateWhat2Draw();

        super.DrawOnCanvas(cvs, paint);
    }

    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
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
                        app.SceneItemView.ShowItem( ListItems_Hold.get(SelectedIndex) );
                    }
                    else
                        SelectedIndex = (nNewPos<ListItems_Hold.size() ? nNewPos : -1 );
                }
            }

        }
    }

    public AItem ItemsHold_Add(String psItemID) {
        AItem oItem = ListItems_All.get(psItemID);
        if (oItem != null)
            if (ListItems_Hold.contains(oItem) == false)
                ListItems_Hold.add(oItem);
        return oItem;
    }

    public AItem ItemsHold_Remove(String psItemID) {
        AItem oItem = ListItems_All.get(psItemID);
        if (oItem != null)
            if (ListItems_Hold.remove(oItem) == false)
                return oItem;
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
                //ItemsHold_Add("worm");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
            }

        }
        else if (psLongID.equals("flag->banner"))
        {
            oNewItem = ItemsHold_Replace("flag", "stick");
            if (oNewItem != null) {
                app.egAddToInventory("banner");
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("cheese+worm"))
        {
            oNewItem = ItemsHold_Replace("cheese", "cheese_worm");
            if (oNewItem != null) {
                ItemsHold_Remove("worm");
                app.egAddToInventory("cheese_worm");
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
                app.egAddToInventory("stick");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
        else if (psLongID.equals("stick+banner"))
        {
            oNewItem = ItemsHold_Replace("stick", "flag");
            if (oNewItem != null) {
                ItemsHold_Remove("banner");
                app.egAddToInventory("flag");
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
                app.egAddToInventory("frod");
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
                app.egAddToInventory("frodworm");
                //if (app.SceneItemView.Visible)
                //    app.SceneItemView.ShowItem( oNewItem );
                SelectedIndex = ListItems_Hold.indexOf( oNewItem );
            }
        }
    }

}
