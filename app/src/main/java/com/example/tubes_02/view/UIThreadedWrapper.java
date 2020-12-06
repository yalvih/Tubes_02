package com.example.tubes_02.view;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tubes_02.presenter.PianoTilesGamePresenter;

public class UIThreadedWrapper extends Handler {
    protected final static int DRAW_TILE = 0;
    protected final static int CLEAR_TILE = 1;
    protected final static int ADD_SCORE = 2;
    protected final static int GAME_OVER = 3;
    protected final static int GENERATE_TILES = 4;
    protected final static int CLEAR_TILES = 5;

    PianoTilesGamePresenter presenter;

    public UIThreadedWrapper(PianoTilesGamePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (msg.what == UIThreadedWrapper.DRAW_TILE) {
            Coordinate param = (Coordinate) msg.obj;
            this.presenter.drawTile(param);
        }
        else if (msg.what == UIThreadedWrapper.CLEAR_TILE) {
            Coordinate param = (Coordinate) msg.obj;
            this.presenter.clearTile(param);
        }
        else if(msg.what == UIThreadedWrapper.ADD_SCORE) {
            this.presenter.addScore();
        }
        else if(msg.what == UIThreadedWrapper.GAME_OVER) {
            this.presenter.gameOver();
        }
        else if(msg.what == UIThreadedWrapper.GENERATE_TILES) {
            this.presenter.generateTiles();
        }
        else if(msg.what == UIThreadedWrapper.CLEAR_TILES) {
            this.presenter.clearList();
        }
    }

    public void drawTile(Coordinate pos) {
        Message msg = new Message();
        msg.what = DRAW_TILE;
        msg.obj = pos;
        this.sendMessage(msg);
    }

    public void clearTile(Coordinate pos) {
        Message msg = new Message();
        msg.what = CLEAR_TILE;
        msg.obj = pos;
        this.sendMessage(msg);
    }

    public void addScore() {
        Message msg = new Message();
        msg.what = ADD_SCORE;
        this.sendMessage(msg);
    }

    public void gameOver() {
        Message msg = new Message();
        msg.what = GAME_OVER;
        this.sendMessage(msg);
    }

    public void generateTile(){
        Message msg = new Message();
        msg.what = GENERATE_TILES;
        this.sendMessage(msg);
    }

    public void clearList(){
        Message msg = new Message();
        msg.what = CLEAR_TILES;
        this.sendMessage(msg);
    }
}
