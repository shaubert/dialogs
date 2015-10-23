package com.shaubert.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.shaubert.ui.adapters.AdaptersCarousel;

public class AlertDialogFragment extends DialogFragment {

    public static final String ATTR_TITLE = "title";
    public static final String ATTR_MESSAGE = "message";
    public static final String ATTR_POS_BUTTON_TEXT = "posButton";
    public static final String ATTR_NEUT_BUTTON_TEXT = "neutButton";
    public static final String ATTR_NEG_BUTTON_TEXT = "negButton";
    public static final String ATTR_WIDTH = "width";
    public static final String ATTR_HEIGHT = "height";
    public static final String ATTR_HAS_LIST_VIEW = "listview";
    public static final String ATTR_POS_BUTTON_DISABLED = "pos_button_disabled";
    public static final String ATTR_NEG_BUTTON_DISABLED = "neg_button_disabled";
    public static final String ATTR_NEUT_BUTTON_DISABLED = "neut_button_disabled";
    public static final String ATTR_CUSTOM_WINDOW_ANIMATION = "custom_window_animation";

    private OnClickListener posClickListener;
    private OnClickListener neutClickListener;
    private OnClickListener negClickListener;
    private DialogInterface.OnCancelListener cancelListener;

    private OnClickListener onItemClickListener;
    private AdaptersCarousel adapterWrapper = new AdaptersCarousel();

