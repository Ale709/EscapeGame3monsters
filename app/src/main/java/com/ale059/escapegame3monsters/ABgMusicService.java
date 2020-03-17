package com.ale059.escapegame3monsters;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class ABgMusicService extends Service {
    MediaPlayer player = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();

        player = MediaPlayer.create(this, R.raw.background_music);
        player.setLooping(true); // зацикливаем
    }

    @Override
    public void onDestroy() {
        if (player != null)
            player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        if (player != null)
            player.start();
    }

    public static void StartPlayback()
    {
        app.getContext().startService(new Intent(app.getContext(), ABgMusicService.class));
    }

    public static void StopPlayback()
    {
        app.getContext().stopService(new Intent(app.getContext(), ABgMusicService.class));
    }
}
