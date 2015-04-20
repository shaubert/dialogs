package com.shaubert.ui.dialogs;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class EditTextDialogManager extends AlertDialogManager {

    protected int editTextLayoutResId;
    protected CharSequence text;

    public EditTextDialogManager(FragmentManager manager, String tag) {
        super(manager, tag);
    }

    public EditTextDialogManager(FragmentActivity activity, String tag) {
        super(activity, tag);
    }

    public int getEditTextLayoutResId() {
        return editTextLayoutResId;
    }

    public EditTextDialogManager setEditTextLayoutResId(int editTextLayoutResId) {
        this.editTextLayoutResId = editTextLayoutResId;
        return this;
    }

    @Override
    protected AlertDialogFragment createDialog() {
        EditTextDialogFragment result = new EditTextDialogFragment();
        result.setArguments(EditTextDialogFragment.getBundle(title, message,
                posButtonText, neutButtonText, negButtonText,
                dialogWidth == DIMENSION_NOT_SET ? null : dialogWidth,
                dialogHeight == DIMENSION_NOT_SET ? null : dialogHeight,
                customWindowAnimationStyle,
                editTextLayoutResId, text));
        return result;
    }

    public CharSequence getText() {
        EditTextDialogFragment dialogFragment = findDialog();
        if (dialogFragment != null) {
            CharSequence text = dialogFragment.getText();
            return text != null ? text : "";
        } else {
            return "";
        }
    }

    public EditTextDialogManager setText(CharSequence text) {
        this.text = text;
        setText(this.<EditTextDialogFragment>findDialog());
        return this;
    }

    private void setText(EditTextDialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.setText(text);
        }
    }
}
