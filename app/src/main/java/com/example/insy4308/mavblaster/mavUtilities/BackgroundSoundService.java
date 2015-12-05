package com.example.insy4308.mavblaster.mavUtilities;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.insy4308.mavblaster.R;

public class BackgroundSoundService extends Service {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer player;
    private int length = 0;


    public BackgroundSoundService(){}

    public class ServiceBinder extends Binder {
        public BackgroundSoundService getService(){
            return BackgroundSoundService.this;
        }
    }


    public IBinder onBind(Intent arg0) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.song);
        player.setLooping(true);
        player.setVolume(50, 50);
    }




    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_STICKY;
    }

    public void pauseMusic(){
        if(player.isPlaying()){
            player.pause();
            length = player.getCurrentPosition();
        }
    }

    public void resumeMusic(){
        if(player.isPlaying()==false){
            player.seekTo(length);
            player.start();


        }
    }

    public void stopMusic(){
        player.stop();
        player.release();
        player = null;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            try{
                player.stop();
                player.release();
            }
            finally {
                player = null;
            }
        }
    }


    public void onLowMemory() {
        super.onLowMemory();
    }



    public IBinder onUnBind(Intent arg0){
        return null;
    }
}

