package com.example.tablayout.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.tablayout.R;


public class VideoAT extends AppCompatActivity{
    private int swipe_Min_Distance = 50;
    private int swipe_Max_Distance = 500;
    private int swipe_Min_Velocity = 100;
    int clickCount = 0;
    //variable for storing the time of first click
    long startTime;
    //variable for calculating the total time
    long duration;
    //constant for defining the time duration between the click that can be considered as double-tap
    static final int MAX_DURATION = 200;
    private static final String TAG = "VideoAT";
    private GestureDetectorCompat mDetector;
    private int dem=1;
    private int progressSeekBar=0;
    private float x3 = 20000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_at);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String url = intent.getStringExtra("link");
        VideoView videoView = findViewById(R.id.video_view);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setVisibility(View.GONE);
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.start();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener(videoView,seekBar));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d(TAG, "onTouchEvent: "+event.toString());
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";
        private VideoView videoView;
        private SeekBar seekBar;

        public MyGestureListener(VideoView videoView, final SeekBar seekBar) {
            this.videoView = videoView;
            this.seekBar = seekBar;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            seekBar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            //Log.d(TAG, "onScroll: " + e1.toString()+e2.toString());
            Log.d(TAG, "onScroll: e1 = "+ e1.getX() + "e2 = "+e2.getX());
            float x1 = e1.getX();
            float x2 = e2.getX();
            int num = (int) (x2-x1)/80;
            Log.d(TAG, "onScroll: num= "+num);
            seekBar.setProgress(progressSeekBar+num);
            progressSeekBar = seekBar.getProgress();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            seekBar.setVisibility(View.INVISIBLE);
            return true;

        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            finish();
            return true;
        }

        //        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2,
//                               float velocityX, float velocityY) {
////            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
////            return true;
//
//            final float xDistance = Math.abs(event1.getX() - event2.getX());
//            final float yDistance = Math.abs(event1.getY() - event2.getY());
//            Log.d(DEBUG_TAG, "onFling: "+xDistance);
//
//            if (xDistance > swipe_Max_Distance
//                    || yDistance > swipe_Max_Distance)
//                return false;
//
//            velocityX = Math.abs(velocityX);
//            velocityY = Math.abs(velocityY);
//            boolean result = false;
//
//            if (velocityX > swipe_Min_Velocity
//                    && xDistance > swipe_Min_Distance) {
//                if (event1.getX() > event2.getX()) // right to left
//                    //this.listener.onSwipe(SWIPE_RIGHT);
//                    Toast.makeText(VideoAT.this, "right to left", Toast.LENGTH_SHORT).show();
//                else
//                    //this.listener.onSwipe(SWIPE_LEFT);
//                    Toast.makeText(VideoAT.this, "left to right", Toast.LENGTH_SHORT).show();
//
//                result = true;
//            } else if (velocityY > swipe_Min_Velocity
//                    && yDistance > swipe_Min_Distance) {
//                if (event1.getY() > event2.getY()) // bottom to up
//                    //this.listener.onSwipe(SWIPE_UP);
//                    Toast.makeText(VideoAT.this, "bottom to up", Toast.LENGTH_SHORT).show();
//                else
//                    //this.listener.onSwipe(SWIPE_DOWN);
//                    Toast.makeText(VideoAT.this, "up down bottom", Toast.LENGTH_SHORT).show();
//
//                result = true;
//            }
//
//            return result;
//
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        this.mDetector.onTouchEvent(event);
//        // Be sure to call the superclass implementation
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean onDown(MotionEvent event) {
//        Log.d(TAG,"onDown: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onFling(MotionEvent event1, MotionEvent event2,
//                           float velocityX, float velocityY) {
//        Log.d(TAG, "onFling: " + event1.toString()+event2.toString());
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent event) {
//        Log.d(TAG, "onLongPress: " + event.toString());
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//                            float distanceY) {
//        //Log.d(TAG, "onScroll: " + e1.toString()+e2.toString());
//        Log.d(TAG, "onScroll: e1 = "+ e1.getX() + "e2 = "+e2.getX());
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent event) {
//        Log.d(TAG, "onShowPress: " + event.toString());
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent event) {
//        Log.d(TAG, "onSingleTapUp: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTap(MotionEvent event) {
//        Log.d(TAG, "onDoubleTap: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTapEvent(MotionEvent event) {
//        Log.d(TAG, "onDoubleTapEvent: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent event) {
//        Log.d(TAG, "onSingleTapConfirmed: " + event.toString());
//        return true;
//    }

}
