package com.mickmelon.carshare.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mickmelon.carshare.R;

public class FragmentHelper {
    /**
     * Shows a fragment.
     * @param activity The calling activity.
     * @param fragment The fragment to be shown.
     */
    public static void showFragment(AppCompatActivity activity, Fragment fragment, boolean closeOldFragment) {
        if (activity == null || fragment == null) return;

        if (closeOldFragment) {
            closeCurrentFragment(activity, "CurrentFragment");
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "CurrentFragment");
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * Closes the currently open fragment that is identified by the tag parameter.
     * @param activity The calling activity.
     * @param tag The fragment tag. Usually "CurrentFragment".
     */
    public static void closeCurrentFragment(AppCompatActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment oldFragment = fragmentManager.findFragmentByTag(tag);
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }
    }
}
