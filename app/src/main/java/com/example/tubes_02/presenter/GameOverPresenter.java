package com.example.tubes_02.presenter;

import android.widget.Button;

public class GameOverPresenter {
    Button main_menu, play_again;
    IGameOverPresenter iGameOverPresenter;

    public GameOverPresenter(GameOverPresenter.IGameOverPresenter iGameOverPresenter,Button main_menu, Button play_again) {
        this.iGameOverPresenter = iGameOverPresenter;
        this.main_menu = main_menu;
        this.play_again = play_again;
    }

    public interface IGameOverPresenter{
    }

    public void setPlay_again(){
        this.main_menu.setText("");
        this.main_menu.setEnabled(false);
        this.play_again.setEnabled(false);
    }
}
