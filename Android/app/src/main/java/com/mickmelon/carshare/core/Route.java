package com.mickmelon.carshare.core;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    private List<LatLng> _latLng;
    private int _distanceMetres;
    private int _durationSeconds;

    public Route(List<LatLng> latLng, int distanceMetres, int durationSeconds) {
        _latLng = latLng;
        _distanceMetres = distanceMetres;
        _durationSeconds = durationSeconds;
    }

    public List<LatLng> getLatLng() { return _latLng; }

    public int getDistanceMetres() { return _distanceMetres; }

    public int getDurationSeconds() { return _durationSeconds; }

    public int getDistanceMiles() {
        return (int) Math.round(_distanceMetres / 1609.344);
    }

    public int getDurationMinutes() {
        return _durationSeconds / 60;
    }
}
