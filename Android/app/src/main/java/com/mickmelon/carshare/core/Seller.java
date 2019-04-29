package com.mickmelon.carshare.core;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Holds information for each Seller
 */
public class Seller {
    /**
     * The Seller ID
     */
    private int _sellerId;

    /**
     * The seller's email address
     */
    private String _email;

    /**
     * The seller's phone number
     */
    private String _phoneNumber;

    /**
     * The seller's name
     */
    private String _name;

    /**
     * The seller's website URL
     */
    private String _website;

    /**
     * The seller's description
     */
    private String _description;

    /**
     * The seller's address
     */
    private String _location;

    /**
     * The seller's password
     */
    private String _password;

    /**
     * Initializes a new instance of the Seller class
     * @param sellerId The seller ID
     * @param email The seller's email
     * @param phoneNumber The seller's phone number
     * @param name The seller's name
     * @param website The seller's website URL
     * @param description The seller's description
     * @param location The seller's location
     * @param password The seller's password
     */
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

    /**
     * Gets the seller ID
     */
    public int getSellerId() { return _sellerId; }

    /**
     * Gets the seller's email
     */
    public String getEmail() { return _email; }

    /**
     * Gets the seller's phone number
     */
    public String getPhoneNumber() { return _phoneNumber; }

    /**
     * Gets the seller's name
     */
    public String getName() { return _name; }

    /**
     * Gets the seller's website URL
     */
    public String getWebsite() { return _website; }

    /**
     * Gets the seller's description
     */
    public String getDescription() { return _description; }

    /**
     * Gets the seller's location address
     */
    public String getLocation() { return _location; }

    /**
     * Gets the seller's password
     */
    public String getPassword() { return _password; }

    /**
     * Creates a new seller from the JSON returned from the web server
     */
    public static Seller fromJson(JSONObject json) {
        try {
            int sellerId = json.getInt("ID");
            String email = json.getString("Email");
            String phoneNumber = json.getString("PhoneNumber");
            String name = json.getString("Name");
            String website = json.getString("Website");
            String description = json.getString("Description");
            String location = json.getString("Location");
            String password = json.getString("Password");

            return new Seller(sellerId, email, phoneNumber, name, website, description, location, password);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}