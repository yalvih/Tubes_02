package com.example.tubes_02.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;
import com.example.tubes_02.presenter.MainMenuPresenter;

public class MainMenuFragment extends Fragment implements View.OnClickListener{
    Button play, leaderboard, settings, exit;
    FragmentListener fragmentListener;
    MainMenuPresenter mainMenuPresenter;
    DBHandler dbHandler;

    public static MainMenuFragment newInstance(String title) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        args.putString("main_game", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        this.play = view.findViewById(R.id.play);
        this.leaderboard = view.findViewById(R.id.leaderboard);
        this.settings = view.findViewById(R.id.settings);
        this.exit = view.findViewById(R.id.exit);
        this.dbHandler = new DBHandler(this.getActivity());
        this.mainMenuPresenter = new MainMenuPresenter(dbHandler);

        this.play.setOnClickListener(this);
        this.leaderboard.setOnClickListener(this);
        this.settings.setOnClickListener(this);
        this.exit.setOnClickListener(this);

        this.fragmentListener.PlayBackgroundSound();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.fragmentListener = (FragmentListener)context;
        }
        else {
            throw new ClassCastException(context.toString() + " must implement FragmentListener!");
        }
    }

    @Override
    public void onClick(View v) {
        if(v==play){
            this.fragmentListener.changePage(2);
        }
        if(v==leaderboard){
            this.fragmentListener.changePage(4);
        }
        if(v==settings){
            this.fragmentListener.changePage(5);
        }
        if (v==exit){
            this.fragmentListener.closeApplication();
        }
    }
}
