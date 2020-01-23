package com.ale059.escapegameplasticineworld1;

public class ASceneItemView extends AScene {

    public boolean Visible = false;
    protected AItem ActiveItem = null;
    protected ASprite ItemSprite = null;

    public ASceneItemView()
    {
        ASprite oSprite = null;
        oSprite = addSprite("bg", R.drawable.bg_black_shade,     0,   0, true, false);
        oSprite.Width = oSprite.Height = 1024;
        oSprite = addSprite("frame", R.drawable.inv_frame_view,  112, 112, true, false);
        oSprite = addSprite("item", 0, 192, 192, true, true);
        oSprite.Width = oSprite.Height = 640;
        ItemSprite = oSprite;

        addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
    }


    @Override
    public void onSpriteTouch(ASprite poSprite, int pX, int pY)
    {
        if (poSprite == null)
            return;
        String sID = poSprite.ID;
        if (sID.equals("exit"))
            Visible = false;
        else if (poSprite == ItemSprite)
        {
            if (ActiveItem != null)
            {
                String sHotSpotID = ActiveItem.getHotSpotAt(pX, pY);
                if (sHotSpotID != null)
                    app.Inventory.onItemHotSpotTouch( ActiveItem.ID+"->"+sHotSpotID );
                else {
                    AItem pSelItem = app.Inventory.getSelectedItem();
                    if (pSelItem != null)
                        if (ActiveItem != pSelItem)
                            app.Inventory.onItemHotSpotTouch(ActiveItem.ID + "+" + pSelItem.ID );
                }
            }
        }
        else if (sID.equals("speech_1"))
            ;

    }

    public void ShowItem( AItem poItem)
    {
        Visible = true;
        ActiveItem = poItem;
        if (ActiveItem == null) {
            Visible = false;
            return;
        }

        ItemSprite.setResourceID( ActiveItem.ResID );

    }

}
