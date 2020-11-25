package com.example.tubes_02.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;

//Main game fragment

public class PianoTilesGameFragment extends Fragment implements View.OnClickListener {
    private FragmentListener listener;

    public static PianoTilesGameFragment newInstance(String title) {
        PianoTilesGameFragment fragment = new PianoTilesGameFragment();
        Bundle args = new Bundle();
        args.putString("main_game", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piano_tiles_game_fragment, container, false);

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