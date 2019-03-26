package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

/**
 * The ViewModel for the view advert layout.
 */
public class ViewAdvertViewModel extends ViewModel {
    /**
     * The instance of DataAccess for interacting with the database.
     */
    private DataAccess _dataAccess = DataAccess.getInstance();

    /**
     * The advert.
     */
    private MutableLiveData<Advert> _advert;

    /**
     * The seller associated with the advert.
     */
    private MutableLiveData<Seller> _seller;

    /**
     * Gets the advert by the specified ID.
     * @param id The advert ID.
     * @return The advert.
     */
    public LiveData<Advert> getAdvert(int id) {
        if (_advert == null || _advert.getValue().getAdvertId() != id) {
            _advert = new MutableLiveData<>();
            loadAdvert(id);
        }

        return _advert;
    }

    /**
     * Gets the seller associated with the advert.
     * @return The seller.
     */
    public LiveData<Seller> getSeller() {
        if (_advert != null) {
            int advertSellerId = _advert.getValue().getSellerId();
            if (_seller != null) {
                int currentSellerId = _seller.getValue().getSellerId();
                if (advertSellerId != currentSellerId) {
                    _seller = new MutableLiveData<>();
                    loadSeller(advertSellerId);
                }
            } else {
                _seller = new MutableLiveData<>();
                loadSeller(advertSellerId);
            }
        }

        return _seller;
    }

    /**
     * Loads a seller from the database.
     * @param id Seller ID
     */
    private void loadSeller(int id) {
        _seller.setValue(_dataAccess.sellers().getSellerById(id));
    }

    /**
     * Loads an advert from the database.
     * @param id The advert ID.
     */
    private void loadAdvert(int id) {
        _advert.setValue(_dataAccess.adverts().getAdvertById(id));
    }
}
