package com.example.tubes_02.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.model.Player;
import com.example.tubes_02.view.Coordinate;
import com.example.tubes_02.view.MainActivity;
import com.example.tubes_02.view.UIThreadedWrapper;

import java.util.LinkedList;
import java.util.Random;

public class PianoTilesGamePresenter {
    public LinkedList<PianoThread> listTiles;
    public Random rnd;
    DBHandler db;
    UIThreadedWrapper threadWrapper;
    ImageView imageView;
    Canvas canvas;
    Bitmap mBitmap;
    Paint paint, paintClear;
    Button start;
    PlayThread playThread;
    boolean isGameStarted; //untuk button kalau dipencet
    IPianoTilesGame iPianoTilesGame;
    int currentScore = 0;
    int tiltCode = 99;

    public PianoTilesGamePresenter(IPianoTilesGame iPianoTilesGame, ImageView imageView, DBHandler db) {
        this.listTiles = new LinkedList<>();
        this.rnd = new Random();
        this.threadWrapper = new UIThreadedWrapper(this);
        this.imageView = imageView;
        this.db = db;
        this.iPianoTilesGame = iPianoTilesGame;
    }

    public interface IPianoTilesGame{
        void createCanvas(Canvas canvas, Bitmap mBitmap);
        void drawTile(Canvas canvas);
        void showToast(int code);
        void clearTile(Canvas canvas);
        void addScore(int score);
        int checkHighScore();
        void changePage(int page);
    }

    public String getHighestScore() {
        return this.db.getLBHighestScore();
    }

    public String getHighScoreLimit() {
        return this.db.getLBLowestScore();
    }

    public void generateTiles(){
        int random = rnd.nextInt(4);
        PianoThread pt = new PianoThread(threadWrapper, this.imageView.getWidth(), this.imageView.getHeight(), random);
        this.listTiles.addFirst(pt);
        pt.start();
    }

    public void generateTiltPrompt(){
        int random = rnd.nextInt(2);
        this.iPianoTilesGame.showToast(random);
        this.tiltCode = random;
    }

    public void checkTiltPrompt(float rollValue){
        if (this.tiltCode == 0) {
            if (rollValue == -1) {
                this.addScoreTilt();
                this.iPianoTilesGame.showToast(2);
                this.tiltCode = 99;
            }
        }
        else if (this.tiltCode == 1) {
            if (rollValue == 1) {
                this.addScoreTilt();
                this.iPianoTilesGame.showToast(2);
                this.tiltCode = 99;
            }
        }
    }

    public void addScore() {
        currentScore++;
        this.iPianoTilesGame.addScore(currentScore);
    }

    public void addScoreTilt() {
        currentScore += 5;
        this.iPianoTilesGame.addScore(currentScore);
    }

    public void clicked(Coordinate c){
        for (int i = 0; i < listTiles.size(); i++) {
            this.listTiles.get(i).checkInput(c);
        }
    }

    public void clearList(){
        this.listTiles.removeLast();
    }

    public void gameOver(){
        if (!MainActivity.gameOver) {
            MainActivity.gameOver = true;
            Log.d("GameOverTest", "Appear only once pls");
            for (int i = 0; i < listTiles.size(); i++) {
                this.listTiles.get(i).isClicked = true;
                this.listTiles.get(i).gameOver = true;
            }
            this.playThread.stopThread();

            int score = iPianoTilesGame.checkHighScore();
            if (score != -1) {
                this.db.addRecord(new Player(0, "Player", Integer.toString(score)));
                this.iPianoTilesGame.changePage(6);
            } else this.iPianoTilesGame.changePage(3);
        }
    }

    public void initiateGame() {
        this.mBitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.mBitmap);
        this.iPianoTilesGame.createCanvas(this.canvas,this.mBitmap);

        //Colors
        this.paint = new Paint();
        this.paintClear = new Paint();

        this.paint.setColor(Color.WHITE);
        this.paintClear.setColor(Color.BLACK);

        canvas.drawColor(Color.BLACK);
        for (int i = 0; i <= 4; i++) {
            canvas.drawRect(new Rect(canvas.getWidth() / 4 * i - 2, 0, canvas.getWidth() / 4 * i+ 2, canvas.getHeight()), paint);
        }

        this.playThread = new PlayThread(threadWrapper);
        playThread.start();

        this.imageView.invalidate();
    }

    public void drawTile(Coordinate coordinate){
        float modifierX = canvas.getWidth() / 8 - 2;
        float modifierY = 200;

        this.canvas.drawRect(coordinate.getX() - modifierX, coordinate.getY() - modifierY, coordinate.getX() + modifierX, coordinate.getY() + modifierY, this.paint);
        this.iPianoTilesGame.drawTile(this.canvas);
    }

    public void clearTile(Coordinate coordinate) {
        float modifierX = canvas.getWidth() / 8 - 2;
        float modifierY = 202;

        this.canvas.drawRect(coordinate.getX() - modifierX, coordinate.getY() - modifierY, coordinate.getX() + modifierX, coordinate.getY() + modifierY, this.paintClear);
        this.iPianoTilesGame.clearTile(this.canvas);
    }
}
