package com.mickmelon.carshare.database;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mickmelon.carshare.core.Advert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RemoteAdvertRepository implements IAdvertRepository {
    public List<Advert> getAllAdverts() {
        /*final List<Advert> adverts = new ArrayList<Advert>();

        RestClient.get("c=advert&a=readall", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray timeline) {
                JSONObject event = null;
                try {
                    for (int i = 0; i < timeline.length(); i++) {
                        event = (JSONObject) timeline.get(i);

                        int id = event.getInt("ID");
                        String vehicleReg = event.getString("VehicleReg");
                        String description = event.getString("Description");
                        double price = event.getDouble("Price");
                        int sellerId = event.getInt("SellerID");

                        adverts.add(new Advert(id, vehicleReg, description, price, sellerId));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return adverts;*/

        return null;
    }

    public Advert getAdvertById(int id) {
        final Advert[] advert = {null};

        RestClient.get("c=advert&a=get&id=" + id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray timeline) {
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                    int id = firstEvent.getInt("ID");
                    String vehicleReg = firstEvent.getString("VehicleReg");
                    String description = firstEvent.getString("Description");
                    double price = firstEvent.getDouble("Price");
                    int sellerId = firstEvent.getInt("SellerID");

                    advert[0] = new Advert(id, vehicleReg, description, price, sellerId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return advert[0];
    }

    public boolean addAdvert(Advert advert) {
        return false;
    }

    public boolean removeAdvert(Advert advert) {
        return false;
    }

    public boolean updateAdvert(Advert advert) {
        return false;
    }
}
