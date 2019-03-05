package com.mickmelon.carshare.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.util.FragmentHelper;

/**
 * This class is the container for all the fragments. It holds the navigation drawer.
 */
public class MainActivity extends AppCompatActivity implements AdvertBrowserFragment.OnAdvertSelectedListener {
    private DrawerLayout _drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        FragmentHelper.showFragment(this, new AdvertBrowserFragment());
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
        transaction.replace(R.id.fragment_container, advertBrowser);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void showFragment(Fragment fragment) {
        if (fragment == null) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onAdvertSelected(int position) {
        ViewAdvertFragment viewAdvert = new ViewAdvertFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        viewAdvert.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, viewAdvert)
                .addToBackStack(null);

        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
