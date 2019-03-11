package com.mickmelon.carshare.presentation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mickmelon.carshare.Identity;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.ToastHelper;

import cz.msebera.android.httpclient.impl.conn.LoggingSessionOutputBuffer;

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

        if (!FragmentHelper.isInitialised()) {
            FragmentHelper.showFragment(this, new AdvertBrowserFragment(), true);
        }

        setupMenu();
    }

    /**
     * Sets up the navigation menu by assigning all the menu options depending on whether the user
     * is logged in or not.
     */
    private void setupMenu() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        menu.add(0, MenuItemName.BROWSE_VEHICLES.ordinal(), 0, "Browse Vehicles").setCheckable(true);

        if (Identity.isLoggedIn()) {
            menu.add(0, MenuItemName.POST_ADVERT.ordinal(), 0, "Post Advert").setCheckable(true);
            menu.add(0, MenuItemName.YOUR_PROFILE.ordinal(), 0, "Your Profile").setCheckable(true);
            menu.add(0, MenuItemName.LOGOUT.ordinal(), 0, "Logout").setCheckable(true);
        } else {
            menu.add(0, MenuItemName.LOGIN.ordinal(), 0, "Login").setCheckable(true);
            menu.add(0, MenuItemName.REGISTER.ordinal(), 0, "Register").setCheckable(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                MenuItemName itemName = getNameFromInt(id);

                menuItem.setChecked(true);

                switch (itemName) {
                    case BROWSE_VEHICLES:
                        FragmentHelper.showFragment(MainActivity.this, new AdvertBrowserFragment(), true);
                        break;

                    case LOGIN:
                        FragmentHelper.showFragment(MainActivity.this, new LoginFragment(), true);
                        break;

                    case LOGOUT:
                        ToastHelper.showToast(getApplicationContext(), "Logout");
                        Identity.logout();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case REGISTER:
                        FragmentHelper.showFragment(MainActivity.this, new RegisterFragment(), true);
                        break;

                    case POST_ADVERT:
                        FragmentHelper.showFragment(MainActivity.this, new PostAdvertFragment(), true);
                        break;

                    case YOUR_PROFILE:
                        ToastHelper.showToast(getApplicationContext(), "Your Profile");
                        break;

                    default:
                        break;
                }

                _drawerLayout.closeDrawers();

                return true;
            }
        });

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AdvertBrowserFragment) {
            AdvertBrowserFragment advertBrowser = (AdvertBrowserFragment) fragment;
            advertBrowser.setOnAdvertSelectedListener(this);
        }
    }

    @Override
    public void onAdvertSelected(int position) {
        ViewAdvertFragment viewAdvert = new ViewAdvertFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        viewAdvert.setArguments(args);

        FragmentHelper.showFragment(this, viewAdvert, false);
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

    enum MenuItemName {
        LOGIN,
        LOGOUT,
        REGISTER,
        BROWSE_VEHICLES,
        POST_ADVERT,
        YOUR_PROFILE
    }

    public static MenuItemName getNameFromInt(int itemId) {
        return MenuItemName.values()[itemId];
    }
}