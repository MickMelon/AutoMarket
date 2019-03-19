package com.mickmelon.carshare.presentation;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.database.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        mMap = googleMap;

        List<LatLng> list = getRoute("", "");
        List<LatLng> newList = getRoadSnappedRoute(list);

        Polyline polyline = mMap.addPolyline(new PolylineOptions()
            .clickable(true).addAll(newList));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(0)));
    }

    private List<LatLng> getRoadSnappedRoute(List<LatLng> route) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();
        List<LatLng> list = new ArrayList();

        String path = "";

        for (int i = 0; i < route.size(); i++) {
            path += route.get(i).latitude + "," + route.get(i).longitude;
            if ((i + 1) != route.size()) path += "|";
        }

        System.out.println(path +"\n\n\n\n");

        try {
            String result = task.execute("https://roads.googleapis.com/v1/snapToRoads?path=" + path + "%20&interpolate=true&key=AIzaSyB1IZZQIp_KVXDBYFHP2ZNlinY34Igt6nk").get();
            JSONObject json = new JSONObject(result);
            JSONArray jsonArray = json.getJSONArray("snappedPoints");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject point = jsonArray.getJSONObject(i);
                JSONObject location = point.getJSONObject("location");
                Double lat = (Double) location.get("latitude");
                Double lon = (Double) location.get("longitude");
                list.add(new LatLng(lat, lon));
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<LatLng> getRoute(String startAddress, String endAddress) {
        List<LatLng> list = new ArrayList();

        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();

        try {
            String result = task.execute("https://maps.googleapis.com/maps/api/directions/json?origin=43+Ivy+Road+Forfar&destination=Balfield+Road+Dundee&key=AIzaSyB1IZZQIp_KVXDBYFHP2ZNlinY34Igt6nk").get();
            JSONObject json = new JSONObject(result);
            JSONArray routes = json.getJSONArray("routes");
            JSONObject route = routes.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs");
            JSONObject leg = legs.getJSONObject(0);
            JSONArray steps = leg.getJSONArray("steps");
            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                JSONObject location = step.getJSONObject("start_location");
                Double lat = (Double) location.get("lat");
                Double lng = (Double) location.get("lng");
                list.add(new LatLng(lat, lng));
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
