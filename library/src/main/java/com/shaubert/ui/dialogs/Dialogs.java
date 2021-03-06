package com.shaubert.ui.dialogs;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Dialogs {

    public static boolean ALLOW_STATE_LOSS = false;
    public static Thread.UncaughtExceptionHandler DIALOGS_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void show(FragmentManager manager, DialogFragment fragment, String tag) {
        show(manager, fragment, tag, false);
    }

    public static void show(FragmentManager manager,
                            DialogFragment fragment,
                            String tag,
                            boolean allowReplace) {
        if (!isOperational(manager)) return;

        Fragment existingFragment = manager.findFragmentByTag(tag);
        if (existingFragment == null || allowReplace) {
            try {
                FragmentTransaction transaction = manager.beginTransaction();
                if (existingFragment != null) {
                    transaction.remove(existingFragment);
                }
                transaction.add(fragment, tag);
                if (ALLOW_STATE_LOSS) {
                    transaction.commitAllowingStateLoss();
                } else {
                    transaction.commit();
                }
            } catch (Exception ex) {
                DIALOGS_EXCEPTION_HANDLER.uncaughtException(Thread.currentThread(),
                        new RuntimeException("failed to show dialog with tag " + tag, ex));
            }
        }
    }

    public static void dismiss(FragmentManager manager, String tag) {
        if (!isOperational(manager)) return;

        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    private static boolean isOperational(FragmentManager manager) {
        return !manager.isDestroyed() && !manager.isStateSaved();
    }
}
