package com.example.kikuchio.photogallery;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

public class PollService extends IntentService {

    private static final int POLL_INTERVAL = 1000 * 15;
    private static final String TAG = "PollService";

    public PollService() {
        super(TAG);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(isOn) {
            alarmMgr.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmMgr.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() == null) return;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString(FlickrFetchr.PREF_SEARCH_QUERY, null);
        String lastResultId = prefs.getString(FlickrFetchr.PREF_LAST_RESULT_ID, null);

        List<GalleryItem> items = query == null
                ? new FlickrFetchr().fetchItems(1)
                : new FlickrFetchr().search(query, 1);
        if(items.size() == 0) return;
        String resultId = items.get(0).getId();
        if(!resultId.equals(lastResultId)) {
            Log.i(TAG, "Got a new result: " + resultId);
            displayNotification();
        } else {
            Log.i(TAG, "Got an old result: " + resultId);
        }
        prefs.edit()
                .putString(FlickrFetchr.PREF_LAST_RESULT_ID, resultId)
                .commit();
        Log.i(TAG, "Received an Intent " + intent);
    }

    private void displayNotification() {
        Resources res = getResources();
        Intent photoGalleryIntent = new Intent(this, PhotoGalleryActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, photoGalleryIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, null)
                .setTicker(res.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(res.getString(R.string.new_pictures_title))
                .setContentText(res.getString(R.string.new_pictures_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


}
