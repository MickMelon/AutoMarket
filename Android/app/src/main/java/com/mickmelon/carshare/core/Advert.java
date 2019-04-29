package com.mickmelon.carshare.core;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The model that holds all the information about an advert.
 */
public class Advert {
    /**
     * The Advert ID.
     */
    private int _advertId;

    /**
     * The vehicle registration number.
     */
    private String _vehicleReg;

    /**
     * The advert description.
     */
    private String _description;

    /**
     * The price to be displayed on the advert.
     */
    private double _price;

    /**
     * The ID of the seller that created the advert.
     */
    private int _sellerId;

    /**
     * The URL to the image on the server.
     */
    private String _imageUrl;

    /**
     * The Bitmap object of the image.
     */
    private Bitmap _imageBitmap;

    /**
     * Initialize a new instance of the Advert class.
     * @param advertId The Advert ID
     * @param vehicleReg The vehicle registration number
     * @param description The advert description
     * @param price The advert price
     * @param sellerId The seller ID
     * @param imageUrl The image URL
     */
    public Advert(int advertId, String vehicleReg, String description, double price, int sellerId, String imageUrl) {
        _advertId = advertId;
        _vehicleReg = vehicleReg;
        _description = description;
        _price = price;
        _sellerId = sellerId;
        _imageUrl = imageUrl;
    }

    /**
     * Gets the advert ID
     */
    public int getAdvertId() { return _advertId; }

    /**
     * Gets the seller ID
     */
    public int getSellerId() { return _sellerId; }

    /**
     * Gets the vehicle reg
     */
    public String getVehicleReg() { return _vehicleReg; }

    /**
     * Gets the advert description
     */
    public String getDescription() { return _description; }

    /**
     * Sets the advert description
     */
    public void setDescription(String description) { _description = description; }

    /**
     * Gets the advert price
     */
    public double getPrice() { return _price; }

    /**
     * Gets the image url
     */
    public String getImageUrl() { return _imageUrl; }

    /**
     * Gets the image bitmap object
     */
    public Bitmap getImageBitmap() { return _imageBitmap; }

    /**
     * Sets the image bitmap object
     */
    public void setImageBitmap(Bitmap image) { _imageBitmap = image; }

    /**
     * Creates an advert from the JSON data (returned by the PHP server)
     */
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
