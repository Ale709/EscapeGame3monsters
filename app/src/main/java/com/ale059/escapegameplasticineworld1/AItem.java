package com.ale059.escapegameplasticineworld1;

import android.graphics.Rect;

import java.util.HashMap;

public class AItem {

    public String ID = null;
    public int ResID = 0;
    protected HashMap<String, Rect> HotSpots = new HashMap<String, Rect>();

    public AItem addHotSpot(int pnState, String psHotSpotID, int X, int Y, int W, int H)
    {
        HotSpots.put(psHotSpotID, new Rect(X,Y,X+W,Y+H) );

        return this;
    }

    public String getHotSpotAt(int pX, int pY)
    {
        for (String sHotSpotID: HotSpots.keySet())
        {
            Rect rc = HotSpots.get(sHotSpotID);
            if (rc != null)
                if (rc.contains(pX,pY)==true)
                    return sHotSpotID;
        }

        return null;
    }

}
