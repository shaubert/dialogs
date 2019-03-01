package com.shaubert.ui.dialogs;


import android.content.DialogInterface;
import android.os.Handler;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;

public abstract class AbstractDialogManager implements DialogManager {

    private String tag;
    private FragmentManager manager;
    private Handler handler;
    private int style = DialogFragment.STYLE_NORMAL;
    private int theme;

    private DialogInterface.OnCancelListener cancelListener;
    private boolean cancellable;
    private boolean canceledOnTouchOutside = true;

    public AbstractDialogManager(String tag, FragmentManager manager) {
        this.tag = tag;
        this.manager = manager;
        this.handler = new Handler();
    }

    public AbstractDialogManager setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    public AbstractDialogManager setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        if (canceledOnTouchOutside) {
            setCancellable(true);
        }
        return this;
    }

    public AbstractDialogManager setCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        if (cancelListener != null) {
            setCancellable(true);
        }

        setCancelListener(findDialog());

        return this;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void showIfNotVisible() {
        if (!isVisible()) {
            showDialog();
        }
    }

    public boolean showDialogSafe() {
        try {
            showDialog();
            return true;
        } catch (Exception ex) {
            Log.e(tag, "failed to show dialog", ex);
            return false;
        }
    }

    @Override
    public void showDialog() {
        if (findDialog() != null) {
            hideDialog();

            //if we hide and add fragment with same tag in same transaction we'll get IllegalState
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Dialogs.show(manager, getDialog(), tag);
                }
            });
        } else {
            Dialogs.show(manager, getDialog(), tag);
        }
    }

    protected DialogFragment getDialog() {
        DialogFragment dialogFragment = createDialog();
        prepareDialog(dialogFragment);
        return dialogFragment;
    }

    protected abstract DialogFragment createDialog();

    protected void prepareDialog(DialogFragment dialogFragment) {
        dialogFragment.setStyle(style, theme);
        setCancelListener(dialogFragment);
    }

    private void setCancelListener(DialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setCancelable(cancellable);
            if (dialogFragment instanceof CancellableDialogFragment) {
                ((CancellableDialogFragment) dialogFragment).setCanceledOnTouchOutside(canceledOnTouchOutside);
                ((CancellableDialogFragment) dialogFragment).setCancelListener(cancelListener);
            }
        }
    }

    @Override
    public void hideDialog() {
        Dialogs.dismiss(manager, tag);
    }

    @Override
    public boolean isVisible() {
        return findDialog() != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends DialogFragment> T findDialog() {
        return (T) getManager().findFragmentByTag(tag);
    }
}
