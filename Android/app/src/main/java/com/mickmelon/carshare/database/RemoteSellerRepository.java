package com.mickmelon.carshare.database;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mickmelon.carshare.core.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RemoteSellerRepository implements ISellerRepository {

    public List<Seller> getAllSellers() {
        return null;
    }

    public Seller getSellerById(int id) {
        final Seller[] seller = {null};

        RestClient.get("c=seller&a=read&id=" + id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                    int id = firstEvent.getInt("ID");
                    String email = firstEvent.getString("Email");
                    String phoneNumber = firstEvent.getString("PhoneNumber");
                    String name = firstEvent.getString("Name");
                    String website = firstEvent.getString("Website");
                    String description = firstEvent.getString("Description");
                    String location = firstEvent.getString("Location");

                    seller[0] = new Seller(id, email, phoneNumber, name, website, description, location, "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return seller[0];
    }

    @Override
    public Seller getSellerByEmail(String email) {
        return null;
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
