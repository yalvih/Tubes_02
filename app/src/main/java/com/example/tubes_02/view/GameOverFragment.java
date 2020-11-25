package com.example.tubes_02.view;

//Game over fragment

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;

public class GameOverFragment extends Fragment implements View.OnClickListener {
    private FragmentListener listener;

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

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.listener = (FragmentListener)context;
        }
        else {
            throw new ClassCastException(context.toString() + " must implement FragmentListener!");
        }
    }

    @Override
    public void onClick(View v) {
//        Onclick goes here
    }
}
//delete this when you receive it