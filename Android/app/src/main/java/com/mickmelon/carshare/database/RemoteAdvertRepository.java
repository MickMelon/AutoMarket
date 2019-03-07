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
    /**
     * Gets all the adverts from the database.
     * @return A list of all the adverts.
     */
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

    /**
     * Gets a single advert by its ID.
     * @param id The Advert ID
     * @return The Advert or null if none found.
     */
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

    /**
     * Adds a new advert to the database.
     * @param advert The Advert to be added.
     * @return Whether the advert was added successfully.
     */
    public boolean addAdvert(Advert advert) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("VehicleReg", advert.getVehicleReg()));
        params.add(new AbstractMap.SimpleEntry("Description", advert.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Price", advert.getPrice()));
        params.add(new AbstractMap.SimpleEntry("SellerID", advert.getSellerId()));
        PostData postData = new PostData("c=advert&a=create", params);

        try {
            String result = task.execute(postData).get();

            return result.equals("Advert was created successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an advert in the database.
     * @param advert The Advert to be updated containing the updated values.
     * @return Whether the advert was updated successfully.
     */
    public boolean updateAdvert(Advert advert) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("ID", advert.getAdvertId()));
        params.add(new AbstractMap.SimpleEntry("VehicleReg", advert.getVehicleReg()));
        params.add(new AbstractMap.SimpleEntry("Description", advert.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Price", advert.getPrice()));
        params.add(new AbstractMap.SimpleEntry("SellerID", advert.getSellerId()));
        PostData postData = new PostData("c=advert&a=update", params);

        try {
            String result = task.execute(postData).get();

            if (result.equals("Advert was updated successfully.")) {
                return true;
            }

            return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes an advert from the database.
     * @param advert The Advert to be removed.
     * @return Whether the advert was removed successfully.
     */
    public boolean removeAdvert(Advert advert) {
        return false;
    }
}
