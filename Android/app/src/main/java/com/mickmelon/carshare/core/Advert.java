package com.mickmelon.carshare.core;

import org.json.JSONException;
import org.json.JSONObject;

public class Advert {
    private int _advertId;
    private String _vehicleReg;
    private String _description;
    private double _price;
    private int _sellerId;

    public Advert(int advertId, String vehicleReg, String description, double price, int sellerId) {
        _advertId = advertId;
        _vehicleReg = vehicleReg;
        _description = description;
        _price = price;
        _sellerId = sellerId;
    }

    public int getAdvertId() { return _advertId; }

    public int getSellerId() { return _sellerId; }

    public String getVehicleReg() { return _vehicleReg; }

    public void setVehicleReg(String vehicleReg) { _vehicleReg = vehicleReg; }

    public String getDescription() { return _description; }

    public void setDescription(String description) { _description = description; }

    public double getPrice() { return _price; }

    public void setPrice(double price) { _price = price; }

    public static Advert fromJson(JSONObject json) {
        try {
            int advertId = json.getInt("ID");
            String vehicleReg = json.getString("VehicleReg");
            String description = json.getString("Description");
            double price = json.getDouble("Price");
            int sellerId = json.getInt("SellerID");

            return new Advert(advertId, vehicleReg, description, price, sellerId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
