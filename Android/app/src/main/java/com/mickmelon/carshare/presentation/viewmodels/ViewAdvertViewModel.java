package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

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
     * The image bitmap associated with the advert
     */
    private MutableLiveData<Bitmap> _imageBitmap;

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
     * Get the advert's image
     * @return Advert image bitmap
     */
    public LiveData<Bitmap> getImageBitmap() {
        if (_imageBitmap == null) {
            loadImageBitmap();
        }

        return _imageBitmap;
    }

    /**
     * Gets the seller associated with the advert.
     * @return The seller.
     */
    public LiveData<Seller> getSeller() {
        if (_advert != null) {
            int advertSellerId = _advert.getValue().getSellerId();

            // If the current seller is not null, check to see if the ID matches the advert.sellerID
            if (_seller != null) {
                int currentSellerId = _seller.getValue().getSellerId();

                // If it doesn't match, load the seller from the database.
                // If it did match, the code will return the currently stored seller.
                if (advertSellerId != currentSellerId) {
                    _seller = new MutableLiveData<>();
                    loadSeller(advertSellerId);
                }
            } else {
                // If the seller is null, it hasn't been loaded yet, so load the one corresponding
                // to the advert.sellerID
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

    /**
     * Loads the advert image bitmap from the database
     */
    private void loadImageBitmap() {
        if (_advert != null) {
            _imageBitmap = new MutableLiveData<>();
            _imageBitmap.setValue(_dataAccess.adverts().getAdvertImageBitmap(_advert.getValue()));
        }
    }
}
