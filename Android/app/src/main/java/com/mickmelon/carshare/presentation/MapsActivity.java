package com.mickmelon.carshare.presentation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.database.HttpClient;
import com.mickmelon.carshare.core.Route;
import com.mickmelon.carshare.util.ToastHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap _map;
    private String _userAddress;
    private String _sellerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Set the arguments
        Bundle args = getIntent().getExtras();
        _sellerAddress = args.getString("SellerAddress");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            handlePermissionsNotGranted();
        } else {
            ToastHelper.showToast(getApplicationContext(), "Permissions granted.");

            // Location Manager
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                handleGpsNotEnabled();
                return;
            }

            // Setup LocationListener
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);

            // If there has been no location updates, get the cached location.
            if (_userAddress == null) {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                setAddressFromLocation(lastLocation);
            }

            displayRouteFromUserToSeller();
        }
    }

    /**
     * Called when the location permissions have not been granted by the user.
     */
    private void handlePermissionsNotGranted() {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        ToastHelper.showToast(getApplicationContext(), "No permissions granted");
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    /**
     * Called when GPS is not enabled on the user's device.
     */
    private void handleGpsNotEnabled() {

    }

    /**
     * Displays the route from the user to seller.
     */
    private void displayRouteFromUserToSeller() {
        Route route = getPolyRoute(_userAddress, _sellerAddress);

        Polyline polyline = _map.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(5)
                .color(Color.BLUE)
                .geodesic(true)
                .addAll(route.getLatLng()));


        _map.moveCamera(CameraUpdateFactory.newLatLng(route.getLatLng().get(0)));
        _map.setTrafficEnabled(true);

        Marker marker = _map.addMarker(new MarkerOptions()
                .position(route.getLatLng().get(route.getLatLng().size() - 1))
                .title("Destination is " + route.getDistanceMiles() + " miles away.")
                .snippet("It will take around " + route.getDurationMinutes() + " minutes."));
        marker.showInfoWindow();
    }

    /**
     * Generates the PolyRoute for displaying on the Google Map.
     * @param startAddress The starting address.
     * @param endAddress The ending address.
     * @return Route or null if none could be created.
     */
    private Route getPolyRoute(String startAddress, String endAddress) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();
        Route route = null;

        // Make sure the start and end addresses are in valid format for adding into URL.
        // Google maps doesn't like spaces or commas in the address.
        startAddress = startAddress.replace(" ", "+");
        startAddress = startAddress.replace(",", "");
        endAddress = endAddress.replace(" ", "+");
        try {
            String result = task.execute("https://maps.googleapis.com/maps/api/directions/json?origin="
                    + startAddress + "&destination="
                    + endAddress + "&key=AIzaSyB1IZZQIp_KVXDBYFHP2ZNlinY34Igt6nk").get();

            // Get the polyline data
            JSONObject json = new JSONObject(result);
            JSONArray routesArray = json.getJSONArray("routes");
            JSONObject routes = routesArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> latLngList = PolyUtil.decode(encodedString);

            // Get the route time and distance
            JSONArray legsArray = routes.getJSONArray("legs");
            JSONObject leg = legsArray.getJSONObject(0);
            JSONObject distance = leg.getJSONObject("distance");
            int distanceMetres = distance.getInt("value");
            JSONObject duration = leg.getJSONObject("duration");
            int durationSeconds = duration.getInt("value");

            route = new Route(latLngList, distanceMetres, durationSeconds);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return route;
    }

    /**
     * Sets the useraddress depending on GPS location.
     * @param location
     */
    private void setAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();

        _userAddress = address + " " + city;
    }

    /**
     * The custom implementation of the LocationListener used to make updates to the user address
     * variable every time the user changes location.
     */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            setAddressFromLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
