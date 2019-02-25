package com.example.kikuchio.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class AudioPlayer {

    private MediaPlayer mPlayer;

    public void play(Context c) {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
        }
        mPlayer.setOnCompletionListener((mp) -> stop());
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
