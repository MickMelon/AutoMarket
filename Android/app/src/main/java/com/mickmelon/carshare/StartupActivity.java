package com.mickmelon.carshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;
import com.mickmelon.carshare.presentation.MainActivity;
import com.mickmelon.carshare.presentation.MapsActivity;

public class StartupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean hijack = false;

        if (hijack) {
            hijack();
            return;
        }

        if (verifyGooglePlayServices()) {
            // We can safely start the app
            System.out.println("Verified");
            setContentView(R.layout.activity_startup);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else System.out.println("NOT VERIFIED");
    }

    private void hijack() {
        boolean success = DataAccess.getInstance().adverts().addAdvert(new Advert(-1, "TEST", "TEST", 3453, 1));
    }

    /*
     * Verify whether the user has Google play services on their device. This is required to use
     * things like Google Maps.
     */
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
