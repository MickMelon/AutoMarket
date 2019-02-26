package com.mickmelon.carshare.core;

public class Advert {
    private int _advertId;
    private int _sellerId;
    private String _vehicleReg;
    private String _description;
    private String _location;
    private double _price;

    public Advert(int advertId, int sellerId, String vehicleReg, String description, String location, double price) {
        _advertId = advertId;
        _sellerId = sellerId;
        _vehicleReg = vehicleReg;
        _description = description;
        _location = location;
        _price = price;
    }

    public int getAdvertId() { return _advertId; }

    public String getVehicleReg() { return _vehicleReg; }
}
