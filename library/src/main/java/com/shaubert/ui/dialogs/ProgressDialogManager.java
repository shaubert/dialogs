package com.shaubert.ui.dialogs;


import android.content.DialogInterface.OnCancelListener;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.text.NumberFormat;

public class ProgressDialogManager extends AbstractDialogManager {

    private CharSequence title;
    private CharSequence message;
    private NumberFormat percentProgressFormat;
    private String numberFormat;
    private boolean spinner = true;

    public ProgressDialogManager(FragmentActivity activity, String tag) {
        this(activity.getSupportFragmentManager(), tag);
    }

    public ProgressDialogManager(FragmentManager manager, String tag) {
        super(tag, manager);
    }

    public ProgressDialogManager setTitle(CharSequence title) {
        this.title = title;
        ProgressDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setTitle(title);
        }
        return this;
    }

    public ProgressDialogManager setMessage(CharSequence message) {
        this.message = message;
        ProgressDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setMessage(message);
        }
        return this;
    }

    public ProgressDialogManager setPercentProgressFormat(NumberFormat percentProgressFormat) {
        this.percentProgressFormat = percentProgressFormat;
        return this;
    }

    public ProgressDialogManager setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }

    public ProgressDialogManager setSpinner(boolean spinner) {
        this.spinner = spinner;
        return this;
    }

    public void setMax(int max) {
        ProgressDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setMax(max);
        }
    }

    public void setProgress(int progress) {
        ProgressDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setProgress(progress);
        }
    }

    public ProgressDialogManager setCancellable(boolean cancellable) {
        return (ProgressDialogManager) super.setCancellable(cancellable);
    }

    public ProgressDialogManager setCancelListener(OnCancelListener cancelListener) {
        return (ProgressDialogManager) super.setCancelListener(cancelListener);
    }

    @Override
    public ProgressDialogManager setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        return (ProgressDialogManager) super.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    protected DialogFragment createDialog() {
        return ProgressDialogFragment.newInstance(
                title, message, spinner, numberFormat, percentProgressFormat);
    }

}