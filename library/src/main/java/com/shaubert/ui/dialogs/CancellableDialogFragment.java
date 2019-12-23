package com.shaubert.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatDialogFragment;

public class CancellableDialogFragment extends AppCompatDialogFragment {

    private DialogInterface.OnCancelListener cancelListener;
    private boolean canceledOnTouchOutside;

    public void setCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        if (cancelListener != null) {
            setCancelable(true);
        }
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            if (isCancelable()) {
                getDialog().setCanceledOnTouchOutside(canceledOnTouchOutside);
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

}
