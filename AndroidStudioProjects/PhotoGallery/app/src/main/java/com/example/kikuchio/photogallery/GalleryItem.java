package com.example.kikuchio.photogallery;

import android.support.annotation.NonNull;

public class GalleryItem {
    private String mId;
    private String mUrl;
    private String mCaption;

    @NonNull
    @Override
    public String toString() {
        return mCaption;
    }

    public String getId() {
        return mId;
    }

    public GalleryItem setId(String mId) {
        this.mId = mId;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public GalleryItem setUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public String getCaption() {
        return mCaption;
    }

    public GalleryItem setCaption(String mCaption) {
        this.mCaption = mCaption;
        return this;
    }
}
