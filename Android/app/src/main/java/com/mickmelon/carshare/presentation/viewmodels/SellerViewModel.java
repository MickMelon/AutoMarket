package com.mickmelon.carshare.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;

public class SellerViewModel extends ViewModel {
    private MutableLiveData<Seller> _seller;

    public LiveData<Seller> getSeller(int id) {
        if (_seller == null || _seller.getValue().getSellerId() != id) {
            _seller = new MutableLiveData<>();
            loadSeller(id);
        }

        return _seller;
    }

    private void loadSeller(int id) {
        DataAccess dataAccess = DataAccess.getInstance();
        _seller.setValue(dataAccess.sellers().getSellerById(id));
    }
}
