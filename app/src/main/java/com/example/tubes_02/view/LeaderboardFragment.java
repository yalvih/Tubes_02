package com.example.tubes_02.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;

public class LeaderboardFragment extends Fragment implements View.OnClickListener{

    ListView listView;
    Button backMainMenu;
    FragmentListener fragmentListener;
    LeaderboardFragmentAdapter lbAdapter;

    public static LeaderboardFragment newInstance(String title) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString("game_over", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_fragment, container, false);

        this.listView = view.findViewById(R.id.list_player);
        this.lbAdapter = new LeaderboardFragmentAdapter(this.getContext());
        this.listView.setAdapter(this.lbAdapter);
        this.backMainMenu = view.findViewById(R.id.back_to_main_menu);

        this.backMainMenu.setOnClickListener(this);

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
        if(v == backMainMenu){
            this.fragmentListener.changePage(1);
        }
    }
}
