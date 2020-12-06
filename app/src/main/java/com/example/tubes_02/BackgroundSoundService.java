package com.example.tubes_02;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tubes_02.view.FragmentListener;
import com.example.tubes_02.view.MusicListener;

import java.util.List;

//public class BackgroundSoundService extends Service implements FragmentListener {

//    public MediaPlayer mediaPlayer;
//    int musicCode = 1;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
////        if (musicCode==1){
////            mediaPlayer = MediaPlayer.create(this, R.raw.music_1);
////            mediaPlayer.setLooping(true); // Set looping
////            mediaPlayer.setVolume(100, 100);
////        }
////
//////        this.musicCode = setMusicCode();
////
//////        if (this.musicListener.selectMusic(musicCode)==0){
//////
//////        }
////
//        mediaPlayer = MediaPlayer.create(this, R.raw.music_1);
//        mediaPlayer.setLooping(true); // Set looping
//        mediaPlayer.setVolume(100, 100);
//
//    }
//
//
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mediaPlayer.start();
//        Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();
//        return startId;
//    }
//
//    public void onStart(Intent intent, int startId) {
//    }
//
//    @Override
//    public void onDestroy() {
//        mediaPlayer.stop();
//        mediaPlayer.release();
//    }
//
//    @Override
//    public void onLowMemory() {
//    }
//
////    public void setMusicCode(int musicCode){
////        this.musicCode = this.musicListener.selectMusic(musicCode);
////    }
//
//    @Override
//    public void changePage(int page) {
//    }
//
//    @Override
//    public void changeTheme(int theme) {
//
//    }
//
//    @Override
//    public void closeApplication() {
//    }
//
//    @Override
//    public void PlayBackgroundSound() {
//    }
//
//    @Override
//    public void selectMusic() {
//        musicCode++;
//        if (musicCode==4){
//            musicCode = 1;
//        }
//
//        if(musicCode==1){
//            mediaPlayer = MediaPlayer.create(this, R.raw.music_1);
//            mediaPlayer.setLooping(true); // Set looping
//            mediaPlayer.setVolume(100, 100);
//        } else if(musicCode==2){
//            mediaPlayer = MediaPlayer.create(this, R.raw.music_2);
//            mediaPlayer.setLooping(true); // Set looping
//            mediaPlayer.setVolume(100, 100);
//        } else if(musicCode==3){
//            mediaPlayer = MediaPlayer.create(this, R.raw.music_3);
//            mediaPlayer.setLooping(true); // Set looping
//            mediaPlayer.setVolume(100, 100);
//        }
//    }
//}
