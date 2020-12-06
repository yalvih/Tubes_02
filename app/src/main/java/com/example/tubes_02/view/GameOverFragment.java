package com.example.tubes_02.view;

//Game over fragment

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;
import com.example.tubes_02.presenter.GameOverPresenter;

public class GameOverFragment extends Fragment implements View.OnClickListener, GameOverPresenter.IGameOverPresenter {
    Button play_again, main_menu;
    private FragmentListener fragmentListener;
    protected GameOverPresenter gameOverPresenter;


    public static GameOverFragment newInstance(String title) {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putString("game_over", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_over_fragment, container, false);

        this.play_again = view.findViewById(R.id.play_again);
        this.main_menu = view.findViewById(R.id.back_to_main_menu);

        this.gameOverPresenter = new GameOverPresenter(this,main_menu,play_again);

        this.play_again.setOnClickListener(this);
        this.main_menu.setOnClickListener(this);

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
        if(v == play_again){
            this.gameOverPresenter.setPlay_again();
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    play_again.setText("Restarting in " + (int) Math.ceil(millisUntilFinished / 1000.0) + "...");
                }
                public void onFinish() {
                    fragmentListener.changePage(2);
                }
            }.start();
        } else if (v == main_menu){
            this.fragmentListener.changePage(1);
        }
    }
}