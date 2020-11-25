package com.example.tubes_02.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;
import com.example.tubes_02.presenter.MainMenuPresenter;

public class MainMenuFragment extends Fragment implements View.OnClickListener{
    Button play;
    FragmentListener fragmentListener;
    MainMenuPresenter mainMenuPresenter; //belum ada constructor

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

        this.play.setOnClickListener(this);

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
    }
}
