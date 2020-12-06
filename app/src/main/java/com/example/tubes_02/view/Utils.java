package com.example.tubes_02.view;

// Helper class for dark mode / light mode

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tubes_02.R;

public class Utils {
    private static int themeCode;
    public final static int THEME_UNSET = 0;
    public final static int THEME_LIGHT = 1;
    public final static int THEME_DARK = 2;

    public Utils(int themeCode) {
        this.themeCode = themeCode;
    }

    public static void changeToTheme(AppCompatActivity activity, int theme) {
        themeCode = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void setThemeOnCreate(AppCompatActivity activity) {
        if (themeCode == THEME_UNSET) {
            activity.setTheme(R.style.AppThemeDark);
        }
        else if (themeCode == THEME_LIGHT) {
            activity.setTheme(R.style.AppTheme);
        }
        else if (themeCode == THEME_DARK) {
            activity.setTheme(R.style.AppThemeDark);
        }
        else {
            Log.d("Debug", "Theme Error!");
            activity.setTheme(R.style.AppTheme);
        }
    }
}
