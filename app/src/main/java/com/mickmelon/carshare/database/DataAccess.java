package com.mickmelon.carshare.database;

public class DataAccess {
    private static DataAccess _instance;

    private AdvertRepository _advertRepository;

    protected DataAccess() {
        _advertRepository = new AdvertRepository();
    }

    public static DataAccess getInstance() {
        if (_instance == null) {
            _instance = new DataAccess();
        }

        return _instance;
    }

    public AdvertRepository adverts() { return _advertRepository; }
}
