package com.example.kikuchio.hellomoon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
    private static final String PLAYER_EXTRA = "com.example.kikuchio.audioPlayer";
    private AudioPlayer mPlayer = new AudioPlayer();;
    private Button mPlayButton;
    private Button mStopButton;
    private Button mPauseButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);
        linkWidgetsToViews(view);
        setWidgetListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void setWidgetListeners() {
        mPlayButton.setOnClickListener(v -> mPlayer.play(getActivity()));
        mPauseButton.setOnClickListener(v -> mPlayer.pause());
        mStopButton.setOnClickListener(v -> mPlayer.stop());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.pause();
    }

    private void linkWidgetsToViews(View view) {
        mPlayButton = view.findViewById(R.id.hello_moon_playButton);
        mPauseButton = view.findViewById(R.id.hello_moon_pauseButton);
        mStopButton = view.findViewById(R.id.hello_moon_stopButton);
    }
}
