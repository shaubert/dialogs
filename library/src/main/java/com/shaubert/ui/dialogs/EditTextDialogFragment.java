package com.shaubert.ui.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.shaubert.ui.dialogs.commons.Keyboard;

public class EditTextDialogFragment extends AlertDialogFragment {

    public static final String EDITTEXT_LAYOUT_RES_ID = "edittext-layout-res-id";
    public static final String EDITTEXT_TEXT = "edittext-text";

    public static Bundle getBundle(CharSequence title, CharSequence message, CharSequence posButtonText,
                                                  CharSequence neutralButtonText, CharSequence negButtonText, Integer widht,
                                                  Integer height, int customWindowAnimationStyle,
                                                  int editTextLayoutResId, CharSequence text) {
        Bundle args = getBundle(title, message, posButtonText, neutralButtonText, negButtonText,
                widht, height, false, customWindowAnimationStyle);
        args.putInt(EDITTEXT_LAYOUT_RES_ID, editTextLayoutResId);
        args.putCharSequence(EDITTEXT_TEXT, text);
        return args;
    }

    private EditText editText;
    private Handler handler = new Handler();

    @Override
    protected AlertDialog.Builder buildDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = super.buildDialog(savedInstanceState);

        int layout = getArguments().getInt(EDITTEXT_LAYOUT_RES_ID, -1);
        if (layout > 0) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(layout, null);
            setupCustomView(view);
            builder.setView(view);
        }
        return builder;
    }

    protected void setupCustomView(View view) {
        editText = find(view, EditText.class);
        if (editText != null) {
            CharSequence text = getArguments().getCharSequence(EDITTEXT_TEXT);
            editText.setText(text);
            if (!TextUtils.isEmpty(text)) {
                editText.setSelection(text.length());
            }
            if (editText.isEnabled()) {
                Keyboard.showKeyboard(editText, handler);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected  <T> T find(View view, Class<T> clazz) {
        if (clazz.isAssignableFrom(view.getClass())) {
            return (T) view;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                T res = find(child, clazz);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    protected EditText getEditText() {
        return editText;
    }

    public CharSequence getText() {
        if (editText != null) {
            return editText.getText();
        } else {
            return null;
        }
    }

    public void setText(CharSequence text) {
        if (editText != null) {
            editText.setText(text);
        }
    }
}
