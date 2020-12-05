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
        while(!isStopped){
            try {
//                Easy  : 955
//                Normal:
//                Hard  : 225
                Thread.sleep(955);
                this.uiThreadedWrapper.generateTile();
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
