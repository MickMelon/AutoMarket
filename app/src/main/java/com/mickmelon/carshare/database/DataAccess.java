package com.mickmelon.carshare.database;

public class DataAccess {
    private static DataAccess _instance;

    private AdvertRepository _advertRepository;
    private SellerRepository _sellerRepository;

    protected DataAccess() {
        _advertRepository = new AdvertRepository();
        _sellerRepository = new SellerRepository();
    }

    public static DataAccess getInstance() {
        if (_instance == null) {
            _instance = new DataAccess();
        }

        return _instance;
    }

    public AdvertRepository adverts() { return _advertRepository; }

    public SellerRepository sellers() { return _sellerRepository; }
}