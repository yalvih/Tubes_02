package com.example.tubes_02.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.tubes_02.R;
import com.example.tubes_02.presenter.PianoThread;
import com.example.tubes_02.presenter.PianoTilesGamePresenter;
import com.example.tubes_02.presenter.PlayThread;

import java.util.Random;

//Main game fragment

public class PianoTilesGameFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, PianoTilesGamePresenter.IPianoTilesGame {
    private FragmentListener fragmentListener;
    ImageView imageView;
    Canvas canvas;
    Bitmap mBitmap;
    Paint paint, paintClear;
    TextView score, high_score; //yang ada angkanya
    Button start;
    PlayThread playThread;
    boolean isGameStarted; //untuk button kalau dipencet
    UIThreadedWrapper threadWrapper;
    PianoThread thread;
    PianoTilesGamePresenter pianoTilesGamePresenter;

    public static PianoTilesGameFragment newInstance(String title) {
        PianoTilesGameFragment fragment = new PianoTilesGameFragment();
        Bundle args = new Bundle();
        args.putString("main_game", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piano_tiles_game_fragment, container, false);

        this.start = view.findViewById(R.id.start_game);
        this.imageView = view.findViewById(R.id.iv_canvas);
        this.score = view.findViewById(R.id.score_number);
        this.high_score = view.findViewById(R.id.hi_score_number);

        this.pianoTilesGamePresenter = new PianoTilesGamePresenter(imageView,this);
//        this.threadWrapper = new UIThreadedWrapper(pianoTilesGamePresenter);

        this.imageView.setOnTouchListener(this);
        this.start.setOnClickListener(this);
        this.start.setVisibility(View.VISIBLE);

        this.isGameStarted = false;

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.fragmentListener = (FragmentListener)context;
        }
        else {
            throw new ClassCastException(context.toString() + " must implement FragmentListener!");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == start) {
            if (!isGameStarted) {
                this.pianoTilesGamePresenter.initiateGame();
                if (v.getId() == R.id.start_game){
                    this.start.setVisibility(View.GONE);
                }
                isGameStarted = true;
            }

//            this.threadList.addFirst(new CustomThread(this.handler, coordinateDir, coordinateMax, coordinateStart));
//            this.threadList.getFirst().start();
//            Random rnd = new Random();
//            int random = rnd.nextInt(4);
//            this.thread = new PianoThread(threadWrapper, this.imageView.getWidth(), this.imageView.getHeight(), random);
//            this.thread.start();

//            while(isGameStarted) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        switch (e.getAction() & e.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.d("InputCheck", "Input works!");
                if (isGameStarted) {
                    Coordinate tap = new Coordinate(e.getX(), e.getY());
//                    this.thread.checkInput(tap);
                    this.pianoTilesGamePresenter.clicked(tap);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("TouchListener", "Pointer down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TouchListener", "Up");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("TouchListener", "Pointer up");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TouchListener", "Move");
                break;
        }
        return true;
    }

//    public void generateTiles(){
//        this.pianoTilesGamePresenter.generateTiles();
//    }
//
//    public void clearList(){
//        this.pianoTilesGamePresenter.clearList();
//    }
//
//    public void gameOver() {
//        this.pianoTilesGamePresenter.gameOver(this.playThread);
//        this.fragmentListener.changePage(3);
//    }

    @Override
    public void createCanvas(Canvas canvas, Bitmap mBitmap) {
        this.canvas = canvas;
        this.mBitmap = mBitmap;
        this.imageView.setImageBitmap(mBitmap);
    }

    @Override
    public void drawTile(Canvas canvas) {
        this.canvas = canvas;
        this.imageView.invalidate();
    }

    @Override
    public void clearTile(Canvas canvas) {
        this.canvas = canvas;
        this.imageView.invalidate();
    }

    @Override
    public void addScore(int score) {
        this.score.setText(Integer.toString(score));
    }

    @Override
    public void changePage(int page) {
        this.fragmentListener.changePage(page);
    }

    /*
    private class CustomListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

    }
     */

    //    public void initiateGame() {
//        this.mBitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
//        this.imageView.setImageBitmap(mBitmap);
//        this.canvas = new Canvas(this.mBitmap);
//
//        //Colors
//        this.paint = new Paint();
//        this.paintClear = new Paint();
//        int colorBlack = ResourcesCompat.getColor(getResources(), R.color.white, null);
//        int colorClear = ResourcesCompat.getColor(getResources(), R.color.black, null);
//        this.paint.setColor(colorBlack);
//        this.paintClear.setColor(colorClear);
//
////        Background and column lines
//        canvas.drawColor(colorClear);
//        for (int i = 0; i <= 4; i++) {
//            canvas.drawRect(new Rect(canvas.getWidth() / 4 * i - 2, 0, canvas.getWidth() / 4 * i+ 2, canvas.getHeight()), paint);
//        }
//
//        //Tester tile. Just so we know how the fuck do we draw this shit
//        //Should look something like this: drawTile(...);, but what should we put as the coordinate??
////        canvas.drawRect(new Rect(canvas.getWidth() / 4 * 0,0,canvas.getWidth() / 4 * 1,400), paint);
//
//        this.imageView.invalidate();
//    }






//        public void drawTile(Coordinate coordinate) {
////        x and y offset
//        float modifierX = canvas.getWidth() / 8 - 2;
//        float modifierY = 200;
//
//        this.canvas.drawRect(coordinate.getX() - modifierX, coordinate.getY() - modifierY, coordinate.getX() + modifierX, coordinate.getY() + modifierY, this.paint);
//        this.imageView.invalidate();
//        }




    //    public void clearTile(Coordinate coordinate) {
////        x and y offset
//        float modifierX = canvas.getWidth() / 8 - 2;
//        float modifierY = 200;
//
//        this.canvas.drawRect(coordinate.getX() - modifierX, coordinate.getY() - modifierY, coordinate.getX() + modifierX, coordinate.getY() + modifierY, this.paintClear);
//        this.imageView.invalidate();
//    }




    //    public void addScore() {
//        int currentScore = Integer.parseInt(this.score.getText().toString());
//        currentScore++;
//        this.score.setText(Integer.toString(currentScore));
//    }
}