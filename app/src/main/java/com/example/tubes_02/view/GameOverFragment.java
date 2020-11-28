package com.example.tubes_02.view;

//Game over fragment

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;

public class GameOverFragment extends Fragment implements View.OnClickListener {
    Button play_again, main_menu;
    private FragmentListener fragmentListener;


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
        if(v==play_again){
            this.fragmentListener.changePage(2);
        } else if (v==main_menu){
            this.fragmentListener.changePage(1);
        }
    }
}
//delete this when you receive it