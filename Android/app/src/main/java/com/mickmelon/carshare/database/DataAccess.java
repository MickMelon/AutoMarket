package com.mickmelon.carshare.database;

/**
 * The class used for interacting with the database.
 */
public class DataAccess {
    /**
     * The singleton instance of this class.
     */
    private static DataAccess _instance;

    /**
     * The advert repository.
     */
    private IAdvertRepository _advertRepository;

    /**
     * The seller repository.
     */
    private ISellerRepository _sellerRepository;

    /**
     * Creates a new instance of the DataAccess. Set to private so that it can only be called
     * from within this class to ensure that there is only ever one instance of it (singleton)
     */
    private DataAccess() {
        _advertRepository = new RemoteAdvertRepository();
        _sellerRepository = new RemoteSellerRepository();
    }

    /**
     * Gets the singleton DataAccess object.
     * @return The DataAccess object.
     */
    public static DataAccess getInstance() {
        if (_instance == null) {
            _instance = new DataAccess();
        }

        return _instance;
    }

    /**
     * The Adverts repository.
     */
    public IAdvertRepository adverts() { return _advertRepository; }

    /**
     * The Sellers repository.
     */
    public ISellerRepository sellers() { return _sellerRepository; }
}