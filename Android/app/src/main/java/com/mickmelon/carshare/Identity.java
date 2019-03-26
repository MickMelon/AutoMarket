package com.mickmelon.carshare;

import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

/**
 * Used for user authentication purposes.
 */
public class Identity {
    /**
     * The DataAccess instance used to interact with the database.
     */
    private static DataAccess _dataAccess = DataAccess.getInstance();

    /**
     * The currently logged in user. This will be null if the user isn't logged in.
     */
    private static Seller _currentUser;

    /**
     * Attempts to login the user.
     * @param email The input email address.
     * @param password The input password.
     * @return Whether the user successfully logged in. If it returns false, either the seller does
     *         not exist or the password didn't match.
     */
    public static boolean login(String email, String password) {
        Seller seller = _dataAccess.sellers().getSellerByEmail(email);

        if (seller != null && seller.getPassword().equalsIgnoreCase(password)) {
            _currentUser = seller;
            return true;
        }

        return false;
    }

    /**
     * Attempts to register a user.
     * @param email The desired email.
     * @param name The desired name.
     * @param password The desired password.
     * @param phoneNumber The desired phone number.
     * @param website The desired website.
     * @param location The desired location.
     * @param description The desired description.
     * @return Whether the registration was successful. If it returns false, it means there is
     *         already a Seller associated with the given email.
     */
    public static boolean register(String email, String name, String password, String phoneNumber, String website, String location, String description) {
        Seller existingSeller = _dataAccess.sellers().getSellerByEmail(email);

        if (existingSeller == null) {
            _dataAccess.sellers().addSeller(new Seller(-1, email, phoneNumber, name, website, description, location, password));
            return true;
        }

        return false;
    }

    /**
     * Logs the current user out.
     */
    public static void logout() {
        if (isLoggedIn()) {
            _currentUser = null;
        }
    }

    /**
     * Checks whether the user is logged in.
     * @return Whether the user is logged in.
     */
    public static boolean isLoggedIn() {
        return (_currentUser != null);
    }

    /**
     * Gets the current logged in user.
     * @return Current user or null if none.
     */
    public static Seller getCurrentUser() { return _currentUser; }
}
