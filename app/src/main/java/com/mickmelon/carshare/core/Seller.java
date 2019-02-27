package com.mickmelon.carshare.core;

public class Seller {
    private int _sellerId;
    private String _email;
    private String _phoneNumber;
    private String _name;
    private String _website;
    private String _description;
    private String _location;

    public Seller(int sellerId, String email, String phoneNumber, String name, String website, String description, String location) {
        _sellerId = sellerId;
        _email = email;
        _phoneNumber = phoneNumber;
        _name = name;
        _website = website;
        _description = description;
        _location = location;
    }

    public int getSellerId() { return _sellerId; }

    public String getEmail() { return _email; }

    public String getPhoneNumber() { return _phoneNumber; }

    public String getName() { return _name; }

    public String getWebsite() { return _website; }

    public String getDescription() { return _description; }

    public String getLocation() { return _location; }
}
