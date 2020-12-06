package com.example.tubes_02.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;
import com.example.tubes_02.presenter.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    public static boolean gameOver = false;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    MediaPlayer player;
    int musicCode = 1;
    private FragmentManager fragmentManager;
    private MainMenuFragment mainMenuFragment;
    private GameOverFragment gameOverFragment;
    private PianoTilesGameFragment pianoTilesGameFragment;
    private SubmitHSFragment highScoreFragment;
    private LeaderboardFragment leaderboardFragment;
    private SettingFragment settingFragment;
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = this.getPreferences(MODE_PRIVATE);
        spEditor = sp.edit();

        new Utils(this.sp.getInt("DARK_THEME", 0));
        Utils.setThemeOnCreate(this);

        this.player = new MediaPlayer();

        if (musicCode==1){
            player = MediaPlayer.create(this, R.raw.music_1);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        this.gameOverFragment = GameOverFragment.newInstance("tiles");
        this.mainMenuFragment = MainMenuFragment.newInstance("tiles");
        this.pianoTilesGameFragment = PianoTilesGameFragment.newInstance("tiles");
        this.highScoreFragment = SubmitHSFragment.newInstance("tiles");
        this.leaderboardFragment = LeaderboardFragment.newInstance("tiles");
        this.settingFragment = SettingFragment.newInstance("tiles");
        this.fragmentManager = this.getSupportFragmentManager();

        changePage(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()){
            player.stop();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
            }
            else {
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (musicCode == 1) {
            player.stop();
            player = MediaPlayer.create(this, R.raw.music_1);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
        else if (musicCode == 2) {
            player.stop();
            player= MediaPlayer.create(this, R.raw.music_2);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
        else {
            player.stop();
            player = MediaPlayer.create(this, R.raw.music_3);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page == 1) {
            ft.replace(R.id.fragment_container, this.mainMenuFragment).addToBackStack(null);
        }
        else if (page == 2) {
            ft.replace(R.id.fragment_container, this.pianoTilesGameFragment).addToBackStack(null);
        }
        else if (page == 3) {
            ft.replace(R.id.fragment_container, this.gameOverFragment).addToBackStack(null);
        }
        else if (page == 4) {
            ft.replace(R.id.fragment_container, this.leaderboardFragment).addToBackStack(null);
        }
        else if (page == 5) {
            ft.replace(R.id.fragment_container, this.settingFragment).addToBackStack(null);
        }
        else if (page == 6) {
            ft.replace(R.id.fragment_container, this.highScoreFragment).addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public void changeTheme(int theme) {
        if (theme == 1) {
            Utils.changeToTheme(this, Utils.THEME_LIGHT);
        }
        else if (theme == 2) {
            Utils.changeToTheme(this, Utils.THEME_DARK);
        }
        else {
            Utils.changeToTheme(this, Utils.THEME_UNSET);
        }
    }

    @Override
    public void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }

    @Override
    public void selectMusic() {
        musicCode++;
        if (musicCode == 4){
            musicCode = 1;
        }

        if (musicCode == 1) {
            player.stop();
            player = MediaPlayer.create(this, R.raw.music_1);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
        else if (musicCode == 2) {
            player.stop();
            player= MediaPlayer.create(this, R.raw.music_2);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
        else {
            player.stop();
            player = MediaPlayer.create(this, R.raw.music_3);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }
    }
}