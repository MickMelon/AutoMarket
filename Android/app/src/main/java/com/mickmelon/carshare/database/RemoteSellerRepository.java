package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        return false;
    }

    public boolean updateSeller(Seller seller) {
        return false;
    }

    public boolean removeSeller(Seller seller) {
        return false;
    }
}
