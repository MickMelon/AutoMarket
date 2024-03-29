package com.mickmelon.carshare.database;

import android.graphics.Bitmap;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.core.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Used to interact with the advert table in the database through the PHP API.
 */
public class RemoteAdvertRepository implements IAdvertRepository {
    /**
     * Gets all the adverts from the database.
     * @return A list of all the adverts.
     */
    public List<Advert> getAllAdverts() {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            List<Advert> adverts = new ArrayList<>();

            String result = task.execute(Constants.PHP_SERVER_URL + "?c=advert&a=read").get();
            JSONObject json = new JSONObject(result);
            JSONArray jsonAllAdverts = json.getJSONArray("Adverts");

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
            String result = task.execute(Constants.PHP_SERVER_URL + "?c=advert&a=read&id=" + id).get();
            JSONObject json = new JSONObject(result);
            JSONObject jsonAdvert = json.getJSONObject("Advert");
            Advert advert = Advert.fromJson(jsonAdvert);
            return advert;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the bitmap image for the given advert
     * @param advert The advert
     * @return Bitmap image
     */
    public Bitmap getAdvertImageBitmap(Advert advert) {
        HttpClient.HttpGetImageAsyncTask imageTask = new HttpClient.HttpGetImageAsyncTask();
        Bitmap bitmap = null;
        try {
            bitmap = imageTask.execute(advert.getImageUrl()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }

    /**
     * Adds a bitmap image for an advert
     * @param advertId The advert ID
     * @param bitmap The bitmap image to be added to the advert
     * @return Whether it was successful
     */
    public boolean addAdvertImageBitmap(int advertId, Bitmap bitmap) {
        HttpClient.HttpPostImageAsyncTask task = new HttpClient.HttpPostImageAsyncTask();
        List<AbstractMap.SimpleEntry> params = new ArrayList<>();

        params.add(new AbstractMap.SimpleEntry("image", bitmap));
        params.add(new AbstractMap.SimpleEntry("advertId", advertId));
        PostData postData = new PostData(Constants.PHP_SERVER_URL + "?c=advert&a=upload_image", params);

        try {
            HttpResult result = task.execute(postData).get();
            return result.getResponseCode() == 200;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Adds a new advert to the database.
     * @param advert The Advert to be added.
     * @return Whether the advert was added successfully.
     */
    public int addAdvert(Advert advert) {
        HttpClient.HttpPostAsyncTask task = new HttpClient.HttpPostAsyncTask();

        List<AbstractMap.SimpleEntry> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry("VehicleReg", advert.getVehicleReg()));
        params.add(new AbstractMap.SimpleEntry("Description", advert.getDescription()));
        params.add(new AbstractMap.SimpleEntry("Price", advert.getPrice()));
        params.add(new AbstractMap.SimpleEntry("SellerID", advert.getSellerId()));
        params.add(new AbstractMap.SimpleEntry("ImageURL", advert.getImageUrl()));
        PostData postData = new PostData(Constants.PHP_SERVER_URL + "?c=advert&a=create", params);

        try {
            HttpResult httpResult = task.execute(postData).get();

            if (httpResult.getResponseCode() == 200) {
                String result = httpResult.getResult();
                JSONObject json = new JSONObject(result);
                int articleId = json.getInt("ArticleID");

                return articleId;
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return -1;
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
        params.add(new AbstractMap.SimpleEntry("ImageURL", advert.getImageUrl()));
        PostData postData = new PostData(Constants.PHP_SERVER_URL + "?c=advert&a=update", params);

        try {
            HttpResult httpResult = task.execute(postData).get();

            return httpResult.getResponseCode() == 200;
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
