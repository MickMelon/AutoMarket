package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Advert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RemoteAdvertRepository implements IAdvertRepository {
    public List<Advert> getAllAdverts() {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            List<Advert> adverts = new ArrayList<>();

            String result = task.execute("c=advert&a=read").get();
            JSONArray jsonAllAdverts = new JSONArray(result);

            for (int i = 0; i < jsonAllAdverts.length(); i++) {
                JSONObject jsonAdvert = (JSONObject) jsonAllAdverts.get(i);
                adverts.add(Advert.fromJson(jsonAdvert));
            }

            return adverts;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Advert getAdvertById(int id) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            String result = task.execute("c=advert&a=read&id=" + id).get();
            JSONObject jsonAdvert = new JSONObject(result);
            Advert advert = Advert.fromJson(jsonAdvert);
            return advert;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addAdvert(Advert advert) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("ID", advert.getAdvertId()));
        params.add(new AbstractMap.SimpleEntry("VehicleReg", advert.getVehicleReg()));
        params.add(new AbstractMap.SimpleEntry("Description", advert.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Price", advert.getPrice()));
        params.add(new AbstractMap.SimpleEntry("SellerID", advert.getSellerId()));
        PostData postData = new PostData("c=advert&a=create", params);

        try {
            String result = task.execute(postData).get();

            if (result.equals("Advert was created successfully.")) {
                return true;
            }

            return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAdvert(Advert advert) {
        return false;
    }

    public boolean updateAdvert(Advert advert) {
        return false;
    }
}
