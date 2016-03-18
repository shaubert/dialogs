package com.shaubert.ui.dialogs;

import android.app.DatePickerDialog;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Date;

public class DatePickerDialogManager extends AbstractDialogManager {

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private int day;
    private int month;
    private int year;
    private Date minDate;
    private Date maxDate;

    private CharSequence title;

    public DatePickerDialogManager(FragmentActivity activity, String tag) {
        this(activity.getSupportFragmentManager(), tag);
    }

    public DatePickerDialogManager(FragmentManager manager, String tag) {
        super(tag, manager);
    }

    public DatePickerDialogManager setCancellable(boolean cancellable) {
        return (DatePickerDialogManager) super.setCancellable(cancellable);
    }

    public DatePickerDialogManager setCancelListener(OnCancelListener cancelListener) {
        return (DatePickerDialogManager) super.setCancelListener(cancelListener);
    }

    @Override
    public DatePickerDialogManager setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        return (DatePickerDialogManager) super.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public DatePickerDialogManager setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
        setDateSetListener(this.<DatePickerDialogFragment> findDialog());
        return this;
    }

    public DatePickerDialogManager setDay(int day) {
        this.day = day;
        return this;
    }

    public DatePickerDialogManager setMonth(int month) {
        this.month = month;
        return this;
    }

    public DatePickerDialogManager setYear(int year) {
        this.year = year;
        return this;
    }

    public DatePickerDialogManager setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public DatePickerDialogManager setMinDate(Date minDate) {
        this.minDate = minDate != null ? new Date(minDate.getTime()) : null;
        return this;
    }

    public DatePickerDialogManager setMaxDate(Date maxDate) {
        this.maxDate = maxDate != null ? new Date(maxDate.getTime()) : null;
        return this;
    }

    @Override
    public DialogFragment createDialog() {
        return DatePickerDialogFragment.newInstance(title, year, month, day, minDate, maxDate);
    }

    @Override
    protected void prepareDialog(DialogFragment dialogFragment) {
        super.prepareDialog(dialogFragment);

        DatePickerDialogFragment datePickerDialogFragment = (DatePickerDialogFragment) dialogFragment;
        setDateSetListener(datePickerDialogFragment);
    }

    private void setDateSetListener(DatePickerDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setDateSetListener(dateSetListener);
        }
    }

}
