package com.ale059.escapegameplasticineworld1;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

public class app extends Application {
    private static app singleton;

    // счетчик уникальных идентификаторов
    private static int uidcount = 1000;
    public static synchronized final int uid()
    {
        uidcount++;
        return uidcount;
    }

    public app getInstance(){
        return singleton;
    }

    private static Context mBaseContext = null;

    @Override
    public void onCreate()
    {
        mBaseContext = getBaseContext();

        singleton = this;

        egCreateItemsAndInventory();
        egCreateScenes();

        super.onCreate();
    }

    public static final Context getContext()
    {
        return mBaseContext;
    }

    public static final String getRString( int pnResId )
    {
        return mBaseContext.getString( pnResId );
    }

    public static final Resources Resources()
    {
        return mBaseContext.getResources();
    }


    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************

    protected static HashMap<Integer, AScene> ListScenes = new HashMap<Integer, AScene>();
    public static int SCENE01 = uid();
    public static int SCENE02 = uid();
    public static int SCENE02PIT = uid();
    public static int SCENE03 = uid();
    public static int SCENE45 = uid();
    public static int SCENE05 = uid();
    public static int SCENE_ITEMVIEW = uid();

    protected static HashMap<Integer, APuzzle> ListPuzzles = new HashMap<Integer, APuzzle>();
    public static int PUZZLE_CHEST1 = uid();
    public static int PUZZLE_MUSHROOM = uid();
    public static int PUZZLE_MTALK1 = uid();
    public static int PUZZLE_MTALK2 = uid();
    public static int PUZZLE_MTALK3 = uid();
    public static int PUZZLE_DOOR45 = uid();

    protected static AScene ActiveScene = null;
    protected static ASceneItemView SceneItemView = new ASceneItemView();;
    protected static AScenePuzzle ScenePuzzle = null;

    public static AInventory Inventory = new AInventory();

    //public static int EG_CODE_TREES = 13232;

    public static void egCreateItemsAndInventory()
    {

    }

    public static void egCreateScenes()
    {
        AScene oScene;

        oScene = new AScene01();
        ListScenes.put(SCENE01, oScene);

        ListPuzzles.put(PUZZLE_MTALK1, ((AScene01)oScene).CreateMTalkPuzzle(1));
        ListPuzzles.put(PUZZLE_MTALK2, ((AScene01)oScene).CreateMTalkPuzzle(2));
        ListPuzzles.put(PUZZLE_MTALK3, ((AScene01)oScene).CreateMTalkPuzzle(3));


        oScene = new AScene02();
        ListScenes.put(SCENE02, oScene);

        ListPuzzles.put(PUZZLE_CHEST1, new APuzzle_Chest1());
        ListPuzzles.put(PUZZLE_MUSHROOM, new APuzzle_Sequence("13232"));

        ListScenes.put(SCENE02PIT, new AScene_Pit());


        oScene = new AScene03();
        ListScenes.put(SCENE03, oScene);

        oScene = new AScene45();
        ListScenes.put(SCENE45, oScene);
        ListPuzzles.put(PUZZLE_DOOR45, ((AScene45)oScene).CreateDoorPuzzle());

        oScene = new AScene05();
        ListScenes.put(SCENE05, oScene);

        //ListScenes.put(SCENE_ITEMVIEW, oScene);

        ActiveScene = ListScenes.get( SCENE01 );



    }

    public static void egMoveToScene( int pnSceneNum )
    {
        AScene oScene = ListScenes.get( pnSceneNum );
        if (oScene != null)
            ActiveScene = oScene;
    }

    public static void egAddToInventory( String psItemID )
    {
        AItem oNewItem = Inventory.ItemsHold_Add(psItemID);
        app.SceneItemView.ShowItem( oNewItem );
    }

    public static boolean egIsSelectedItem( String psItemID )
    {
        AItem oItem = Inventory.getSelectedItem();
        if (oItem != null)
            if (oItem.ID.equals(psItemID))
                return true;
        return false;
    }

    public static void egShowPuzzle( APuzzle poPuzzle )
    {
        if (poPuzzle == null)
            return;

        AScenePuzzle oScene = poPuzzle.getScene();
        if (oScene != null) {
            ScenePuzzle = oScene;
        }

    }

    public static void egHidePuzzle( )
    {
        ScenePuzzle = null;
    }


    public static APuzzle egGetPuzzle( int pnPuzzleID )
    {
        return ListPuzzles.get( pnPuzzleID );
    }
}
