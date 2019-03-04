package com.mickmelon.carshare;

import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

public class Identity {
    private static DataAccess _dataAccess = DataAccess.getInstance();
    private static Seller _currentUser;

    public static boolean login(String email, String password) {
        Seller seller = _dataAccess.sellers().getSellerByEmail(email);
        if (seller != null && seller.getPassword().equalsIgnoreCase(password)) {
            _currentUser = seller;
            return true;
        }

        return false;
    }

    public static boolean register(String email, String name, String password, String phoneNumber, String website, String location, String description) {
        // Call dataaccess to add the seller
        Seller existingSeller = _dataAccess.sellers().getSellerByEmail(email);
        if (existingSeller == null) {
            _dataAccess.sellers().addSeller(new Seller(-1, email, phoneNumber, name, website, description, location, password));
            return true;
        }

        return false;
    }

    public static boolean isLoggedIn() {
        return (_currentUser == null);
    }
}
