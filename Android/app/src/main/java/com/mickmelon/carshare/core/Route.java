package com.mickmelon.carshare.core;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Holds information about a route
 */
public class Route {
    /**
     * The list of co-ordinates on the route.
     */
    private List<LatLng> _latLng;

    /**
     * How long the route is in metres.
     */
    private int _distanceMetres;

    /**
     * How long the route will take in seconds.
     */
    private int _durationSeconds;

    /**
     * Initializes a new instance of the Route class.
     * @param latLng The list of co-ordinates on the route
     * @param distanceMetres The distance of the route
     * @param durationSeconds The duration of the route
     */
    public Route(List<LatLng> latLng, int distanceMetres, int durationSeconds) {
        _latLng = latLng;
        _distanceMetres = distanceMetres;
        _durationSeconds = durationSeconds;
    }

    /**
     * Gets all the route co-ordinates
     */
    public List<LatLng> getLatLng() { return _latLng; }

    /**
     * Gets the distance in miles
     */
    public int getDistanceMiles() {
        return (int) Math.round(_distanceMetres / 1609.344);
    }

    /**
     * Gets the duration in minutes
     */
    public int getDurationMinutes() {
        return _durationSeconds / 60;
    }
}
