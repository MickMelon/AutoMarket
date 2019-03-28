package com.mickmelon.carshare.presentation;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import com.mickmelon.carshare.util.IntentHelper;
import com.mickmelon.carshare.util.ToastHelper;

import java.io.IOException;

import cz.msebera.android.httpclient.impl.conn.LoggingSessionOutputBuffer;

/**
 * This class is the container for all the fragments. It holds the navigation drawer.
 */
public class MainActivity extends AppCompatActivity implements AdvertBrowserFragment.OnAdvertSelectedListener {
    /**
     * The navigation drawer.
     */
    private DrawerLayout _drawerLayout;

    /**
     * Called when the activity is about to be created.
     */
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

        // Only change the fragment if there isn't one shown. This is to accommodate for this
        // onCreate function being called every time the orientation is changed. The Fragments
        // use ViewModels to support orientation changes within themselves.
        if (!FragmentHelper.isFragmentShown()) {
            FragmentHelper.showFragment(this, new AdvertBrowserFragment(), true);
        }

        setupMenu();
    }

    /**
     * Sets up the navigation menu by assigning all the menu options depending on whether the user
     * is logged in or not.
     */
    public void setupMenu() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        menu.clear();

        menu.add(0, MenuItemName.BROWSE_VEHICLES.ordinal(), 0, "Browse Vehicles").setCheckable(true);

        if (Identity.isLoggedIn()) {
            menu.add(2, MenuItemName.POST_ADVERT.ordinal(), 0, "Post Advert").setCheckable(true);
            menu.add(2, MenuItemName.YOUR_PROFILE.ordinal(), 0, "Your Profile").setCheckable(true);
            menu.add(1, MenuItemName.LOGOUT.ordinal(), 0, "Logout").setCheckable(true);
        } else {
            menu.add(1, MenuItemName.LOGIN.ordinal(), 0, "Login").setCheckable(true);
            menu.add(1, MenuItemName.REGISTER.ordinal(), 0, "Register").setCheckable(true);
        }

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            MenuItemName itemName = getNameFromInt(id);

            menuItem.setChecked(true);

            switch (itemName) {
                case BROWSE_VEHICLES:
                    FragmentHelper.showFragment(this, new AdvertBrowserFragment(), true);
                    break;

                case LOGIN:
                    FragmentHelper.showFragment(this, new LoginFragment(), true);
                    break;

                case LOGOUT:
                    ToastHelper.showToast(getApplicationContext(), "Logout");
                    Identity.logout();
                    FragmentHelper.showFragment(this, new AdvertBrowserFragment(), true);
                    setupMenu();
                    break;

                case REGISTER:
                    FragmentHelper.showFragment(this, new RegisterFragment(), true);
                    break;

                case POST_ADVERT:
                    FragmentHelper.showFragment(this, new PostAdvertFragment(), true);
                    break;

                case YOUR_PROFILE:
                    ToastHelper.showToast(getApplicationContext(), "Your Profile");
                    break;

                default:
                    break;
            }

            _drawerLayout.closeDrawers();

            return true;
        });

    }

    /**
     * Called when a fragment has been attached to the activity.
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AdvertBrowserFragment) {
            AdvertBrowserFragment advertBrowser = (AdvertBrowserFragment) fragment;
            advertBrowser.setOnAdvertSelectedListener(this);
        }
    }

    /**
     * Called when an advert has been selected.
     */
    @Override
    public void onAdvertSelected(int position) {
        ViewAdvertFragment viewAdvert = new ViewAdvertFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        viewAdvert.setArguments(args);

        FragmentHelper.showFragment(this, viewAdvert, false);
    }

    /**
     * Called when an item has been selected on the navigation drawer menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the back button has been pressed.
     */
    @Override
    public void onBackPressed() {
        FragmentHelper.showFragment(this, new AdvertBrowserFragment(), true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        _drawerLayout.closeDrawers();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == MenuItemName.BROWSE_VEHICLES.ordinal()) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
    }

    /**
     * Enum used to indicate the names of the menu items.
     */
    enum MenuItemName {
        LOGIN,
        LOGOUT,
        REGISTER,
        BROWSE_VEHICLES,
        POST_ADVERT,
        YOUR_PROFILE
    }

    /**
     * Gets the menu item name corresponding to the specified id.
     */
    public static MenuItemName getNameFromInt(int itemId) {
        return MenuItemName.values()[itemId];
    }
}