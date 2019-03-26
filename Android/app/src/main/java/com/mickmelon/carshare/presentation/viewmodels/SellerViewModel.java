package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

/**
 * The ViewModel for the seller layout.
 */
public class SellerViewModel extends ViewModel {
    /**
     * The seller.
     */
    private MutableLiveData<Seller> _seller;

    /**
     * Gets a seller for the given ID.
     * @param id The seller ID.
     * @return The seller.
     */
    public LiveData<Seller> getSeller(int id) {
        if (_seller == null || _seller.getValue().getSellerId() != id) {
            _seller = new MutableLiveData<>();
            loadSeller(id);
        }

        return _seller;
    }

    /**
     * Loads a seller from the database.
     * @param id The seller ID.
     */
    private void loadSeller(int id) {
        DataAccess dataAccess = DataAccess.getInstance();
        _seller.setValue(dataAccess.sellers().getSellerById(id));
    }
}
