package com.example.tubes_02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment implements View.OnClickListener {

    Button backMainMenu, change_music, change_theme, delete_all;
    FragmentListener fragmentListener;
    DBHandler dbHandler; //untuk delete all data
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    MusicListener musicListener;
    int darkTheme;

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
        this.dbHandler = new DBHandler(this.getActivity());
        sp = this.getActivity().getPreferences(MODE_PRIVATE);
        spEditor = sp.edit();

        this.backMainMenu = view.findViewById(R.id.back_to_main_menu);
        this.change_music = view.findViewById(R.id.change_music);
        this.change_theme = view.findViewById(R.id.change_theme);
//        this.delete_all = view.findViewById(R.id.delete_all);

        this.darkTheme = this.sp.getInt("DARK_THEME", 0);
        if (this.darkTheme == 2) {
            this.change_theme.setText("ENABLE");
        }
        else this.change_theme.setText("DISABLE");

        this.backMainMenu.setOnClickListener(this);
        this.change_music.setOnClickListener(this);
        this.change_theme.setOnClickListener(this);
//        this.delete_all.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.fragmentListener = (FragmentListener)context;
        }
        else {
            throw new ClassCastException(context.toString() + " must implement Fragment Listener!");
        }
    }

    @Override
    public void onClick(View v) {
        sp = this.getActivity().getPreferences(MODE_PRIVATE);
        spEditor = sp.edit();

        if (v == this.change_theme) {
            if (this.darkTheme == 2) {
                this.spEditor.putInt("DARK_THEME", 1);
                spEditor.apply();
                fragmentListener.changeTheme(1);
            }
            else {
                this.spEditor.putInt("DARK_THEME", 2);
                spEditor.apply();
                fragmentListener.changeTheme(2);
            }
        }

        if(v==backMainMenu){
            this.fragmentListener.changePage(1);
        }

        if(v==change_music){
            Log.d("test", "onClick: Cli");
            this.fragmentListener.selectMusic();
        }
    }
}
