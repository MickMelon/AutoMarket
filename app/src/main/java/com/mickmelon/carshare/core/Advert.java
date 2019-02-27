package com.mickmelon.carshare.core;

public class Advert {
    private int _advertId;
    private String _vehicleReg;
    private String _description;
    private double _price;
    private Seller _seller;

    public Advert(int advertId, String vehicleReg, String description, double price, Seller seller) {
        _advertId = advertId;
        _vehicleReg = vehicleReg;
        _description = description;
        _price = price;
        _seller = seller;
    }

    public int getAdvertId() { return _advertId; }

    public Seller getSeller() { return _seller; }

    public String getVehicleReg() { return _vehicleReg; }

    public void setVehicleReg(String vehicleReg) { _vehicleReg = vehicleReg; }

    public String getDescription() { return _description; }

    public void setDescription(String description) { _description = description; }

    public double getPrice() { return _price; }

    public void setPrice(double price) { _price = price; }
}
