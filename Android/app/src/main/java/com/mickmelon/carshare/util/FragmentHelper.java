package com.mickmelon.carshare.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mickmelon.carshare.R;

public class FragmentHelper {
    public static void showFragment(AppCompatActivity activity, Fragment fragment) {
        if (activity == null || fragment == null) return;

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
