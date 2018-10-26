package com.shaubert.ui.dialogs;

import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.BaseAdapter;

public class AlertDialogManager extends AbstractDialogManager {

    public static int DIMENSION_NOT_SET = Integer.MIN_VALUE;

    protected CharSequence title;
    protected CharSequence message;
    protected CharSequence posButtonText;
    protected CharSequence neutButtonText;
    protected CharSequence negButtonText;
    protected int dialogWidth = DIMENSION_NOT_SET;
    protected int dialogHeight = DIMENSION_NOT_SET;
    protected int customWindowAnimationStyle;
    protected int customViewLayoutResId;

    private OnClickListener posButtonListener;
    private OnClickListener neutButtonListener;
    private OnClickListener negButtonListener;

    private boolean posButtonEnabled = true;
    private boolean negButtonEnabled = true;
    private boolean neutButtonEnabled = true;

    private BaseAdapter listAdapter;
    private OnClickListener itemClickListener;

    public AlertDialogManager(FragmentActivity activity, String tag) {
        this(activity.getSupportFragmentManager(), tag);
    }

    public AlertDialogManager(FragmentManager manager, String tag) {
        super(tag, manager);
    }

    public AlertDialogManager setTitle(CharSequence title) {
        this.title = title;
        AlertDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setTitle(title);
        }
        return this;
    }

    public AlertDialogManager setCancellable(boolean cancellable) {
        return (AlertDialogManager) super.setCancellable(cancellable);
    }

    public AlertDialogManager setCancelListener(OnCancelListener cancelListener) {
        return (AlertDialogManager) super.setCancelListener(cancelListener);
    }

    @Override
    public AlertDialogManager setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        return (AlertDialogManager) super.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public AlertDialogManager setMessage(CharSequence message) {
        this.message = message;
        AlertDialogFragment dialog = findDialog();
        if (dialog != null) {
            dialog.setMessage(message);
        }
        return this;
    }

    public void setCustomWindowAnimationStyle(int customWindowAnimationStyle) {
        this.customWindowAnimationStyle = customWindowAnimationStyle;
    }

    public AlertDialogManager setLayout(int widht, int height) {
        this.dialogWidth = widht;
        this.dialogHeight = height;
        return this;
    }

    public AlertDialogManager setListAdapter(BaseAdapter listAdapter) {
        this.listAdapter = listAdapter;
        setListAdapter(this.<AlertDialogFragment>findDialog());
        return this;
    }

    public AlertDialogManager setOnItemClickListener(OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        setOnItemClickListener(this.<AlertDialogFragment>findDialog());
        return this;
    }

    public void setPosButtonEnabled(boolean posButtonEnabled) {
        this.posButtonEnabled = posButtonEnabled;
        setPosButtonEnabled(this.<AlertDialogFragment>findDialog());
    }

    public void setNegButtonEnabled(boolean negButtonEnabled) {
        this.negButtonEnabled = negButtonEnabled;
        setNegButtonEnabled(this.<AlertDialogFragment>findDialog());
    }

    public void setNeutButtonEnabled(boolean neutButtonEnabled) {
        this.neutButtonEnabled = neutButtonEnabled;
        setNeutButtonEnabled(this.<AlertDialogFragment>findDialog());
    }

    public AlertDialogManager setPosButton(CharSequence posButtonText, OnClickListener listener) {
        return setPosButtonText(posButtonText).setPosButtonListener(listener);
    }

    public AlertDialogManager setNeutButton(CharSequence neutButtonText, OnClickListener listener) {
        return setNeutButtonText(neutButtonText).setNeutButtonListener(listener);
    }

    public AlertDialogManager setNegButton(CharSequence negButtonText, OnClickListener listener) {
        return setNegButtonText(negButtonText).setNegButtonListener(listener);
    }

    public AlertDialogManager setPosButtonText(CharSequence posButtonText) {
        this.posButtonText = posButtonText;
        return this;
    }

    public AlertDialogManager setNeutButtonText(CharSequence neutButtonText) {
        this.neutButtonText = neutButtonText;
        return this;
    }

    public AlertDialogManager setNegButtonText(CharSequence negButtonText) {
        this.negButtonText = negButtonText;
        return this;
    }

    public AlertDialogManager setPosButtonListener(OnClickListener posButtonListener) {
        this.posButtonListener = posButtonListener;
        setPosListener(this.<AlertDialogFragment>findDialog());
        return this;
    }

    public AlertDialogManager setNeutButtonListener(OnClickListener neutButtonListener) {
        this.neutButtonListener = neutButtonListener;
        setNeutListener(this.<AlertDialogFragment>findDialog());
        return this;
    }

    public AlertDialogManager setNegButtonListener(OnClickListener negButtonListener) {
        this.negButtonListener = negButtonListener;
        setNegListener(this.<AlertDialogFragment>findDialog());
        return this;
    }

    public AlertDialogManager setCustomViewLayoutResId(int customViewLayoutResId) {
        this.customViewLayoutResId = customViewLayoutResId;
        return this;
    }

    @Override
    protected DialogFragment createDialog() {
        return AlertDialogFragment.newInstance(
                title,
                message,
                posButtonText,
                neutButtonText,
                negButtonText,
                dialogWidth == DIMENSION_NOT_SET ? null : dialogWidth,
                dialogHeight == DIMENSION_NOT_SET ? null : dialogHeight,
                listAdapter != null,
                customWindowAnimationStyle,
                customViewLayoutResId
        );
    }

    @Override
    protected void prepareDialog(DialogFragment dialogFragment) {
        super.prepareDialog(dialogFragment);
        AlertDialogFragment alertDialogFragment = (AlertDialogFragment) dialogFragment;
        if (listAdapter != null) {
            alertDialogFragment.setListAdapter(listAdapter);
        }
        setPosListener(alertDialogFragment);
        setNeutListener(alertDialogFragment);
        setNegListener(alertDialogFragment);
        setOnItemClickListener(alertDialogFragment);
        setPosButtonEnabled(alertDialogFragment);
        setNegButtonEnabled(alertDialogFragment);
        setNeutButtonEnabled(alertDialogFragment);
    }

    private void setOnItemClickListener(AlertDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setOnItemClickListener(itemClickListener);
        }
    }

    private void setPosListener(AlertDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setPosClickListener(posButtonListener);
        }
    }

    private void setNeutListener(AlertDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setNeutClickListener(neutButtonListener);
        }
    }

    private void setNegListener(AlertDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setNegClickListener(negButtonListener);
        }
    }

    private void setListAdapter(AlertDialogFragment dialogFragment) {
        if (dialogFragment != null && !dialogFragment.isListAdapterSet()) {
            dialogFragment.setListAdapter(listAdapter);
        }
    }

    private void setPosButtonEnabled(AlertDialogFragment dialog) {
        if (dialog != null) {
            dialog.setPosButtonEnabled(posButtonEnabled);
        }
    }

    private void setNegButtonEnabled(AlertDialogFragment dialog) {
        if (dialog != null) {
            dialog.setNegButtonEnabled(negButtonEnabled);
        }
    }

    private void setNeutButtonEnabled(AlertDialogFragment dialog) {
        if (dialog != null) {
            dialog.setNeutButtonEnabled(neutButtonEnabled);
        }
    }

}
