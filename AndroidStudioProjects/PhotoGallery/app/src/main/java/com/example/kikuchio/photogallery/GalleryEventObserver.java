package com.example.kikuchio.photogallery;

import java.util.ArrayList;
import java.util.List;

public class GalleryEventObserver {

    private static List<NewGalleryItemsFetchedEventListener> newDataListeners = new ArrayList<>();

    private GalleryEventObserver() {}

    public static void registerNewDataListener(NewGalleryItemsFetchedEventListener listener){
        newDataListeners.add(listener);
    }

    public static void publishNewDataEvent(NewGalleryItemsFetchedEvent event) {
        for(NewGalleryItemsFetchedEventListener l : newDataListeners)
            l.onNewDataEvent(event);
    }

}
