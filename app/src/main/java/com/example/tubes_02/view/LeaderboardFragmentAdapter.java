package com.example.tubes_02.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tubes_02.R;
import com.example.tubes_02.model.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragmentAdapter extends BaseAdapter {

    private List<Player> listItem;
    private Context fragment;

    public LeaderboardFragmentAdapter(Context fragment) {
        this.fragment = fragment;
        this.listItem = new ArrayList<>();
    }

    public void updateList(List<Player> newList) {
        this.listItem = newList;
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount(){
        return listItem.size();
    }

    @Override
    public Object getItem(int i){
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i){
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(this.fragment).inflate(R.layout.player_list_strings, parent , false);

        ViewHolder viewHolder = new ViewHolder(view);
        Player currentPlayer = (Player)this.getItem(i);
        viewHolder.updateView(currentPlayer);
        view.setTag(viewHolder);

        return view;
    }

    private class ViewHolder {
        protected TextView title;
        protected TextView score;

        public ViewHolder(View view) {
            this.title = view.findViewById(R.id.list_item_string);
            this.score = view.findViewById(R.id.list_item_string_detail);
        }

        public void updateView(final Player player) {
            this.title.setText(player.getName());
            this.score.setText(player.getScore());
        }
    }
}
