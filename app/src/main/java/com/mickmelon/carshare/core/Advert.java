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

    public int getSellerId() { return _sellerId; }

    public String getVehicleReg() { return _vehicleReg; }

    public void setVehicleReg(String vehicleReg) { _vehicleReg = vehicleReg; }

    public String getDescription() { return _description; }

    public void setDescription(String description) { _description = description; }

    public String getLocation() { return _location; }

    public void setLocation(String location) { _location = location; }

    public double getPrice() { return _price; }

    public void setPrice(double price) { _price = price; }
}
