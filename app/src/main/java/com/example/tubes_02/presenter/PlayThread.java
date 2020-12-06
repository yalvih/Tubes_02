package com.example.tubes_02.presenter;

import android.view.View;

import com.example.tubes_02.view.UIThreadedWrapper;

public class PlayThread extends Thread {

    boolean isStopped = false;
    UIThreadedWrapper uiThreadedWrapper;

    public PlayThread(UIThreadedWrapper uiThreadedWrapper){
        this.uiThreadedWrapper = uiThreadedWrapper;
    }

    public void run(){
        int i = 1;
        while(!isStopped){
            try {
//                Easy  : 955
//                Normal: 555
//                Hard  : 225
                Thread.sleep(225);
                this.uiThreadedWrapper.generateTile();
                if (i % 25 == 0) {
                    this.uiThreadedWrapper.generateTiltPrompt();
                }
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return ;
    }

    public void stopThread(){
        this.isStopped = true;
    }
}
