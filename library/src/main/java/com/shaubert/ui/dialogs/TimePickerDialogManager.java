package com.shaubert.ui.dialogs;

import android.app.TimePickerDialog;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;

public class TimePickerDialogManager extends AbstractDialogManager {

    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private int hour;
    private int minutes;
    private boolean h24;

    private CharSequence title;

    public TimePickerDialogManager(FragmentActivity activity, String tag) {
        this(activity.getSupportFragmentManager(), tag);
        h24 = DateFormat.is24HourFormat(activity);
    }


    public TimePickerDialogManager(FragmentManager manager, String tag) {
        super(tag, manager);
    }

    public TimePickerDialogManager setCancellable(boolean cancellable) {
        return (TimePickerDialogManager) super.setCancellable(cancellable);
    }

    public TimePickerDialogManager setCancelListener(OnCancelListener cancelListener) {
        return (TimePickerDialogManager) super.setCancelListener(cancelListener);
    }

    @Override
    public TimePickerDialogManager setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        return (TimePickerDialogManager) super.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public TimePickerDialogManager setTimeSetListener(TimePickerDialog.OnTimeSetListener timeSetListener) {
        this.timeSetListener = timeSetListener;
        setTimeSetListener(this.<TimePickerDialogFragment>findDialog());
        return this;
    }

    public TimePickerDialogManager setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public TimePickerDialogManager setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public TimePickerDialogManager setH24(boolean h24) {
        this.h24 = h24;
        return this;
    }

    public TimePickerDialogManager setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public DialogFragment createDialog() {
        return TimePickerDialogFragment.newInstance(title, hour, minutes, h24);
    }

    @Override
    protected void prepareDialog(DialogFragment dialogFragment) {
        super.prepareDialog(dialogFragment);

        TimePickerDialogFragment timePickerDialogFragment = (TimePickerDialogFragment) dialogFragment;
        setTimeSetListener(timePickerDialogFragment);
    }

    private void setTimeSetListener(TimePickerDialogFragment fragment) {
        if (fragment != null) {
            fragment.setTimeSetListener(this.timeSetListener);
        }
    }

}
