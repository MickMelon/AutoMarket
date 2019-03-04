package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Seller;

import java.util.List;

public interface ISellerRepository {
    List<Seller> getAllSellers();
    Seller getSellerById(int id);
    Seller getSellerByEmail(String email);
    boolean addSeller(Seller seller);
    boolean updateSeller(Seller seller);
    boolean removeSeller(Seller seller);
}
