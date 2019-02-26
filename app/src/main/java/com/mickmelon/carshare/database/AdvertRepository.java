package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Advert;

import java.util.ArrayList;
import java.util.List;

public class AdvertRepository {
    private List<Advert> _adverts;

    public AdvertRepository() {
        _adverts = new ArrayList<Advert>();

        _adverts.add(new Advert(1, 1, "SA07ENW", "Volkswagen Golf Gt Tdi", "Dundee", 2000));
        _adverts.add(new Advert(2, 1, "YE05JYF", "Volkswagen Golf Tdi", "Dundee", 1599));
        _adverts.add(new Advert(3, 2, "SP07NMA", "Vauxhall Zafira", "Aberdeen", 1355.33));
        _adverts.add(new Advert(4, 3, "SM53YXN", "Vauxhall Corsa", "Forfar", 2999.99));
        _adverts.add(new Advert(5, 4, "ST06XHJ", "Ford Fiesta", "Arbroath", 500));
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

    public boolean addAdvert(int advertId, int sellerId, String vehicleReg, String description, String location, double price) {
        if (getAdvertById(advertId) != null) {
            return false;
        }

        _adverts.add(new Advert(advertId, sellerId, vehicleReg, description, location, price));
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
