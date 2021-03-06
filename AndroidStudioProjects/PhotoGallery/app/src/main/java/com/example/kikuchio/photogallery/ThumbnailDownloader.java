package com.example.kikuchio.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailDownloader<Token> extends HandlerThread {

    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler mHandler;
    private Handler mResponseHandler;
    private Listener<Token> mLisenter;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD) {
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for URl " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    public void setListener(Listener<Token> listener) {
        mLisenter = listener;
    }

    public void queueMessage(Token token, String url) {
        Log.i(TAG, "Got a URL: " + url);
        requestMap.put(token, url);
        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    private void handleRequest(final Token token) {
        try {
            final String url = requestMap.get(token);
            if(url == null) return;
            final Bitmap bitmap = getBitmap(url);
            Log.i(TAG, "Bitmap created");
            mResponseHandler.post(() -> {
                if (requestMap.get(token) != url) return;
                requestMap.remove(token);
                mLisenter.onThumbnailDownloaded(token, bitmap);
            });
        } catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
    }

    private Bitmap getBitmap(String url) throws IOException {
        byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
        return bitmapBytes == null ? null : BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }
}
