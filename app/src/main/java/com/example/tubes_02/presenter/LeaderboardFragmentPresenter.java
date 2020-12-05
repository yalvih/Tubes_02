package com.example.tubes_02.presenter;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.model.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragmentPresenter {
    public List<Player> players;
    public ILeaderboard ui;
    public DBHandler dbHandler;

    public LeaderboardFragmentPresenter(ILeaderboard ui, DBHandler dbHandler) {
        this.players = new ArrayList<>();
        this.ui = ui;
        this.dbHandler = dbHandler;
    }

    public interface ILeaderboard {
        void UpdateList(List<Player> data);
    }

    public void loadData() {
        this.players = dbHandler.getHighScores();
        this.ui.UpdateList(this.players);
    }
}
