package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;

import java.util.List;

/**
 * The ViewModel for the AdvertBrowser layout.
 */
public class AdvertBrowserViewModel extends ViewModel {
    /**
     * The list of adverts.
     */
    private MutableLiveData<List<Advert>> _adverts;

    /**
     * Gets the list of adverts.
     * @return The adverts.
     */
    public LiveData<List<Advert>> getAdverts() {
        if (_adverts == null) {
            _adverts = new MutableLiveData<>();
            loadAdverts();
        }

        return _adverts;
    }

    /**
     * Loads the adverts from the database.
     */
    private void loadAdverts() {
        DataAccess dataAccess = DataAccess.getInstance();
        List<Advert> adverts = dataAccess.adverts().getAllAdverts();

        for (Advert advert : adverts) {
            Bitmap image = dataAccess.adverts().getAdvertImageBitmap(advert);
            advert.setImageBitmap(image);
        }

        _adverts.setValue(adverts);
    }
}
