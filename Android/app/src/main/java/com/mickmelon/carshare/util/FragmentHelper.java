package com.mickmelon.carshare.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mickmelon.carshare.R;

public class FragmentHelper {
    /**
     * Shows a fragment.
     * @param activity The calling activity.
     * @param fragment The fragment to be shown.
     */
    public static void showFragment(AppCompatActivity activity, Fragment fragment) {
        if (activity == null || fragment == null) return;

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
