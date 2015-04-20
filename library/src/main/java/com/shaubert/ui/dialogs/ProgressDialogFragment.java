package com.shaubert.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

import java.text.NumberFormat;

public class ProgressDialogFragment extends DialogFragment {

    private OnCancelListener cancelListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence("title");
        CharSequence message = getArguments().getCharSequence("message");
        String progressNumberFormat = getArguments().getString("number-format");
        NumberFormat progressPercentFormat = (NumberFormat) getArguments().getSerializable("percent-format");
        boolean spinner = getArguments().getBoolean("spinner", true);

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        });
        dialog.setProgressStyle(spinner ? ProgressDialog.STYLE_SPINNER : ProgressDialog.STYLE_HORIZONTAL);
        if (progressNumberFormat != null) {
            dialog.setProgressNumberFormat(progressNumberFormat);
        }
        if (progressPercentFormat != null) {
            dialog.setProgressPercentFormat(progressPercentFormat);
        }
        if (message != null) {
            dialog.setMessage(message);
        }
        if (title != null) {
            dialog.setTitle(title);
        }
        return dialog;
    }

    public void setMax(int max) {
        ProgressDialog dialog = getDialog();
        if (dialog != null) {
            dialog.setMax(max);
        }
    }

    public void setProgress(int progress) {
        ProgressDialog dialog = getDialog();
        if (dialog != null) {
            dialog.setProgress(progress);
        }
    }

    public void setMessage(CharSequence message) {
        ProgressDialog dialog = getDialog();
        if (dialog != null) {
            dialog.setMessage(message);
        }
    }

    public void setTitle(CharSequence title) {
        ProgressDialog dialog = getDialog();
        if (dialog != null) {
            dialog.setTitle(title);
        }
    }

    @Override
    public ProgressDialog getDialog() {
        return (ProgressDialog) super.getDialog();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public static ProgressDialogFragment newInstance(CharSequence title, CharSequence message, boolean spinner,
            String progressNumberFormat, NumberFormat progressPercentFormat) {
        ProgressDialogFragment result = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putCharSequence("title", title);
        args.putCharSequence("message", message);
        args.putBoolean("spinner", spinner);
        args.putString("number-format", progressNumberFormat);
        args.putSerializable("percent-format", progressPercentFormat);
        result.setArguments(args);
        return result;
    }

}