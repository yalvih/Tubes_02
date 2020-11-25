package com.example.tubes_02.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.tubes_02.R;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    private FragmentManager fragmentManager;
    private MainMenuFragment mainMenuFragment;
    private GameOverFragment gameOverFragment;
    private PianoTilesGameFragment pianoTilesGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameOverFragment = GameOverFragment.newInstance("tiles");
        this.mainMenuFragment = MainMenuFragment.newInstance("tiles");
        this.pianoTilesGameFragment = PianoTilesGameFragment.newInstance("tiles");
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
        ft.commit();
    }
}