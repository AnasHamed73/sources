package com.example.kikuchio.criminalintent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.kikuchio.criminalintent.date";
    private static final Calendar CALENDAR_INSTANCE = Calendar.getInstance();

    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(EXTRA_DATE);
        Calendar calendar = calendarInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View datePickerView = initDatePicker(year, month, day);
        return buildDateDialog(datePickerView);
    }

    @NonNull
    private View initDatePicker(int year, int month, int day) {
        View datePickerView = Objects.requireNonNull(getActivity())
                .getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker datePicker = datePickerView.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, datePickerOnDateChangedListener());
        return datePickerView;
    }

    private Dialog buildDateDialog(View datePickerView) {
        return new DatePickerDialog.Builder(getActivity())
                .setView(datePickerView)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, dateDialogOnClickListener())
                .create();
    }

    @NonNull
    private DialogInterface.OnClickListener dateDialogOnClickListener() {
        return (dialog, which) -> sendResult(Activity.RESULT_OK);
    }

    private void sendResult(int resultCode) {
        if(getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @NonNull
    private DatePicker.OnDateChangedListener datePickerOnDateChangedListener() {
        return (view, year, monthOfYear, dayOfMonth) -> {
            mDate = updateDate(year, monthOfYear, dayOfMonth);
            updateDateExtra();
        };
    }

    private void updateDateExtra() {
        Objects.requireNonNull(getArguments()).putSerializable(EXTRA_DATE, mDate);
    }

    private Date updateDate(int year, int month, int day) {
        Calendar calendar = calendarInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);        
        return calendar.getTime();
    }

    @NonNull
    private Calendar calendarInstance() {
        CALENDAR_INSTANCE.setTime(mDate);
        return CALENDAR_INSTANCE;
    }
}
