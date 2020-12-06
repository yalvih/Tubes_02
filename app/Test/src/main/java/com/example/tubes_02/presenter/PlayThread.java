package com.example.tubes_02.presenter;

import android.view.View;

import com.example.tubes_02.view.UIThreadedWrapper;

public class PlayThread extends Thread {

    boolean isStop;
    UIThreadedWrapper uiThreadedWrapper;

    public PlayThread(UIThreadedWrapper uiThreadedWrapper){
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.isStop = false;
    }

    public void run(){
        while(!isStop){
            try {
                Thread.sleep(1200);
                this.uiThreadedWrapper.generateTile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return ;
    }

    public void stopThread(){
        this.isStop = true;
    }




}
