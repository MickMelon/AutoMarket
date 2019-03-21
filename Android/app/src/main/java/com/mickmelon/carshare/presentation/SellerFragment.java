package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Route;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.HttpClient;
import com.mickmelon.carshare.presentation.viewmodels.SellerViewModel;
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SellerFragment extends Fragment implements OnMapReadyCallback {
    private MapView _mapView;
    private GoogleMap _googleMap;
    private String _address;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView sellerName = view.findViewById(R.id.textView_SellerName);
        TextView location = view.findViewById(R.id.textView_Address);
        TextView phoneNumber = view.findViewById(R.id.textView_PhoneNumber);
        TextView email = view.findViewById(R.id.textView_Email);

        // Get the arguments from the bundle.
        Bundle args = getArguments();
        int sellerId = args.getInt("SellerId");

        // Get the viewmodel.
        SellerViewModel viewModel = ViewModelProviders.of(this).get(SellerViewModel.class);

        // Get the seller and ensure one was found
        LiveData<Seller> sellerLiveData = viewModel.getSeller(sellerId);
        if (sellerLiveData == null) {
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new ViewAdvertFragment(), false);
            return;
        }

        // Set up the observer
        sellerLiveData.observe(this, seller -> {
            sellerName.setText(seller.getName());
            location.setText(seller.getLocation());
            phoneNumber.setText(seller.getPhoneNumber());
            email.setText(seller.getEmail());
        });

        _address = sellerLiveData.getValue().getLocation();

        Button callButton = view.findViewById(R.id.button_Call);
        Button emailButton = view.findViewById(R.id.button_Email);
        Button websiteButton = view.findViewById(R.id.button_Website);
        Button mapButton = view.findViewById(R.id.button_Map);

        callButton.setOnClickListener(v -> makePhoneCall(sellerLiveData.getValue().getPhoneNumber()));
        emailButton.setOnClickListener(v -> composeEmail(sellerLiveData.getValue().getEmail(), "Enquiry"));
        websiteButton.setOnClickListener(v -> openWebPage(sellerLiveData.getValue().getWebsite()));
        mapButton.setOnClickListener(v -> ActivityHelper.showActivity(getContext(), MapsActivity.class));

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        _mapView = view.findViewById(R.id.mapView);
        _mapView.onCreate(mapViewBundle);
        _mapView.getMapAsync(this);

    }

    /**
     * Composes an email and opens the email client ready to be sent.
     * @param address The email address to send to.
     * @param subject The subject of the email.
     */
    private void composeEmail(String address, String subject) {
        String[] addresses = new String[] {address};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "I am interested in buying your vehicle.");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Makes a phone call to the given number.
     * @param phoneNumber The number to call.
     */
    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Opens a web page with the given URL.
     * @param url The URL of the web page to be loaded.
     */
    private void openWebPage(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {

        }
        Uri webPage = Uri.parse("http://" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        _mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        _mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        _mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        _mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Route route = getPolyRoute("43 Ivy Road Forfar", _address);

        Polyline polyline = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(5)
                .color(Color.BLUE)
                .geodesic(true)
                .addAll(route.getLatLng()));


        map.moveCamera(CameraUpdateFactory.newLatLng(route.getLatLng().get(0)));
        map.setTrafficEnabled(true);

        Marker marker = map.addMarker(new MarkerOptions()
                .position(route.getLatLng().get(route.getLatLng().size() - 1))
                .title("Destination is " + route.getDistanceMiles() + " miles away.")
                .snippet("It will take around " + route.getDurationMinutes() + " minutes."));
        marker.showInfoWindow();
    }

    @Override
    public void onPause() {
        _mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        _mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        _mapView.onLowMemory();
    }

    private Route getPolyRoute(String startAddress, String endAddress) {
        HttpClient.HttpGetAsyncTask task = new HttpClient.HttpGetAsyncTask();
        Route route = null;

        startAddress = startAddress.replace(" ", "+");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return route;
    }
}
