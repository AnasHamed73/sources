package com.example.kikuchio.nerdlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class NerdLauncherFragment extends ListFragment {

    private static final String TAG = "NerdLauncherFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = getSortedActivitiesForIntent(startupIntent, packageManager);
        setListAdapter(activitiesArrayAdapter(activities));
        Log.i(TAG, "I've found " + activities.size() + " activities");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo activity = (ResolveInfo) l.getAdapter().getItem(position);
        ActivityInfo activityInfo = activity.activityInfo;
        if (activityInfo == null) return;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        startActivity(intent);
    }

    private ListAdapter activitiesArrayAdapter(List<ResolveInfo> activities) {
        return new ResolveInfoArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, activities);
    }

    private class ResolveInfoArrayAdapter extends ArrayAdapter<ResolveInfo> {

        public ResolveInfoArrayAdapter(@NonNull Context context, int resource,
                                       @NonNull List<ResolveInfo> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            PackageManager packageManager = getActivity().getPackageManager();
            ResolveInfo activity = getItem(position);
            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view;
            textView.setText(activity.loadLabel(packageManager));
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(activity.loadIcon(packageManager), null, null, null);
            return view;
        }
    }

    private List<ResolveInfo> getSortedActivitiesForIntent(Intent startupIntent, PackageManager packageManager) {
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent, 0);
        Collections.sort(activities, (a, b) ->
                String.CASE_INSENSITIVE_ORDER
                        .compare(a.loadLabel(packageManager).toString()
                                , b.loadLabel(packageManager).toString()));
        return activities;
    }
}
