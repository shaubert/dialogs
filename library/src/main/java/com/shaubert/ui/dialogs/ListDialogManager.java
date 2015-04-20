package com.shaubert.ui.dialogs;

import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.BaseAdapter;

public class ListDialogManager extends AbstractDialogManager {

    public static int DIMENSION_NOT_SET = Integer.MIN_VALUE;

    protected CharSequence title;
    protected int dialogWidth = DIMENSION_NOT_SET;
    protected int dialogHeight = DIMENSION_NOT_SET;

    private OnCancelListener cancelListener;

    private BaseAdapter listAdapter;
    private OnClickListener itemClickListener;

    private boolean cancellable;

    public ListDialogManager(FragmentActivity activity, String tag) {
        this(activity.getSupportFragmentManager(), tag);
    }

    public ListDialogManager(FragmentManager manager, String tag) {
        super(tag, manager);
    }

    public ListDialogManager setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public ListDialogManager setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    public ListDialogManager setLayout(int width, int height) {
        this.dialogWidth = width;
        this.dialogHeight = height;
        return this;
    }

    public ListDialogManager setListAdapter(BaseAdapter listAdapter) {
        this.listAdapter = listAdapter;
        setListAdapter(this.<ListDialogFragment>findDialog());
        return this;
    }

    public ListDialogManager setOnItemClickListener(OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        setOnItemClickListener(this.<ListDialogFragment>findDialog());
        return this;
    }

    public ListDialogManager setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        setCancelListener(this.<ListDialogFragment>findDialog());
        return this;
    }

    @Override
    protected DialogFragment createDialog() {
        return ListDialogFragment.newInstance(title,
                dialogWidth == DIMENSION_NOT_SET ? null : dialogWidth,
                dialogHeight == DIMENSION_NOT_SET ? null : dialogHeight);
    }

    @Override
    protected void prepareDialog(DialogFragment dialogFragment) {
        super.prepareDialog(dialogFragment);
        ListDialogFragment listDialogFragment = (ListDialogFragment) dialogFragment;
        if (listAdapter != null) {
            listDialogFragment.setListAdapter(listAdapter);
        }
        setCancelListener(listDialogFragment);
        setOnItemClickListener(listDialogFragment);
        setListAdapter(listDialogFragment);
    }

    private void setOnItemClickListener(ListDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setOnItemClickListener(itemClickListener);
        }
    }

    private void setCancelListener(ListDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setCancelable(cancellable);
            dialogFragment.setCancelListener(cancelListener);
        }
    }

    private void setListAdapter(ListDialogFragment dialogFragment) {
        if (dialogFragment != null && !dialogFragment.isListAdapterSet()) {
            dialogFragment.setListAdapter(listAdapter);
        }
    }

}