package com.example.tubes_02.view;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class UIThreadedWrapper extends Handler {
    protected final static int DRAW_TILE = 0;
    protected final static int CLEAR_TILE = 1;
    protected final static int ADD_SCORE = 2;
    protected final static int GAME_OVER = 3;

    protected PianoTilesGameFragment fragment;

    public UIThreadedWrapper(PianoTilesGameFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (msg.what == UIThreadedWrapper.DRAW_TILE) {
            Coordinate param = (Coordinate) msg.obj;
            this.fragment.drawTile(param);
        }
        else if (msg.what == UIThreadedWrapper.CLEAR_TILE) {
            Coordinate param = (Coordinate) msg.obj;
            this.fragment.clearTile(param);
        }
        else if(msg.what == UIThreadedWrapper.ADD_SCORE) {
            this.fragment.addScore();
        }
        else if(msg.what == UIThreadedWrapper.GAME_OVER) {
            this.fragment.gameOver();
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
}
