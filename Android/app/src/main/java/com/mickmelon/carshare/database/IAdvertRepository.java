package com.mickmelon.carshare.database;

import android.graphics.Bitmap;

import com.mickmelon.carshare.core.Advert;

import java.util.List;

public interface IAdvertRepository {
    List<Advert> getAllAdverts();
    Advert getAdvertById(int id);
    Bitmap getAdvertImageBitmap(Advert advert);
    boolean addAdvertImageBitmap(Advert advert, Bitmap bitmap);
    boolean addAdvert(Advert advert);
    boolean removeAdvert(Advert advert);
    boolean updateAdvert(Advert advert);
}
