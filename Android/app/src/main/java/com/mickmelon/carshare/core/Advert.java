package com.mickmelon.carshare.core;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Advert {
    private int _advertId;
    private String _vehicleReg;
    private String _description;
    private double _price;
    private int _sellerId;
    private String _imageUrl;
    private Bitmap _imageBitmap;

    public Advert(int advertId, String vehicleReg, String description, double price, int sellerId, String imageUrl) {
        _advertId = advertId;
        _vehicleReg = vehicleReg;
        _description = description;
        _price = price;
        _sellerId = sellerId;
        _imageUrl = imageUrl;
    }

    public int getAdvertId() { return _advertId; }

    public int getSellerId() { return _sellerId; }

    public String getVehicleReg() { return _vehicleReg; }

    public void setVehicleReg(String vehicleReg) { _vehicleReg = vehicleReg; }

    public String getDescription() { return _description; }

    public void setDescription(String description) { _description = description; }

    public double getPrice() { return _price; }

    public void setPrice(double price) { _price = price; }

    public String getImageUrl() { return _imageUrl; }

    public void setImageUrl(String imageUrl) { _imageUrl = imageUrl; }

    public Bitmap getImageBitmap() { return _imageBitmap; }

    public void setImageBitmap(Bitmap image) { _imageBitmap = image; }

    public static Advert fromJson(JSONObject json) {
        try {
            int advertId = json.getInt("ID");
            String vehicleReg = json.getString("VehicleReg");
            String description = json.getString("Description");
            double price = json.getDouble("Price");
            int sellerId = json.getInt("SellerID");
            String imageUrl = json.getString("ImageURL");

            return new Advert(advertId, vehicleReg, description, price, sellerId, imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
