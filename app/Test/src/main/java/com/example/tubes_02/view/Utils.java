package com.example.tubes_02.view;

// Helper class for music

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tubes_02.R;

public class Utils {
    private static int musicCode;
    public final static int MUSIC_1 = 0; //Tetris Music 01
    public final static int MUSIC_2  = 1; //Tetris Music 02
    public final static int MUSIC_3  = 2; //Corneria Starfox

    public Utils(int musicCode) {
        Utils.musicCode = musicCode;
    }

    public static void changeToMusic(AppCompatActivity activity, int music) {
        musicCode = music;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public void setThemeOnCreate() {

        if (musicCode == MUSIC_1) {
//            MediaPlayer.create(getApplicationContext(), R.raw.music_1);
        }
        else if (musicCode == MUSIC_2) {
//            activity.setTheme(R.style.AppTheme);
        }
        else if (musicCode == MUSIC_3) {
//            activity.setTheme(R.style.AppThemeDark);
        }
        else {
            Log.d("Debug", "Theme Error!");
//            activity.setTheme(R.style.AppTheme);
        }
    }
}
