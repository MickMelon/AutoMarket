package com.mickmelon.carshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.HttpClient;
import com.mickmelon.carshare.database.PostData;
import com.mickmelon.carshare.database.RemoteSellerRepository;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        /*HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

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

        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();
        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("Email", "test@test.com"));
        params.add(new AbstractMap.SimpleEntry("PhoneNumber", "07123432"));
        params.add(new AbstractMap.SimpleEntry("Name", "hehehe"));
        params.add(new AbstractMap.SimpleEntry("Website", "http://shite.com"));
        params.add(new AbstractMap.SimpleEntry("Description", "fuck ytou"));
        params.add(new AbstractMap.SimpleEntry("Location", "farfir"));
        PostData postData = new PostData("c=seller&a=create", params);

        try {
            String result = task.execute(postData).get();
            System.out.println(result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        RemoteSellerRepository repo = new RemoteSellerRepository();
        Seller seller = repo.getSellerById(1);
        System.out.println(seller.getName());
        List<Seller> sellers = repo.getAllSellers();
        System.out.println(sellers.size());
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
