package com.shaubert.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.DatePicker;

import java.util.Date;

public class DatePickerDialogFragment extends DialogFragment {

    public static DatePickerDialogFragment newInstance(CharSequence title, int year,
            int monthOfYear, int dayOfMonth, Date minDate, Date maxDate) {
        Bundle args = new Bundle();
        args.putCharSequence("title", title);
        args.putInt("year", year);
        args.putInt("monthOfYear", monthOfYear);
        args.putInt("dayOfMonth", dayOfMonth);
        args.putLong("min-date", minDate == null ? Long.MIN_VALUE : minDate.getTime());
        args.putLong("max-date", maxDate == null ? Long.MIN_VALUE : maxDate.getTime());
        DatePickerDialogFragment res = new DatePickerDialogFragment();
        res.setArguments(args);
        return res;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DialogInterface.OnCancelListener cancelListener;

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    public void setCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public DatePickerDialog getDialog() {
        return (DatePickerDialog) super.getDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence("title");
        int year = getArguments().getInt("year");
        int monthOfYear = getArguments().getInt("monthOfYear");
        int dayOfMonth = getArguments().getInt("dayOfMonth");
        long minDate = getArguments().getLong("min-date", Long.MIN_VALUE);
        long maxDate = getArguments().getLong("max-date", Long.MIN_VALUE);


        DatePickerDialog dialog = new CustomDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (dateSetListener != null) {
                    dateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
                }
            }
        }, year, monthOfYear, dayOfMonth);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        });

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (minDate != Long.MIN_VALUE) {
            dialog.getDatePicker().setMinDate(minDate);
        }
        if (maxDate != Long.MIN_VALUE) {
            dialog.getDatePicker().setMaxDate(maxDate);
        }
        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

    public static class CustomDatePickerDialog extends DatePickerDialog {

        private OnDateSetListener callBack;

        public CustomDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, null, year, monthOfYear, dayOfMonth);
            this.callBack = callBack;
        }

        public CustomDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                                      int dayOfMonth) {
            super(context, theme, null, year, monthOfYear, dayOfMonth);
            this.callBack = callBack;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            super.onClick(dialog, which);
            if (callBack != null) {
                callBack.onDateSet(getDatePicker(), getDatePicker().getYear(),
                        getDatePicker().getMonth(), getDatePicker().getDayOfMonth());
            }
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            getDatePicker().init(year, month, day, this);
        }

    }
}