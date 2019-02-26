package com.mickmelon.carshare.presentation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mickmelon.carshare.R;

/**
 * This class is the container for all the fragments. It holds the navigation drawer.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_advertbrowser);

        showFragment();
    }

    public void showFragment() {
        AdvertBrowserFragment advertBrowser = new AdvertBrowserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(android.R.id.content, advertBrowser);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
