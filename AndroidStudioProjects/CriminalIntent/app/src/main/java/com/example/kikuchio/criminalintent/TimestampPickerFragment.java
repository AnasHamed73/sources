package com.example.kikuchio.criminalintent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.Objects;

import static com.example.kikuchio.criminalintent.CrimeFragment.DIALOG_DATE;
import static com.example.kikuchio.criminalintent.CrimeFragment.DIALOG_TIME;
import static com.example.kikuchio.criminalintent.CrimeFragment.REQUEST_DATE;
import static com.example.kikuchio.criminalintent.CrimeFragment.REQUEST_TIME;

public class TimestampPickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.kikuchio.criminalintent.date";

    private Date mDate;

    public static TimestampPickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, date);
        TimestampPickerFragment timestampPickerFragment = new TimestampPickerFragment();
        timestampPickerFragment.setArguments(bundle);
        return timestampPickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(EXTRA_DATE);
        View timestampChooserView = initTimestampChooser();
        return buildTimestampChooserDialog(timestampChooserView);
    }

    private View initTimestampChooser() {
        View timestampChooserView = Objects.requireNonNull(getActivity()).getLayoutInflater()
                .inflate(R.layout.dialog_timestamp_chooser, null);
        initDateChoiceWidget(timestampChooserView);
        initTimeChoiceWidget(timestampChooserView);
        return timestampChooserView;
    }

    private void initTimeChoiceWidget(View timestampChooserView) {
        Button timeChoiceButton = timestampChooserView.findViewById(R.id.dialog_time_choice_button);
        timeChoiceButton.setOnClickListener(v -> initiateTimePickerDialog());
    }

    private void initDateChoiceWidget(View timestampChooserView) {
        Button dateChoiceButton = timestampChooserView.findViewById(R.id.dialog_date_choice_button);
        dateChoiceButton.setOnClickListener(v -> initiateDatePickerDialog());
    }

    private void initiateTimePickerDialog() {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mDate);
        showDialog(timePickerFragment, REQUEST_TIME, DIALOG_TIME);
    }

    private void initiateDatePickerDialog() {
        DatePickerFragment dateFragment = DatePickerFragment.newInstance(mDate);
        showDialog(dateFragment, REQUEST_DATE, DIALOG_DATE);
    }

    private void showDialog(DialogFragment fragment, int requestCode, String tag) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragment.setTargetFragment(getTargetFragment(), requestCode);
        this.dismiss();
        fragment.show(fragmentManager, tag);
    }

    private Dialog buildTimestampChooserDialog(View timestampChooserView) {
        return new DatePickerDialog.Builder(getActivity())
                .setView(timestampChooserView)
                .setTitle(R.string.timestamp_chooser_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
