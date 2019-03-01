package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.core.Seller;

import java.util.ArrayList;
import java.util.List;

public class AdvertRepository {
    private List<Advert> _adverts;

    public AdvertRepository() {
        _adverts = new ArrayList<Advert>();

        /*_adverts.add(new Advert(1, "SA07ENW", "Volkswagen Golf Gt Tdi",2000,
                new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar")));
        _adverts.add(new Advert(2, "YE05JYF", "Volkswagen Golf Tdi", 1599,
                new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar")));
        _adverts.add(new Advert(3, "SP07NMA", "Vauxhall Zafira", 1355.33,
                new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar")));
        _adverts.add(new Advert(4, "SM53YXN", "Vauxhall Corsa", 2999.99,
                new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar")));
        _adverts.add(new Advert(5, "ST06XHJ", "Ford Fiesta", 500,
                new Seller(1, "1@1.com", "01", "Seller1", "www.1.com", "Description1", "Forfar")));*/
    }

    public List<Advert> getAllAdverts() {
        return _adverts;
    }

    public Advert getAdvertById(int advertId) {
        for (Advert advert : _adverts) {
            if (advert.getAdvertId() == advertId)
                return advert;
        }

        return null;
    }

    public boolean addAdvert(int advertId, String vehicleReg, String description, double price, Seller seller) {
        if (getAdvertById(advertId) != null) {
            return false;
        }

        //_adverts.add(new Advert(advertId, vehicleReg, description, price, seller));
        return true;
    }

    public boolean removeAdvert(int advertId) {
        Advert advert = getAdvertById(advertId);
        if (advert == null) {
            return false;
        }

        _adverts.remove(advert);
        return true;
    }
}
