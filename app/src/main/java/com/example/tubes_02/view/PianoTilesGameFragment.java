package com.example.tubes_02.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tubes_02.DBHandler;
import com.example.tubes_02.R;
import com.example.tubes_02.presenter.PianoThread;
import com.example.tubes_02.presenter.PianoTilesGamePresenter;
import com.example.tubes_02.presenter.PlayThread;

//Main game fragment

public class PianoTilesGameFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, PianoTilesGamePresenter.IPianoTilesGame, SensorEventListener {
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
    DBHandler dbHandler;
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    private float[] accelerometerData;
    private float[] magnetometerData;
    private final float VALUE_DRIFT = 0.5f;

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
        MainActivity.gameOver = false;

        this.start = view.findViewById(R.id.start_game);
        this.imageView = view.findViewById(R.id.iv_canvas);
        this.score = view.findViewById(R.id.score_number);
        this.high_score = view.findViewById(R.id.hi_score_number);

        this.dbHandler = new DBHandler(this.getActivity());
        this.pianoTilesGamePresenter = new PianoTilesGamePresenter(this, imageView, dbHandler);
        this.high_score.setText(this.pianoTilesGamePresenter.getHighestScore());
        this.mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        this.mSensorAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.mSensorMagnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

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
    public void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
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
                if (isGameStarted) {
                    Coordinate tap = new Coordinate(e.getX(), e.getY());
                    this.pianoTilesGamePresenter.clicked(tap);
                }
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
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                this.accelerometerData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetometerData = event.values.clone();
                Log.d("SensorDetect", "test");
                break;
        }

        final float[] rotationMatrix = new float[9];
        final float[] orientationAngles = new float[3];
        if (accelerometerData != null && magnetometerData != null) {
            mSensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData);
            mSensorManager.getOrientation(rotationMatrix, orientationAngles);
        }

        float roll = orientationAngles[2];
        if (Math.abs(roll) < VALUE_DRIFT) {
            roll = 0;
        }
        else {
            if (roll > 0) {
                roll = 1;
            }
            else roll = -1;
        }

        this.pianoTilesGamePresenter.checkTiltPrompt(roll);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

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
    public void showToast(int toastCode) {
        String text = "";
        if (toastCode == 0) {
            text = "Tilt left to gain extra points!";
        }
        else if (toastCode == 1) {
            text = "Tilt right to gain extra points!";
        }
        Toast prompt = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
        prompt.setGravity(Gravity.CENTER, 0, -800);
        prompt.show();
        if (toastCode == 2) {
            prompt.cancel();
            Toast toast = Toast.makeText(getActivity(), "+5 points!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -800);
            toast.show();
        }
    }

    @Override
    public void clearTile(Canvas canvas) {
        this.canvas = canvas;
        this.imageView.invalidate();
    }

    @Override
    public void addScore(int score) {
        int currentScore = score;
        this.score.setText(Integer.toString(currentScore));

        if (currentScore > Integer.parseInt(this.high_score.getText().toString())) {
            this.high_score.setText(Integer.toString(currentScore));
        }
    }

    @Override
    public void changePage(int page) {
        this.fragmentListener.changePage(page);
    }

    @Override
    public int checkHighScore() {
        int highscore = Integer.parseInt(this.pianoTilesGamePresenter.getHighScoreLimit());
        int score = Integer.parseInt(this.score.getText().toString());

        if (score > highscore) {
            return score;
        }
        else return -1;
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