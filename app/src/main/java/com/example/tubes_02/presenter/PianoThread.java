package com.example.tubes_02.presenter;

import android.util.Log;

import com.example.tubes_02.view.Coordinate;
import com.example.tubes_02.view.UIThreadedWrapper;

public class PianoThread extends Thread {
    protected UIThreadedWrapper uiThreadedWrapper;
    protected int column;
    protected float YIncrement = 1.5f;
    protected float canvasWidth;
    protected float canvasHeight;
    protected Coordinate currentPosition;
    protected boolean stopped = false;
    protected boolean isClicked = false;

    public PianoThread(UIThreadedWrapper uiThreadedWrapper, float canvasWidth, float canvasHeight, int column) {
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.column = column;
    }

    public void stopThread() {
        Log.d("TAG", "stopThread: ");
        this.stopped = true;
    }

    public void run() {
        this.stopped = false;
        currentPosition = new Coordinate(canvasWidth * (column * 2 + 1) / 8, -200);

        while (checkValid(this.currentPosition.getY()) && !this.stopped) {
            try {
                Thread.sleep(2);
                if (!this.isClicked){
                    uiThreadedWrapper.clearTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
                }
                this.currentPosition.setY(this.currentPosition.getY() + this.YIncrement);
                if (!this.isClicked){
                    uiThreadedWrapper.drawTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!this.isClicked) {
            uiThreadedWrapper.gameOver();
        }
        return;
    }

    public void checkInput(Coordinate input) {
        if (!isClicked) {
            if (input.getX() >= this.currentPosition.getX() - canvasWidth / 8 - 2 && input.getX() <= this.currentPosition.getX() + canvasWidth / 8 + 2){
                Log.d("InputCheck", "x check works!");
                if (input.getY() >= this.currentPosition.getY() - 200 && input.getY() <= this.currentPosition.getY() + 200){
                    Log.d("InputCheck", "Clicked on the tile!");
                    this.uiThreadedWrapper.addScore();
                    this.isClicked = true;
                    uiThreadedWrapper.clearTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
                }
            }
        }
    }

    public boolean checkValid(float y){
        if(y > canvasHeight + 200){
            return false;
        }
        else return true;
    }
}
