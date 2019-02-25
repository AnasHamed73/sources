package com.example.kikuchio.photogallery;

import java.util.List;

public class NewGalleryItemsFetchedEvent {

    private List<GalleryItem> newData;

    public NewGalleryItemsFetchedEvent(List<GalleryItem> newData) {
        this.newData = newData;
    }

    public List<GalleryItem> newData() {
        return newData;
    }
}
