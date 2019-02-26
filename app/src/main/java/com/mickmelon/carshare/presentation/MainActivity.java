package com.mickmelon.carshare.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mickmelon.carshare.R;

/**
 * This class is the container for all the fragments. It holds the navigation drawer.
 */
public class MainActivity extends AppCompatActivity implements AdvertBrowserFragment.OnAdvertSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_advertbrowser);

        showFragment();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AdvertBrowserFragment) {
            AdvertBrowserFragment advertBrowser = (AdvertBrowserFragment) fragment;
            advertBrowser.setOnAdvertSelectedListener(this);
        }
    }

    public void showFragment() {
        AdvertBrowserFragment advertBrowser = new AdvertBrowserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(android.R.id.content, advertBrowser);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onAdvertSelected(int position) {
        //ViewAdvertFragment viewAdvert = (ViewAdvertFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_viewadvert);

        ViewAdvertFragment viewAdvert = new ViewAdvertFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        viewAdvert.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, viewAdvert);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
