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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.kikuchio.criminalintent.date";
    private static final Calendar CALENDAR_INSTANCE = Calendar.getInstance();

    private Date mDate;

    public static TimePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, date);
        TimePickerFragment datePickerFragment = new TimePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(EXTRA_DATE);
        Calendar calendar = calendarInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        View timePickerView = initTimePicker(hour, minute);
        return buildTimeDialog(timePickerView);
    }

    @NonNull
    private View initTimePicker(int hour, int minute) {
        View timePickerView = Objects.requireNonNull(getActivity())
                .getLayoutInflater().inflate(R.layout.dialog_time, null);
        TimePicker timePicker = timePickerView.findViewById(R.id.dialog_time_timePicker);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        timePicker.setOnTimeChangedListener(timePickerOnTimeChangedListener());
        return timePickerView;
    }

    private Dialog buildTimeDialog(View timePickerView) {
        return new DatePickerDialog.Builder(getActivity())
                .setView(timePickerView)
                .setTitle(R.string.time_picker_title)
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

    private TimePicker.OnTimeChangedListener timePickerOnTimeChangedListener() {
        return (view, hourOfDay, minute) -> {
            mDate = updateTime(hourOfDay, minute);
            updateDateExtra();
        };
    }

    private void updateDateExtra() {
        Objects.requireNonNull(getArguments()).putSerializable(EXTRA_DATE, mDate);
    }

    private Date updateTime(int hour, int minute) {
        Calendar calendar = calendarInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    @NonNull
    private Calendar calendarInstance() {
        CALENDAR_INSTANCE.setTime(mDate);
        return CALENDAR_INSTANCE;
    }
}

