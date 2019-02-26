package com.mickmelon.carshare.database;

import com.mickmelon.carshare.core.Advert;

import java.util.List;

public interface IAdvertRepository {
    List<Advert> getAllAdverts();
    Advert getAdvertById();
    boolean addAdvert(Advert advert);
    boolean removeAdvert(Advert advert);
    boolean updateAdvert(Advert advert);
}
