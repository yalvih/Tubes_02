package com.example.tubes_02.view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;
import com.example.tubes_02.presenter.PianoTilesGamePresenter;
import com.example.tubes_02.presenter.SubmitHSPresenter;

import java.util.LinkedList;
import java.util.Random;

public class SubmitHSFragment extends Fragment implements View.OnClickListener {
    FragmentListener fragmentListener;
    EditText name;
    Button submit;
    DBHandler dbHandler;
    SubmitHSPresenter presenter;

    public static SubmitHSFragment newInstance(String title) {
        SubmitHSFragment fragment = new SubmitHSFragment();
        Bundle args = new Bundle();
        args.putString("main_game", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submit_hs_fragment, container, false);

        this.name = view.findViewById(R.id.hs_name);
        this.submit = view.findViewById(R.id.hs_submit);
        this.dbHandler = new DBHandler(this.getActivity());
        this.presenter = new SubmitHSPresenter(this.dbHandler);

        this.submit.setOnClickListener(this);

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
        if (v == this.submit) {
            String playerName = this.name.getText().toString();
            this.presenter.updateName(playerName);
            this.name.setText("");
            this.fragmentListener.changePage(4);
            this.fragmentListener.closeKeyboard();
        }
    }
}
