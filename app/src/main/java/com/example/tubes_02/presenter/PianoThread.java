package com.example.tubes_02.presenter;

import android.util.Log;

import com.example.tubes_02.view.Coordinate;
import com.example.tubes_02.view.MainActivity;
import com.example.tubes_02.view.UIThreadedWrapper;

public class PianoThread extends Thread {
    protected UIThreadedWrapper uiThreadedWrapper;
    protected int column;
//  Easy  : 1.0f
//  Normal:
//  Hard  : 4.0f;
    protected float YIncrement = 4.0f;
    protected float canvasWidth;
    protected float canvasHeight;
    protected Coordinate currentPosition;
    protected boolean stopped = false;
    protected boolean isClicked = false;
    protected boolean gameOver = false;

    public PianoThread(UIThreadedWrapper uiThreadedWrapper, float canvasWidth, float canvasHeight, int column) {
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.column = column;
    }

    public void stopThread() {
        this.stopped = true;
    }

    public void stopGameOver() {
        this.stopped = true;
        this.gameOver = true;
    }

    public void run() {
        this.currentPosition = new Coordinate(canvasWidth * (column * 2 + 1) / 8, -200);

        while (checkValid(this.currentPosition.getY()) && !this.stopped) {
            try {
                Thread.sleep(2);
                uiThreadedWrapper.clearTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
                this.currentPosition.setY(this.currentPosition.getY() + this.YIncrement);
                uiThreadedWrapper.drawTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!this.gameOver && !MainActivity.gameOver) {
            if (!this.isClicked) {
                uiThreadedWrapper.gameOver();
            }
        }

        uiThreadedWrapper.clearTile(new Coordinate(this.currentPosition.getX(), this.currentPosition.getY()));
        this.uiThreadedWrapper.clearList();

        return;
    }

    public void checkInput(Coordinate input) {
        if (!isClicked && !this.stopped) {
            if (input.getX() >= this.currentPosition.getX() - canvasWidth / 8 - 2 &&
                    input.getX() <= this.currentPosition.getX() + canvasWidth / 8 + 2 &&
                    input.getY() >= this.currentPosition.getY() - 200 &&
                    input.getY() <= this.currentPosition.getY() + 200) {
                this.isClicked = true;
                this.uiThreadedWrapper.addScore();
                this.stopThread();
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
