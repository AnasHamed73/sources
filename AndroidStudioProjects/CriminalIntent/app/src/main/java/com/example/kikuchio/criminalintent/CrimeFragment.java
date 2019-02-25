package com.example.kikuchio.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    public static final String EXTRA_CRIME_ID = "com.example.kikuchio.criminalintent.crime_id";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";

    private Crime mCrime;

    private EditText mTitleEditText;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private Button mReportCrimeButton;
    private Button mSuspectButton;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = retrieveSelectedCrime();
        if (hasParentActivity()) {
            setHasOptionsMenu(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        linkWidgetsToViews(view);
        updateCrimeWidgets();
        initWidgetListeners();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            setCrimeDate(getDateExtra(data));
            showToast(R.string.date_updated_toast);
        } else if (requestCode == REQUEST_TIME) {
            setCrimeDate(getDateExtra(data));
            showToast(R.string.time_updated_toast);
        }
        else if(requestCode == REQUEST_CONTACT) {
            linkChosenContactName(data.getData());
        }
    }

    private void linkChosenContactName(Uri contactUri) {
        String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        try(Cursor cursor = getActivity().getContentResolver()
                .query(contactUri, queryFields, null, null, null)) {
            if(cursor.getCount() == 0) return;
            cursor.moveToFirst();
            String susName = cursor.getString(0);
            mCrime.setSuspect(susName);
            updateSuspectWidget(susName);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (hasParentActivity()) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).delete(mCrime);
                if (hasParentActivity()) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    private boolean hasParentActivity() {
        return NavUtils.getParentActivityName(getActivity()) != null;
    }

    private void showToast(int toastTextId) {
        Toast.makeText(getActivity(), toastTextId, Toast.LENGTH_SHORT).show();
    }

    private Date getDateExtra(Intent data) {
        return (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
    }

    private void setCrimeDate(Date date) {
        mCrime.setDate(date);
        updateCrimeDateWidget(date);
    }

    private Crime retrieveSelectedCrime() {
        UUID crimeId = (UUID) Objects.requireNonNull(getArguments()).getSerializable(EXTRA_CRIME_ID);
        return CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    private String getCrimeReport() {
        String solvedString = mCrime.isSolved() ? getString(R.string.crime_report_solved)
                : getString(R.string.crime_report_unsolved);
        String dateString = DateFormat.getDateInstance()
                .format(mCrime.getDate());
        String suspect = getString(mCrime.getSuspect() != null ? R.string.crime_report_suspect
                : R.string.crime_report_no_suspect);
        return getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);
    }

    private void updateCrimeWidgets() {
        updateCrimeDateWidget(mCrime.getDate());
        updateCrimeTitleWidget(mCrime.getTitle());
        updateCrimeSolvedWidget(mCrime.isSolved());
        updateSuspectWidget(mCrime.getSuspect());
    }

    private void updateSuspectWidget(String suspect) {
        if(suspect != null && !suspect.isEmpty())
            mSuspectButton.setText(suspect);
    }

    private void updateCrimeSolvedWidget(boolean solved) {
        mSolvedCheckBox.setChecked(solved);
    }

    private void updateCrimeTitleWidget(String title) {
        mTitleEditText.setText(title);
    }

    private void updateCrimeDateWidget(Date date) {
        mDateButton.setText(date.toString());
    }

    private void linkWidgetsToViews(View view) {
        mTitleEditText = view.findViewById(R.id.crime_title);
        mDateButton = view.findViewById(R.id.crime_date);
        mSolvedCheckBox = view.findViewById(R.id.crime_solved);
        mReportCrimeButton = view.findViewById(R.id.crime_reportButton);
        mSuspectButton = view.findViewById(R.id.crime_suspectButton);
    }

    private void initWidgetListeners() {
        mTitleEditText.addTextChangedListener(editTextTextChangedListener());
        mSolvedCheckBox.setOnCheckedChangeListener(solvedCheckBoxOnCheckChangedListener());
        mDateButton.setOnClickListener(dateButtonOnClickListener());
        mReportCrimeButton.setOnClickListener(reportCrimeButtonOnClickListener());
        mSuspectButton.setOnClickListener(suspectButtonOnClickListener());
    }

    private View.OnClickListener suspectButtonOnClickListener() {
        return v -> {
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(i, REQUEST_CONTACT);
        };
    }

    private View.OnClickListener reportCrimeButtonOnClickListener() {
        return v -> {
          Intent i = new Intent(Intent.ACTION_SEND);
          i.setType("text/plain");
          i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
          i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
          startActivity(Intent.createChooser(i, getString(R.string.crime_report_send)));
        };
    }

    @NonNull
    private View.OnClickListener dateButtonOnClickListener() {
        return v -> {
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            TimestampPickerFragment datePicker = TimestampPickerFragment.newInstance(mCrime.getDate());
            datePicker.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            datePicker.show(fragmentManager, DIALOG_DATE);
        };
    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener solvedCheckBoxOnCheckChangedListener() {
        return (view, isChecked) -> mCrime.setSolved(isChecked);
    }

    @NonNull
    private TextWatcher editTextTextChangedListener() {
        return new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}
