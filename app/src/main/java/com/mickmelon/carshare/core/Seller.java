package com.mickmelon.carshare.core;

public class Seller {
    private int _sellerId;
    private String _email;
    private String _phoneNumber;
    public String _name;
    public String _website;
    public String _description;

    public Seller(int sellerId, String email, String phoneNumber, String name, String website, String description) {
        _sellerId = sellerId;
        _email = email;
        _phoneNumber = phoneNumber;
        _name = name;
        _website = website;
        _description = description;
    }
}
