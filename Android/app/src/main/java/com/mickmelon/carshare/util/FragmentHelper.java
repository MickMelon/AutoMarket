package com.mickmelon.carshare.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.presentation.MainActivity;

/**
 * Helpful functions to show and close fragments.
 */
public class FragmentHelper {
    /**
     * Indicates whether a fragment has been shown.
     */
    private static boolean _fragmentShown = false;

    /**
     * Checks whether a fragment has been shown.
     * @return Whether a fragment has been shown.
     */
    public static boolean isFragmentShown() { return _fragmentShown; }

    /**
     * Shows a fragment.
     * @param activity The calling activity.
     * @param fragment The fragment to be shown.
     */
    public static void showFragment(AppCompatActivity activity, Fragment fragment, boolean closeOldFragment) {
        if (activity == null || fragment == null) return;

        if (closeOldFragment) {
            closeCurrentFragment(activity);
        }

        fragment.setRetainInstance(true);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "CurrentFragment");
        transaction.addToBackStack(null);

        transaction.commit();

        _fragmentShown = true;
    }

    /**
     * Closes the currently open fragment that is identified by the tag parameter.
     * @param activity The calling activity.
     */
    public static void closeCurrentFragment(AppCompatActivity activity) {
        closeFragment(activity, "CurrentFragment");
    }

    /**
     * Closes a fragment that is identified by the tag parameter.
     * @param activity The calling activity.
     * @param tag The fragment tag.
     */
    public static void closeFragment(AppCompatActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment oldFragment = fragmentManager.findFragmentByTag(tag);
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }

        _fragmentShown = false;
    }
}
