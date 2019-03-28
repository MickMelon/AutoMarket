package com.mickmelon.carshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mickmelon.carshare.core.Constants;
import com.mickmelon.carshare.database.HttpClient;
import com.mickmelon.carshare.database.HttpResult;
import com.mickmelon.carshare.database.PostData;
import com.mickmelon.carshare.presentation.MainActivity;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is the starting activity - it is the activity that is created when the application first starts.
 * Its task is to ensure that it is safe to start the application by doing things like checking
 * if Google Play services are available.
 */
public class StartupActivity extends AppCompatActivity {
    /**
     * Called when the activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        //test();

        if (verifyGooglePlayServices()) {
            // We can safely start the app
            System.out.println("Verified");
            setContentView(R.layout.activity_startup);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else System.out.println("NOT VERIFIED");
    }

    /**
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

    private void test() {


        HttpClient.HttpGetImageAsyncTask imageTask = new HttpClient.HttpGetImageAsyncTask();
        Bitmap bitmap = null;
        try {
            bitmap = imageTask.execute("https://jira.appcelerator.org/secure/useravatar?fsize=small&avatarId=10174").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        HttpClient.HttpPostImageAsyncTask task = new HttpClient.HttpPostImageAsyncTask();
        List<AbstractMap.SimpleEntry> params = new ArrayList<>();

        params.add(new AbstractMap.SimpleEntry("image", bitmap));
        params.add(new AbstractMap.SimpleEntry("advertId", 1));
        PostData postData = new PostData(Constants.PHP_SERVER_URL + "?c=advert&a=upload_image", params);
        try {
            HttpResult result = task.execute(postData).get();
            System.out.println("** Result ** \n" + result.getResult() + "\n** End-result **\n");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("It fucked up");
        }
    }
}
