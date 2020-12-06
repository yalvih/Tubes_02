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

import com.example.tubes_02.BackgroundSoundService;
import com.example.tubes_02.R;
import com.example.tubes_02.presenter.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    private FragmentManager fragmentManager;
    private MainMenuFragment mainMenuFragment;
    private GameOverFragment gameOverFragment;
    private PianoTilesGameFragment pianoTilesGameFragment;
    private LeaderboardFragment leaderboardFragment;
    private SettingFragment settingFragment;
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = this.getPreferences(MODE_PRIVATE);
        spEditor = sp.edit();

//        new Utils(this.sp.getInt("DARK_THEME", 0));
//        Utils.setThemeOnCreate(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        this.gameOverFragment = GameOverFragment.newInstance("tiles");
        this.mainMenuFragment = MainMenuFragment.newInstance("tiles");
        this.pianoTilesGameFragment = PianoTilesGameFragment.newInstance("tiles");
        this.leaderboardFragment = LeaderboardFragment.newInstance("tiles");
        this.settingFragment = SettingFragment.newInstance("tiles");
        this.fragmentManager = this.getSupportFragmentManager();

        changePage(1);
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
        ft.commit();
    }

    @Override
    public void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }

    @Override
    public void PlayBackgroundSound() {
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
    }

    @Override
    public void changeToMusic(int music) {
        if (music == 0) {
            Utils.changeToMusic(this, Utils.MUSIC_1);
        }
        else if (music == 1) {
            Utils.changeToMusic(this, Utils.MUSIC_2);
        }
        else {
            Utils.changeToMusic(this, Utils.MUSIC_3);
        }
    }
}