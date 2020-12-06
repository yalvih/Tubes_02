package com.example.tubes_02.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    ListView listView;
    Button backMainMenu;
    FragmentListener fragmentListener;
    ListView lv2;
    Button hapus;
    LeaderboardFragmentAdapter lbAdapter;
    DBHandler dbh;


    public static SettingFragment newInstance(String title) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString("game_over", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        this.listView = view.findViewById(R.id.list_player);
        this.backMainMenu = view.findViewById(R.id.back_to_main_menu);
        this.hapus = view.findViewById(R.id.btn_hapus);
        this.backMainMenu.setOnClickListener(this);
        this.hapus.setOnClickListener(this);
        this.dbh = new DBHandler(this.getActivity());
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
        if(v==backMainMenu){
            this.fragmentListener.changePage(1);
        }else if(v == hapus){
            dbh.deleteAllPlayer();
        }
    }
}
