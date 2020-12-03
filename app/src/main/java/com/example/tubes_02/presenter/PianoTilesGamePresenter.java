package com.example.tubes_02.presenter;

import android.widget.ImageView;

import com.example.tubes_02.view.Coordinate;
import com.example.tubes_02.view.UIThreadedWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PianoTilesGamePresenter {
    public LinkedList<PianoThread> listTiles;
    public Random rnd;
    UIThreadedWrapper threadWrapper;
    ImageView imageView;

    public PianoTilesGamePresenter(UIThreadedWrapper threadWrapper, ImageView imageView) {
        this.listTiles = new LinkedList<>();
        this.rnd = new Random();
        this.threadWrapper = threadWrapper;
        this.imageView = imageView;
    }

    public void generateTiles(){
        int random = rnd.nextInt(4);
        PianoThread pt = new PianoThread(threadWrapper, this.imageView.getWidth(), this.imageView.getHeight(), random);
        pt.start();
        this.listTiles.addFirst(pt);
    }

    public void clicked(Coordinate c){
        for (int i = 0; i < listTiles.size(); i++) {
            this.listTiles.get(i).checkInput(c);
        }
    }

    public void clearList(){
        this.listTiles.removeLast();
    }

    public void gameOver(PlayThread playThread){
        playThread.stopThread();
    }
}
