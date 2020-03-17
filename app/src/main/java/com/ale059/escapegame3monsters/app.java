package com.ale059.escapegame3monsters;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

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
    public static final String FILENAME_SETTINGS = "ASettings.cfg";
    private static SharedPreferences mSettings = null;

    public static int IsMusicOn = 0;
    public static int IsSoundOn = 0;

    @Override
    public void onCreate()
    {
        mBaseContext = getBaseContext();

        singleton = this;

        mSettings = mBaseContext.getSharedPreferences(FILENAME_SETTINGS, Context.MODE_PRIVATE);

        IsMusicOn = app.egReadSingleSettingInt("Setting_Music_On", 1);
        IsSoundOn = app.egReadSingleSettingInt("Setting_Sound_On", 1);

        app.egPrepareSound( R.raw.snd_boilering );
        app.egPrepareSound( R.raw.snd_bones );
        app.egPrepareSound( R.raw.snd_bookcase_move );
        app.egPrepareSound( R.raw.snd_cat );
        app.egPrepareSound( R.raw.snd_chest_open );
        app.egPrepareSound( R.raw.snd_code_wrong );
        app.egPrepareSound( R.raw.snd_door_open );
        app.egPrepareSound( R.raw.snd_item_take );
        app.egPrepareSound( R.raw.snd_mouse );
        app.egPrepareSound( R.raw.snd_owl );
        app.egPrepareSound( R.raw.snd_princess );
        app.egPrepareSound( R.raw.snd_spell );
        app.egPrepareSound( R.raw.snd_success );
        app.egPrepareSound( R.raw.snd_tap );
        app.egPrepareSound( R.raw.snd_web_wipe );
        //app.egPlaySound( R.raw.snd_item_take );

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
    public static int SCENE04 = uid();
    public static int SCENE45 = uid();
    public static int SCENE05 = uid();
    //public static int SCENE_ITEMVIEW = uid();
    public static int SCENE_MENU_MAIN = uid();
    public static int SCENE_MENU_THEEND = uid();
    public static int SCENE_MENU_ABOUT = uid();
    public static int SCENE_MENU_HINT = uid();

    protected static HashMap<Integer, APuzzle> ListPuzzles = new HashMap<Integer, APuzzle>();
    public static int PUZZLE_CHEST1 = uid();
    public static int PUZZLE_CHEST2 = uid();
    public static int PUZZLE_MUSHROOM = uid();
    public static int PUZZLE_MTALK1 = uid();
    public static int PUZZLE_MTALK2 = uid();
    public static int PUZZLE_MTALK3 = uid();
    public static int PUZZLE_DOOR45 = uid();
    public static int PUZZLE_DOOR2 = uid();
    public static int PUZZLE_CAULDRON = uid();

    protected static AScene ActiveScene = null;
    protected static ASceneItemView SceneItemView = new ASceneItemView();;
    protected static AScenePuzzle ScenePuzzle = null;
    public static AInventory Inventory = new AInventory();

    protected static AScene SceneMenu = null;

    protected static HashMap<String, Integer> ListEventValues = new HashMap<String, Integer>();

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
        ListPuzzles.put(PUZZLE_CHEST2, new APuzzle_Chest2());

        oScene = new AScene45();
        ListScenes.put(SCENE45, oScene);
        ListPuzzles.put(PUZZLE_DOOR45, ((AScene45)oScene).CreateDoorPuzzle());

        oScene = new AScene04();
        ListScenes.put(SCENE04, oScene);
        ListPuzzles.put(PUZZLE_DOOR2, ((AScene04)oScene).CreateDoorPuzzle());

        oScene = new AScene05();
        ListScenes.put(SCENE05, oScene);
        ListPuzzles.put(PUZZLE_CAULDRON, new APuzzle_Cauldron());

        //ListScenes.put(SCENE_ITEMVIEW, oScene);

        ActiveScene = ListScenes.get( SCENE01 );

        oScene = new ASceneMenuMain();
        ListScenes.put(SCENE_MENU_MAIN, oScene);

        SceneMenu = ListScenes.get( SCENE_MENU_MAIN );
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
        if (oNewItem!=null)
        {
            app.egPlaySound( R.raw.snd_item_take );

            String sItemID = oNewItem.ID;
            if (sItemID.equals("shovel"))
                egSetProgressEventValue("s2_shovel_take",1);
            else if (sItemID.equals("rope"))
                egSetProgressEventValue("s2_rope_take",1);
            else if (sItemID.equals("broom"))
                egSetProgressEventValue("s5_broom_take",1);
            else if (sItemID.equals("ring"))
                egSetProgressEventValue("s3_ring_take",1);
            else if (sItemID.equals("potion"))
                egSetProgressEventValue("s5_potion_take",1);
        }
        egShowItem(oNewItem);
    }

    public static void egRemoveFromInventory( String psItemID )
    {
        Inventory.ItemsHold_Remove(psItemID);
    }

    public static void egShowItem( AItem poItem )
    {
        app.SceneItemView.ShowItem( poItem );
    }
    public static void egShowItem( String psItemID )
    {
        egShowItem( Inventory.getItemByID(psItemID) );
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


    public static void egResetProgress()
    {
        //app.egSetProgressEventValue("s45_open_door", 0);
        //app.egSetProgressEventValue("s5_cheese_take", 0);
        //app.egSetProgressEventValue("s4_apple_take", 0);
        //app.egSetProgressEventValue("s4_mouse_owl", 0);
        //app.egSetProgressEventValue("s4_scroll_take", 0);
        //app.egSetProgressEventValue("s5_scroll_give", 0);
        //app.egSetProgressEventValue("s5_dagger_take", 0);
        //app.egSetProgressEventValue("s5_banner_take", 0);
        //app.egSetProgressEventValue("s5_banner_look", 0);
        //app.egSetProgressEventValue("s2_chest1_open", 0);
        //app.egSetProgressEventValue("s2_shovel_take", 0);
        //app.egSetProgressEventValue("s2_shovel_dig", 0);
//        app.egSetProgressEventValue("s2_bone_take", 0);
//        app.egSetProgressEventValue("s2_worm_take", 0);
        //app.egSetProgressEventValue("s2_mushroom_take", 0);
        //app.egSetProgressEventValue("s5_ingridients", 0);
        //app.egSetProgressEventValue("s5_potion_appear", 0);
        //app.egSetProgressEventValue("s5_potion_take", 0);
        //app.egSetProgressEventValue("s1_potion_give", 0);
        //app.egSetProgressEventValue("s1_bone_solve", 0);
        //app.egSetProgressEventValue("s1_bone_give", 0);
        //app.egSetProgressEventValue("s3_skeleton_appear", 0);
        //app.egSetProgressEventValue("s2_branch_take", 0);
        //app.egSetProgressEventValue("s2_branch_cut", 0);
        //app.egSetProgressEventValue("s2_rope_take", 0);
        //app.egSetProgressEventValue("s3_frod_make", 0);
        //app.egSetProgressEventValue("s2_worm2_take", 0);
        //app.egSetProgressEventValue("s3_frodworm_make", 0);
        //app.egSetProgressEventValue("s3_skeleton_fish", 0);
        //app.egSetProgressEventValue("s1_fish_solve", 0);
        //app.egSetProgressEventValue("s1_fish_give", 0);
        //app.egSetProgressEventValue("s5_cat_appear", 0);
        //app.egSetProgressEventValue("s3_flag_make", 0);
        //app.egSetProgressEventValue("s3_flag_put", 0);
        //app.egSetProgressEventValue("s3_chest2_hint", 0);
        app.egSetProgressEventValue("s3_chest2_open", 0);
        //app.egSetProgressEventValue("s3_ring_take", 0);
        //app.egSetProgressEventValue("s3_ring_solve", 0);
        //app.egSetProgressEventValue("s3_ring_give", 0);
        //app.egSetProgressEventValue("s4_princess_appear", 0);
        //app.egSetProgressEventValue("s4_bookcase_moved", 0);
        //app.egSetProgressEventValue("s5_broom_take", 0);
        app.egSetProgressEventValue("s4_broom_use", 0);
        app.egSetProgressEventValue("s4_door_hint", 0);
        app.egSetProgressEventValue("s4_door_open", 0);



    }

    //protected static HashMap<Integer, MediaPlayer> ListSounds = new HashMap<Integer, MediaPlayer>();
    protected static SoundPool mSoundPool = null;
    protected static HashMap<Integer, Integer> ListSounds = new HashMap<Integer, Integer>();

    public static int egPrepareSound( int pnResID )
    {
        Integer i = pnResID;
//        MediaPlayer mp = ListSounds.get(i);
//        if (mp == null) {
//            mp = MediaPlayer.create(mBaseContext, pnResID);
//            try {
//                mp.prepare();
//            } catch (Exception e) {}
//            ListSounds.put(i, mp);
//        }
//        return mp;
        if (mSoundPool == null) {
            int maxStreams = 2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mSoundPool = new SoundPool.Builder()
                        .setMaxStreams(maxStreams)
                        .build();
            } else {
                mSoundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
            }
        }
        Integer nRes = ListSounds.get(i);
        if (nRes == null) {
            nRes = mSoundPool.load(mBaseContext, pnResID, 1);
            ListSounds.put(i, nRes);
        }
        return nRes.intValue();
    }

    public static void egPlaySound( int pnResID )
    {
//        Integer i = pnResID;
//        MediaPlayer mp = egPrepareSound( pnResID );
//        if (mp != null)
//            mp.start();
        int nSoundID = egPrepareSound( pnResID );
        if (mSoundPool != null)
            mSoundPool.play(nSoundID, 1,1,1,0,1);
    }

    public static int egGetProgressEventValue( String psEventID )
    {
        Integer nRet = ListEventValues.get( psEventID );
        if (nRet == null)
            return 0;
        return nRet.intValue();
    }

    public static void egSetProgressEventValue( String psEventID, int pnValue )
    {
        Integer nValue = new Integer(pnValue);
        ListEventValues.put( psEventID, nValue );

        egWriteSingleSetting("Event_"+psEventID, nValue);
    }

    public static void egWriteSingleSetting(String psSettingID, int pnValue)
    {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(psSettingID, pnValue);
        editor.apply();
    }

    public static int egReadSingleSettingInt(String psSettingID, int pnDefaultValue)
    {
        if (mSettings.contains( psSettingID ))
            return mSettings.getInt( psSettingID, pnDefaultValue);
        return pnDefaultValue;
    }

}
