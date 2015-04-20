package com.shaubert.ui.dialogs;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Dialogs {

    public static void show(FragmentManager manager, DialogFragment fragment, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            fragment.show(manager, tag);
        }
    }

    public static void dismiss(FragmentManager manager, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.beginTransaction()
            .remove(fragment)
            .commitAllowingStateLoss();
        }
    }
}
