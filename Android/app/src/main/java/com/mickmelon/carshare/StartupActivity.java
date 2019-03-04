package com.mickmelon.carshare;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mickmelon.carshare.database.HttpClient;
import com.mickmelon.carshare.database.PostAsyncTask;
import com.mickmelon.carshare.presentation.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.concurrent.ExecutionException;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        PostAsyncTask task = new PostAsyncTask();

        String seller = null;
        try {
            seller = task.execute("c=seller&a=read&id=1").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(seller);
            String name = jsonObject.getString("Name");

            System.out.println("NAME: " + name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();/*
        if (verifyGooglePlayServices()) {
            // We can safely start the app
            System.out.println("Verified");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else System.out.println("NOT VERIFIED");*/

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
