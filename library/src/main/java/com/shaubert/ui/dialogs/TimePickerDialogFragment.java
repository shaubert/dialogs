package com.shaubert.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.widget.TimePicker;

public class TimePickerDialogFragment extends CancellableDialogFragment {

    public static TimePickerDialogFragment newInstance(CharSequence title, int hour,
            int minutes, boolean h24) {
        Bundle args = new Bundle();
        args.putCharSequence("title", title);
        args.putInt("hour", hour);
        args.putInt("minutes", minutes);
        args.putBoolean("24h", h24);
        TimePickerDialogFragment res = new TimePickerDialogFragment();
        res.setArguments(args);
        return res;
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener;

    public void setTimeSetListener(TimePickerDialog.OnTimeSetListener timeSetListener) {
        this.timeSetListener = timeSetListener;
    }

    @Override
    public DatePickerDialog getDialog() {
        return (DatePickerDialog) super.getDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence("title");
        int hour = getArguments().getInt("hour", 0);
        int minutes = getArguments().getInt("minutes", 0);
        boolean h24 = getArguments().getBoolean("24h", DateFormat.is24HourFormat(getActivity()));

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (timeSetListener != null) {
                    timeSetListener.onTimeSet(view, hourOfDay, minute);
                }
            }
        }, hour, minutes, h24);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        });

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        return dialog;
    }

}