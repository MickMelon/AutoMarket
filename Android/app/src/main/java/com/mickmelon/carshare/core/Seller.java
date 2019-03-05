package com.mickmelon.carshare.core;

import org.json.JSONException;
import org.json.JSONObject;

public class Seller {
    private int _sellerId;
    private String _email;
    private String _phoneNumber;
    private String _name;
    private String _website;
    private String _description;
    private String _location;
    private String _password;

    public Seller(int sellerId, String email, String phoneNumber, String name, String website, String description, String location, String password) {
        _sellerId = sellerId;
        _email = email;
        _phoneNumber = phoneNumber;
        _name = name;
        _website = website;
        _description = description;
        _location = location;
        _password = password;
    }

    public int getSellerId() { return _sellerId; }

    public String getEmail() { return _email; }

    public String getPhoneNumber() { return _phoneNumber; }

    public String getName() { return _name; }

    public String getWebsite() { return _website; }

    public String getDescription() { return _description; }

    public String getLocation() { return _location; }

    public String getPassword() { return _password; }

    public static Seller fromJson(JSONObject json) {
        try {
            int sellerId = json.getInt("ID");
            String email = json.getString("Email");
            String phoneNumber = json.getString("PhoneNumber");
            String name = json.getString("Name");
            String website = json.getString("Website");
            String description = json.getString("Description");
            String location = json.getString("Location");

            return new Seller(sellerId, email, phoneNumber, name, website, description, location, "");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}