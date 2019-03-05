package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RemoteSellerRepository implements ISellerRepository {

    public List<Seller> getAllSellers() {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            List<Seller> sellers = new ArrayList<>();

            String result = task.execute("c=seller&a=read").get();
            JSONArray jsonAllSellers = new JSONArray(result);

            for (int i = 0; i < jsonAllSellers.length(); i++) {
                JSONObject jsonSeller = (JSONObject) jsonAllSellers.get(i);
                sellers.add(Seller.fromJson(jsonSeller));
            }

            return sellers;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Seller getSellerById(int id) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            String result = task.execute("c=seller&a=read&id=" + id).get();
            JSONObject jsonSeller = new JSONObject(result);
            Seller seller = Seller.fromJson(jsonSeller);
            return seller;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Seller getSellerByEmail(String email) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            String result = task.execute("c=seller&a=read&email=" + email).get();
            JSONObject jsonSeller = new JSONObject(result);
            Seller seller = Seller.fromJson(jsonSeller);
            return seller;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSeller(Seller seller) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("Email", seller.getEmail()));
        params.add(new AbstractMap.SimpleEntry("PhoneNumber", seller.getPhoneNumber()));
        params.add(new AbstractMap.SimpleEntry("Name", seller.getName()));
        params.add(new AbstractMap.SimpleEntry("Website", seller.getWebsite()));
        params.add(new AbstractMap.SimpleEntry("Description", seller.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Location", seller.getLocation()));
        params.add(new AbstractMap.SimpleEntry("Password", seller.getPassword()));
        PostData postData = new PostData("c=seller&a=create", params);

        try {
            String result = task.execute(postData).get();

            return result.equals("Seller was created successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSeller(Seller seller) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("ID", seller.getSellerId()));
        params.add(new AbstractMap.SimpleEntry("Email", seller.getEmail()));
        params.add(new AbstractMap.SimpleEntry("PhoneNumber", seller.getPhoneNumber()));
        params.add(new AbstractMap.SimpleEntry("Name", seller.getName()));
        params.add(new AbstractMap.SimpleEntry("Website", seller.getWebsite()));
        params.add(new AbstractMap.SimpleEntry("Description", seller.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Location", seller.getLocation()));
        params.add(new AbstractMap.SimpleEntry("Password", seller.getPassword()));
        PostData postData = new PostData("c=seller&a=update", params);

        try {
            String result = task.execute(postData).get();

            return result.equals("Seller was updated successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSeller(Seller seller) {
        return false;
    }
}
