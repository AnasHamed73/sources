package com.example.kikuchio.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CrimeListFragment extends ListFragment {
    public static final String TAG = "CrimeListFragment";
    public static final String EXTRA_CRIME_ID = "com.example.kikuchio.criminalintent.crimeId";
    private boolean mSubtitleShown;
    private List<Crime> mCrimes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        setListAdapter(new CrimeAdapter(mCrimes));
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mSubtitleShown = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = view.findViewById(android.R.id.list);
//        registerForContextMenu(listView);     //floating context menu
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new MultiChoiceModeListener());
        if (mSubtitleShown) {
            getActionBar().setSubtitle(R.string.subtitle);
        }
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = (Crime) (getListAdapter().getItem(position));
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleShown && subtitle != null) {
            subtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                startNewCrimeActivity();
                return true;
            case R.id.menu_item_show_subtitle:
                toggleSubtitle(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
        Crime crime = adapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).delete(crime);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void toggleSubtitle(MenuItem item) {
        if (getActionBar().getSubtitle() == null) {
            showSubtitle(item);
        } else {
            hideSubtitle(item);
        }
    }

    private void showSubtitle(MenuItem item) {
        item.setTitle(R.string.hide_subtitle);
        getActionBar().setSubtitle(R.string.subtitle);
        mSubtitleShown = true;
    }

    private void hideSubtitle(MenuItem item) {
        item.setTitle(R.string.show_subtitle);
        getActionBar().setSubtitle(null);
        mSubtitleShown = false;
    }

    private void startNewCrimeActivity() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).add(crime);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(intent, 0);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        CrimeAdapter(List<Crime> crimes) {
            super(Objects.requireNonNull(getActivity()), 0, crimes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = initView(convertView);
            Crime crime = getItem(position);
            setCrimeTitle(convertView, Objects.requireNonNull(crime).getTitle());
            setCrimeDate(convertView, crime.getDate());
            setCrimeSolved(convertView, crime.isSolved());
            return convertView;
        }

        private View initView(@Nullable View convertView) {
            return convertView == null
                    ? getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null)
                    : convertView;
        }

        private void setCrimeSolved(@NonNull View convertView, boolean isSolved) {
            CheckBox solvedCheckBox = convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(isSolved);
        }

        private void setCrimeDate(@NonNull View convertView, Date crimeDate) {
            TextView dateTextView = convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(crimeDate.toString());
        }

        private void setCrimeTitle(@NonNull View convertView, String crimeTitle) {
            TextView titleTextView = convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crimeTitle);
        }
    }

    private class MultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.crime_list_item_context, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_delete_crime:
                    CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
                    CrimeLab crimeLab = CrimeLab.get(getActivity());
                    for (int i = adapter.getCount(); i >= 0 ; i--) {
                        if (getListView().isItemChecked(i)) {
                            crimeLab.delete(adapter.getItem(i));
                        }
                    }
                    mode.finish();
                    adapter.notifyDataSetChanged();
                    return true;
                default:
                    return false;

            }
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}

