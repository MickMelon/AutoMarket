package com.mickmelon.carshare.database;

public class DataAccess {
    private static DataAccess _instance;

    private IAdvertRepository _advertRepository;
    private ISellerRepository _sellerRepository;

    protected DataAccess() {
        _advertRepository = new RemoteAdvertRepository();
        _sellerRepository = new RemoteSellerRepository();
    }

    public static DataAccess getInstance() {
        if (_instance == null) {
            _instance = new DataAccess();
        }

        return _instance;
    }

    public IAdvertRepository adverts() { return _advertRepository; }

    public ISellerRepository sellers() { return _sellerRepository; }
}