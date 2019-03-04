package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerRepository {
    private List<Seller> _sellers;

    public SellerRepository() {
        _sellers = new ArrayList<Seller>();
/*
        _sellers.add(new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar"));
        _sellers.add(new Seller(2, "2@2.com", "02", "Seller2", "www.2.com", "Description2", "Arbroath"));
        _sellers.add(new Seller(3, "3@3.com", "03", "Seller3", "www.3.com", "Description3", "Dundee"));*/
    }

    public Seller getSellerById(int sellerId) {
        for (Seller seller : _sellers) {
            if (seller.getSellerId() == sellerId)
                return seller;
        }

        return null;
    }
}
