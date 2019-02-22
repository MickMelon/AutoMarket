package com.mickmelon.carshare;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.sql.Connection;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (verifyGooglePlayServices()) {
            // We can safely start the app
            System.out.println("Verified");
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else System.out.println("NOT VERIFIED");

    }

    private boolean verifyGooglePlayServices() {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int result = availability.isGooglePlayServicesAvailable(getApplicationContext());

        if (result != ConnectionResult.SUCCESS) {
            if (availability.isUserResolvableError(result)) {
                availability.getErrorDialog(this, result, 1000).show();
            }
            return false;
        }

        return true;
    }
}
