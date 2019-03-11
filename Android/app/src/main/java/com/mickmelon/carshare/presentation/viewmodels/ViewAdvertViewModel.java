package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

public class ViewAdvertViewModel extends ViewModel {
    private DataAccess _dataAccess = DataAccess.getInstance();

    private MutableLiveData<Advert> _advert;
    private MutableLiveData<Seller> _seller;

    public LiveData<Advert> getAdvert(int id) {
        if (_advert == null || _advert.getValue().getAdvertId() != id) {
            _advert = new MutableLiveData<>();
            loadAdvert(id);
        }

        return _advert;
    }

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

    private void loadSeller(int id) {
        _seller.setValue(_dataAccess.sellers().getSellerById(id));
    }

    private void loadAdvert(int id) {
        _advert.setValue(_dataAccess.adverts().getAdvertById(id));
    }
}
