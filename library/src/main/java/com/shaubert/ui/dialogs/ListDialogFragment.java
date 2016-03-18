package com.shaubert.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.shaubert.ui.adapters.AdaptersCarousel;

public class ListDialogFragment extends CancellableDialogFragment {

    public static final String ATTR_TITLE = "title";
    public static final String ATTR_WIDTH = "width";
    public static final String ATTR_HEIGHT = "height";

    private OnClickListener onItemClickListener;
    private AdaptersCarousel adapterWrapper = new AdaptersCarousel();

    private OnClickListener onItemClick = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(dialog, which);
            }
        }
    };

    public static ListDialogFragment newInstance(CharSequence title) {
        return newInstance(title, null, null);
    }

    public static ListDialogFragment newInstance(CharSequence title, Integer width, Integer height) {
        Bundle args = getBundle(title, width, height);

        ListDialogFragment result = new ListDialogFragment();
        result.setArguments(args);
        return result;
    }

    protected static Bundle getBundle(CharSequence title, Integer width, Integer height) {
        Bundle args = new Bundle();
        args.putCharSequence(ATTR_TITLE, title);
        if (width != null) {
            args.putInt(ATTR_WIDTH, width);
        }
        if (height != null) {
            args.putInt(ATTR_HEIGHT, height);
        }
        return args;
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
        this.adapterWrapper.addAdapter(adapter);
    }

    public boolean isListAdapterSet() {
        return this.adapterWrapper.getAdaptersCount() > 0;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        CharSequence title = getArguments().getCharSequence(ATTR_TITLE);
        if (TextUtils.isEmpty(title)) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        } else {
            dialog.setTitle(title);
        }

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        });

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
        dialog.setContentView(list, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            WindowManager.LayoutParams attributes = new WindowManager.LayoutParams();
            attributes.copyFrom(getDialog().getWindow().getAttributes());
            if (getArguments().containsKey(ATTR_WIDTH) && getArguments().containsKey(ATTR_HEIGHT)) {
                int width = getArguments().getInt(ATTR_WIDTH);
                int height = getArguments().getInt(ATTR_HEIGHT);
                attributes.width = width;
                attributes.height = height;
            } else {
                float screenWidthDp = Sizes.pxToDp(
                        getActivity().getResources().getDisplayMetrics().widthPixels, getActivity());
                if (screenWidthDp > 360) {
                    attributes.width = Sizes.dpToPx(340, getActivity());
                } else {
                    attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                }
            }

            getDialog().getWindow().setAttributes(attributes);
        }
    }

}