    private OnClickListener posButtonClick = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (posClickListener != null) {
                posClickListener.onClick(dialog, which);
            }
        }
    };
    private OnClickListener neutButtonClick = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (neutClickListener != null) {
                neutClickListener.onClick(dialog, which);
            }
        }
    };
    private OnClickListener negButtonClick = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (negClickListener != null) {
                negClickListener.onClick(dialog, which);
            }
        }
    };

    private OnClickListener onItemClick = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(dialog, which);
            }
        }
    };

    public static AlertDialogFragment newInstance(CharSequence title, CharSequence message, CharSequence posButtonText,
                                                  CharSequence neutralButtonText, CharSequence negButtonText) {
        return newInstance(title, message, posButtonText, neutralButtonText, negButtonText, null, null, false, -1);
    }

    public static AlertDialogFragment newInstance(CharSequence title, CharSequence message, CharSequence posButtonText,
            CharSequence neutralButtonText, CharSequence negButtonText, Integer width, Integer height, boolean hasListView,
            int customWindowAnimationStyle) {
        Bundle args = getBundle(title, message, posButtonText, neutralButtonText, negButtonText,
                width, height, hasListView, customWindowAnimationStyle);

        AlertDialogFragment result = new AlertDialogFragment();
        result.setArguments(args);
        return result;
    }

    protected static Bundle getBundle(CharSequence title, CharSequence message, CharSequence posButtonText,
                                      CharSequence neutralButtonText, CharSequence negButtonText, Integer width,
                                      Integer height, boolean hasListView, int customWindowAnimationStyle) {
        Bundle args = new Bundle();
        args.putCharSequence(ATTR_TITLE, title);
        args.putCharSequence(ATTR_MESSAGE, message);
        args.putCharSequence(ATTR_POS_BUTTON_TEXT, posButtonText);
        args.putCharSequence(ATTR_NEUT_BUTTON_TEXT, neutralButtonText);
        args.putCharSequence(ATTR_NEG_BUTTON_TEXT, negButtonText);
        args.putBoolean(ATTR_HAS_LIST_VIEW, hasListView);
        if (width != null) {
            args.putInt(ATTR_WIDTH, width);
        }
        if (height != null) {
            args.putInt(ATTR_HEIGHT, height);
        }
        args.putInt(ATTR_CUSTOM_WINDOW_ANIMATION, customWindowAnimationStyle);
        return args;
    }

    public void setPosClickListener(OnClickListener posClickListener) {
        this.posClickListener = posClickListener;
    }

    public void setNeutClickListener(OnClickListener neutClickListener) {
        this.neutClickListener = neutClickListener;
    }

    public void setNegClickListener(OnClickListener negClickListener) {
        this.negClickListener = negClickListener;
    }

    public void setCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        if (cancelListener != null) {
            setCancelable(true);
        }
    }

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setListAdapter(BaseAdapter adapter) {
        if (adapterWrapper.getAdaptersCount() > 0) {
            throw new IllegalStateException("adapter was already set");
        }
        if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        if (!getArguments().getBoolean(ATTR_HAS_LIST_VIEW, false)) {
            throw new IllegalStateException("to set adapter you must instantiate fragment with hasListView=true");
        }
        this.adapterWrapper.addAdapter(adapter);
    }

    public boolean isListAdapterSet() {
        return this.adapterWrapper.getAdaptersCount() > 0;
    }

    @Override
    public AlertDialog getDialog() {
        return (AlertDialog) super.getDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = buildDialog(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            WindowManager.LayoutParams attributes = new WindowManager.LayoutParams();
            attributes.copyFrom(getDialog().getWindow().getAttributes());
            Bundle arguments = getArguments();
            if (arguments.containsKey(ATTR_WIDTH) && arguments.containsKey(ATTR_HEIGHT)) {
                int width = arguments.getInt(ATTR_WIDTH);
                int height = arguments.getInt(ATTR_HEIGHT);
                attributes.width = width;
                attributes.height = height;
            }

            int customAnimationStyle = arguments.getInt(ATTR_CUSTOM_WINDOW_ANIMATION, -1);
            if (customAnimationStyle > 0) {
                attributes.windowAnimations = customAnimationStyle;
            }

            getDialog().getWindow().setAttributes(attributes);

            if (arguments.getBoolean(ATTR_POS_BUTTON_DISABLED, false)) {
                setPosButtonEnabled(false);
            }
            if (arguments.getBoolean(ATTR_NEG_BUTTON_DISABLED, false)) {
                setNegButtonEnabled(false);
            }
            if (arguments.getBoolean(ATTR_NEUT_BUTTON_DISABLED, false)) {
                setNeutButtonEnabled(false);
            }
        }
    }

    protected AlertDialog.Builder buildDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence(ATTR_TITLE);
        CharSequence mes = getArguments().getCharSequence(ATTR_MESSAGE);
        CharSequence pos = getArguments().getCharSequence(ATTR_POS_BUTTON_TEXT);
        CharSequence neut = getArguments().getCharSequence(ATTR_NEUT_BUTTON_TEXT);
        CharSequence neg = getArguments().getCharSequence(ATTR_NEG_BUTTON_TEXT);
        boolean hasListView = getArguments().getBoolean(ATTR_HAS_LIST_VIEW, false);

        AlertDialog.Builder builder = null;
        int customTheme = getTheme();
        if (customTheme != 0) {
            if (customTheme >= 0x01000000) {   // start of real resource IDs.
                builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), customTheme));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                builder = new AlertDialog.Builder(getActivity(), customTheme);
            }
        }
        if (builder == null) {
            builder = new AlertDialog.Builder(getActivity());
        }

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return dispatchOnBackPressed();
                } else {
                    return keyCode == KeyEvent.KEYCODE_SEARCH;
                }
            }
        });
        if (title != null) {
            builder.setTitle(title);
        }
        if (mes != null) {
            builder.setMessage(mes);
        }
        if (pos != null) {
            builder.setPositiveButton(pos, posButtonClick);
        }
        if (neut != null) {
            builder.setNeutralButton(neut, neutButtonClick);
        }
        if (neg != null) {
            builder.setNegativeButton(neg, negButtonClick);
        }

        if (hasListView) {
            ListView list = new ListView(getActivity());
            list.setPadding(0, 0, 0, 0);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemClick.onClick(getDialog(), position);
                }
            });
            list.setCacheColorHint(getActivity().getResources().getColor(android.R.color.transparent));
            list.setAdapter(adapterWrapper);
            builder.setView(list);
        }
        return builder;
    }

    protected boolean dispatchOnBackPressed() {
        return false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

    public void setMessage(CharSequence message) {
        if (getDialog() != null) {
            getArguments().putCharSequence(ATTR_MESSAGE, message);
            getDialog().setMessage(message);
        }
    }

    public void setTitle(CharSequence title) {
        if (getDialog() != null) {
            getArguments().putCharSequence(ATTR_TITLE, title);
            getDialog().setTitle(title);
        }
    }

    public void setPosButtonEnabled(boolean enabled) {
        setButtonEnabled(AlertDialog.BUTTON_POSITIVE, enabled);
        saveButtonEnabledStateToArgs(ATTR_POS_BUTTON_DISABLED, enabled);
    }

    public void setNegButtonEnabled(boolean enabled) {
        setButtonEnabled(AlertDialog.BUTTON_NEGATIVE, enabled);
        saveButtonEnabledStateToArgs(ATTR_NEG_BUTTON_DISABLED, enabled);
    }

    public void setNeutButtonEnabled(boolean enabled) {
        setButtonEnabled(AlertDialog.BUTTON_NEUTRAL, enabled);
        saveButtonEnabledStateToArgs(ATTR_NEUT_BUTTON_DISABLED, enabled);
    }

    private void setButtonEnabled(int buttonId, boolean enabled) {
        if (getDialog() != null) {
            Button button = getDialog().getButton(buttonId);
            if (button != null) {
                button.setEnabled(enabled);
            }
        }
    }

    private void saveButtonEnabledStateToArgs(String key, boolean enabled) {
        if (enabled) {
            getArguments().remove(key);
        } else {
            getArguments().putBoolean(key, true);
        }
    }

}