package com.example.kikuchio.photogallery;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotoGalleryFragment extends Fragment implements NewGalleryItemsFetchedEventListener {

    private static final String TAG = "PhotoGalleryFragment";
    private GridView mGridView;
    private List<GalleryItem> mItems;
    private ArrayAdapter<GalleryItem> mAdapter;
    private ThumbnailDownloader<ImageView> mThumbnailDownloader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        GalleryEventObserver.registerNewDataListener(this);
        if (mItems == null) {
            updateItems(1);
        }
        startThumbnailDownloaderThread();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = getActivity().getComponentName();
        SearchableInfo searchInfo = searchManager.getSearchableInfo(componentName);
        searchView.setSearchableInfo(searchInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .edit()
                        .putString(FlickrFetchr.PREF_SEARCH_QUERY, null)
                        .commit();
                updateItems(1);
                return true;
            case R.id.menu_item_toggle_polling:
                boolean alarmStatus = PollService.isServiceAlarmOn(getActivity());
                PollService.setServiceAlarm(getActivity(), !alarmStatus);
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem toggleMenuItem = menu.findItem(R.id.menu_item_toggle_polling);
        boolean alarmIsOn = PollService.isServiceAlarmOn(getActivity());
        toggleMenuItem.setTitle(alarmIsOn ? R.string.stop_polling : R.string.start_polling);
    }

    private void startThumbnailDownloaderThread() {
        mThumbnailDownloader = new ThumbnailDownloader<>(new Handler());
        mThumbnailDownloader.setListener((imageView, thumbnail) -> {
            if (isVisible())
                imageView.setImageBitmap(thumbnail);
        });
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "ThumbnailDownloader thread Destroyed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        setHasOptionsMenu(true);
        linkWidgetsToViews(view);
        setupAdapter();
        return view;
    }

    @Override
    public void onNewDataEvent(NewGalleryItemsFetchedEvent event) {
        if (mItems == null)
            mItems = new ArrayList<>(FlickrFetchr.ITEMS_PER_PAGE);
        mItems.addAll(event.newData());
        int current = mGridView.getFirstVisiblePosition();
        setupAdapter();
        mGridView.setSelection(current);
    }

    private void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;
        if (mItems != null) {
            initAdapter();
            mGridView.setAdapter(mAdapter);
        } else {
            mGridView.setAdapter(null);
        }
    }

    private void initAdapter() {
        if (mAdapter == null)
            mAdapter = new DynamicArrayAdapter();
    }

    private void linkWidgetsToViews(View view) {
        mGridView = view.findViewById(R.id.gridView);
    }

    private class DynamicArrayAdapter extends ArrayAdapter<GalleryItem> {

        DynamicArrayAdapter() {
            super(Objects.requireNonNull(getActivity()), android.R.layout.simple_gallery_item, mItems);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = Objects.requireNonNull(getActivity())
                        .getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
            }
            fetchImageThumbnail(position, convertView);
            if (position == mItems.size() - 1 && mItems.size() % FlickrFetchr.ITEMS_PER_PAGE == 0) {
                updateItems((mItems.size() / FlickrFetchr.ITEMS_PER_PAGE) + 1);
            }
            return convertView;
        }

        private void fetchImageThumbnail(int position, @NonNull View convertView) {
            ImageView img = convertView.findViewById(R.id.gallery_item_imageView);
            img.setImageResource(R.drawable.brian_up_close);
            GalleryItem item = getItem(position);
            mThumbnailDownloader.queueMessage(img, item.getUrl());
        }
    }

    private class FetchItemsTask extends AsyncTask<Integer, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Integer... pageNo) {
            int page = pageNo == null ? 1 : pageNo[0];
            Activity activity = getActivity();
            if (activity == null) {
                return new ArrayList<>();
            }
            String query = PreferenceManager.getDefaultSharedPreferences(activity)
                    .getString(FlickrFetchr.PREF_SEARCH_QUERY, null);
            if(mItems != null)
                mItems.clear();
            if (query != null)
                return new FlickrFetchr().search(query, page);
            else
                return new FlickrFetchr().fetchItems(page);
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            GalleryEventObserver.publishNewDataEvent(new NewGalleryItemsFetchedEvent(items));
        }
    }

    public void updateItems(int page) {
        new FetchItemsTask().execute(page);
    }
}
