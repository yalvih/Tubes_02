package com.example.tubes_02.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
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

//Main game fragment

public class PianoTilesGameFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    private FragmentListener fragmentListener;
    ImageView imageView;
    Canvas canvas;
    Bitmap mBitmap;
    Paint paint;
    TextView score, high_score; //yang ada angkanya
    Button start;
    boolean isGameStarted; //untuk button kalau dipencet

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
        if(v == start){
            if (!isGameStarted) {
                initiateGame();
                if (v.getId() == R.id.start_game){
                    this.start.setVisibility(View.GONE);
                }
                isGameStarted = true;
            }
        }
    }

    public void initiateGame() {
        // 1. Create Bitmap
        this.mBitmap = Bitmap.createBitmap(imageView.getWidth(),imageView.getHeight(),Bitmap.Config.ARGB_8888);

        // 2. Associate the bitmap to the ImageView.
        this.imageView.setImageBitmap(mBitmap);

        // 3. Create a Canvas with the bitmap.
        this.canvas = new Canvas(this.mBitmap);

        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.purple_200,null);
        canvas.drawColor(mColorBackground);

        //THIS BELOW IS JUST DUMMY, REPLACED WITH THREAD GENERATED ANIMATED RECTANGLE FROM TOP TO BOTTOM
        //IF TOUCHES BELOW, THREAD STOPS, AND CHANGE PAGE TO GAME OVER PAGE

        this.paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(), R.color.black, null) ;
        this.paint.setColor(mColorTest);

        canvas.drawRect(new Rect(-2, 0, 2, canvas.getHeight()), paint);
        canvas.drawRect(new Rect(canvas.getWidth() / 4 - 2, 0, canvas.getWidth() / 4 + 2, canvas.getHeight()), paint);
        canvas.drawRect(new Rect(canvas.getWidth() / 4 * 2 - 2, 0, canvas.getWidth() / 4 * 2 + 2, canvas.getHeight()), paint);
        canvas.drawRect(new Rect(canvas.getWidth() / 4 * 3 - 2, 0, canvas.getWidth() / 4 * 3 + 2, canvas.getHeight()), paint);
        canvas.drawRect(new Rect(canvas.getWidth() - 2, 0, canvas.getWidth() + 2, canvas.getHeight()), paint);

        //Tester tile. Just so we know how the fuck do we draw this shit
        //Should look something like this: drawTile(...);, but what should we put as the coordinate??
        canvas.drawRect(new Rect(canvas.getWidth() / 4 * 0,0,canvas.getWidth() / 4 * 1,400), paint);

        //resetCanvas
        this.imageView.invalidate();
    }

    public void drawTile(Coordinate coordinate){
        int left = (int) coordinate.getX() - canvas.getWidth() / 8;
        int right = (int) coordinate.getX() + canvas.getWidth() / 8;
        int top = (int) coordinate.getY();
        int bottom = top + 400;
        //this.mCanvas.drawColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

        this.canvas.drawRect(new Rect(left, top, right, bottom), this.paint);
        this.imageView.invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private class CustomListener extends GestureDetector.SimpleOnGestureListener{

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

}
//delete this when you receive it