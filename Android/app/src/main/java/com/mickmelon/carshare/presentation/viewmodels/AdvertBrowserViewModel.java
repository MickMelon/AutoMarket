package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;

import java.util.List;

public class AdvertBrowserViewModel extends ViewModel {
    private MutableLiveData<List<Advert>> _adverts;

    public LiveData<List<Advert>> getAdverts() {
        if (_adverts == null) {
            _adverts = new MutableLiveData<>();
            loadAdverts();
        }

        return _adverts;
    }

    private void loadAdverts() {
        DataAccess dataAccess = DataAccess.getInstance();
        _adverts.setValue(dataAccess.adverts().getAllAdverts());
    }
}
