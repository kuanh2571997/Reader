package com.example.tablayout.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tablayout.Model.DataVideo;
import com.example.tablayout.Model.VideoAdapter2;
import com.example.tablayout.R;
import com.example.tablayout.Presenter.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.r0adkll.slidr.Slidr;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class PlayVideo extends AppCompatActivity {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private String url;
    private int mResumeWindow;
    private long mResumePosition;
    private RecyclerView recyclerView;
    private ArrayList<DataVideo> arr;
    private long time = 0;
    private ProgressBar progressBarVolume, progressBarVolume2;
    private static final String TAG = "trangthai";
    private float startX, startY, moveX, moveY;
    private int volumePG = 100, position;
    private FloatingActionButton back;
    private ImageView imgVolume;
    private Display window;
    private boolean width = false, heigh = false;
    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton floatingActionButton1, floatingActionButton2;
    private ImageButton btnPre, btnNext;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        Slidr.attach(this);

        progressBarVolume = findViewById(R.id.progressBarVolume);
        actionBar = getSupportActionBar();
        actionBar.hide();
        imgVolume = findViewById(R.id.imgVolume);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
//        back = findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        back.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                finish();
//                Intent intent1 = new Intent(PlayVideo.this, MainActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent1);
//                return false;
//            }
//        });
        progressBarVolume.setProgress(volumePG);
        //volumePG = progressBarVolume.getProgress();
//        Log.d(TAG, "onCreate: "+volumePG);

        Intent intent = getIntent();
        url = intent.getStringExtra("link");
        position = intent.getIntExtra("position",0);

        Bundle bundle = intent.getBundleExtra("bundle");
        arr = (ArrayList<DataVideo>) bundle.getSerializable("array");
        recyclerView = findViewById(R.id.recyclerView);
        VideoAdapter2 videoAdapter = new VideoAdapter2(this, arr);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PlayVideo.class);
                intent.putExtra("link", arr.get(position).getUrlVideo());
                Bundle bundle = new Bundle();
                bundle.putSerializable("array", arr);
                intent.putExtra("bundle", bundle);
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d(TAG, "onScrolled: "+dx+"----"+dy);
                if (dy > 0) {
                    materialDesignFAM.setVisibility(View.GONE);
                    //Log.d(TAG, "onScrolled: up");
                } else {
                    materialDesignFAM.setVisibility(View.VISIBLE);
                    //Log.d(TAG, "onScrolled: donw");
                }

            }
        });

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayVideo.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
        progressBarVolume.setVisibility(View.INVISIBLE);
        imgVolume.setVisibility(View.INVISIBLE);
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayVideo.this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen) {
                    volumePG = progressBarVolume.getProgress();
//                    Log.d(TAG, "onClick: "+volumePG);
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    openFullscreenDialog();
                } else {
                    volumePG = progressBarVolume.getProgress();
//                    Log.d(TAG, "onClick: "+volumePG);
                    closeFullscreenDialog();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        btnPre = controlView.findViewById(R.id.btn_pre);
        if(position==0){
            btnPre.setVisibility(View.GONE);
        }
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position ==0){
                    Toast.makeText(PlayVideo.this, "don't pre", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), PlayVideo.class);
                    intent.putExtra("link", arr.get(position-1).getUrlVideo());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", arr);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("position", position-1);
                    startActivity(intent);
                }
            }
        });

        btnNext = controlView.findViewById(R.id.btn_next);
        if(position == 8){
            btnNext.setVisibility(View.GONE);
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 8){
                    Toast.makeText(PlayVideo.this, "don't next", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), PlayVideo.class);
                    intent.putExtra("link", arr.get(position+1).getUrlVideo());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", arr);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("position", position+1);
                    startActivity(intent);
                }
            }
        });

    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

        mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);
    }


    @Override
    protected void onResume() {

        super.onResume();

        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();

            String streamUrl = url;

            String userAgent = Util.getUserAgent(this, getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, null, httpDataSourceFactory);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri daUri = Uri.parse(streamUrl);


//            String userAgent = Util.getUserAgent(PlayVideo.this, getApplicationContext().getApplicationInfo().packageName);
//            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(PlayVideo.this, null, httpDataSourceFactory);
//            Uri daUri = Uri.parse(streamUrl);

            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory, null, null);
        }

        initExoPlayer();
        window = getWindowManager().getDefaultDisplay();
        window.getWidth();
        mExoPlayerView.showController();
        float volume = (float) progressBarVolume.getProgress() / 100;
//        Log.d(TAG, "onTouch: " + volume);
//        mExoPlayerView.getPlayer().setVolume(volume);
        mExoPlayerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("trangthai", "onTouch: " + event.toString());
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getY() - startY < 10 && event.getY() - startY > -10) {
                        mExoPlayerView.showController();
                    }
                    progressBarVolume.setVisibility(View.GONE);
                    imgVolume.setVisibility(View.GONE);
                    volumePG = (int) (mExoPlayerView.getPlayer().getVolume() * 100);
                    //mExoPlayerView.showController();
                    long ab = event.getDownTime() - time;
                    if (event.getDownTime() - time < 500) {
                        if (!mExoPlayerFullscreen) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            openFullscreenDialog();
                        } else {
                            closeFullscreenDialog();
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                    }
                    time = event.getDownTime();
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = event.getX();
                    startY = event.getY();
                    moveX = event.getX();
                    moveY = event.getY();
                    //Log.d(TAG, "onTouch: "+mExoPlayerView.getPlayer());
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (startX < window.getWidth() / 2) {
//                    Log.d(TAG, "onTouch: subX"+(event.getX()-startX));
//                    Log.d(TAG, "onTouch: subY"+(event.getY()-startY));
//                    Log.d(TAG, "onTouch-subX: "+(event.getX()-moveX));
                        moveX = event.getX();
                        moveY = event.getY();
                        progressBarVolume.setVisibility(View.VISIBLE);
                        imgVolume.setVisibility(View.VISIBLE);
                        int progress = (int) (mExoPlayerView.getPlayer().getVolume() * 100);
                        //Log.d(TAG, "onTouch-progress: "+progress);
                        progressBarVolume.setProgress(progress);
                        int sum = (int) (event.getY() - startY) / 2;
                        //Log.d(TAG, "onTouch: "+sum);
                        progressBarVolume.setProgress(volumePG - sum);
                        float volume = (float) progressBarVolume.getProgress() / 100;
                        mExoPlayerView.getPlayer().setVolume(volume);
                        if (progressBarVolume.getProgress() == 0) {
                            imgVolume.setImageResource(R.drawable.mute);
                        } else {
                            imgVolume.setImageResource(R.drawable.volume);
                        }
                    }

                }
                return true;
            }
        });

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayVideo.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }
}