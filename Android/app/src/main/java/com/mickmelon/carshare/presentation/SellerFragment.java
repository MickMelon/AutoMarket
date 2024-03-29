package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.presentation.viewmodels.SellerViewModel;
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.IntentHelper;

import java.io.IOException;
import java.util.List;

/**
 * The fragment for controlling the Seller layout.
 */
public class SellerFragment extends Fragment implements OnMapReadyCallback {
    /**
     * The MapView that the Google maps will be displayed on with the seller address.
     */
    private MapView _mapView;

    /**
     * The seller's address.
     */
    private String _address;

    /**
     * The name of the MapView bundle key.
     */
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    /**
     * Called when the fragment is created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller, container, false);
    }

    /**
     * Called after the fragment is created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        populateView(view, savedInstanceState);
    }

    /**
     * Populates the layout with the details from the seller that should be specified by the
     * bundle arguments.
     */
    private void populateView(View view, @Nullable Bundle savedInstanceState) {
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

        // Set the address used for displaying seller location on the map.
        _address = sellerLiveData.getValue().getLocation();

        setupButtons(view, sellerLiveData);
        setupMapView(view ,savedInstanceState);
    }

    /**
     * Sets up the buttons used on the layout.
     * @param view The view passed to onViewCreated
     * @param sellerLiveData The live data from the seller view model.
     */
    private void setupButtons(View view, LiveData<Seller> sellerLiveData) {
        Button callButton = view.findViewById(R.id.button_Call);
        Button emailButton = view.findViewById(R.id.button_Email);
        Button websiteButton = view.findViewById(R.id.button_Website);
        Button mapButton = view.findViewById(R.id.button_Map);

        callButton.setOnClickListener(v -> IntentHelper.makePhoneCall(getActivity(), sellerLiveData.getValue().getPhoneNumber()));
        emailButton.setOnClickListener(v -> IntentHelper.composeEmail(getActivity(), sellerLiveData.getValue().getEmail(), "Enquiry", "I am interested in buying your vehicle."));
        websiteButton.setOnClickListener(v -> IntentHelper.openWebPage(getActivity(), sellerLiveData.getValue().getWebsite()));
        mapButton.setOnClickListener(v -> {
            Bundle mapArgs = new Bundle();
            mapArgs.putString("SellerAddress", _address);
            ActivityHelper.showActivity(getContext(), MapsActivity.class, false, mapArgs);
        });
    }

    /**
     * Sets up the google maps view.
     * @param view The view passed to onViewCreated.
     * @param savedInstanceState The bundle passed to onViewCreated.
     */
    private void setupMapView(View view, @Nullable Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        _mapView = view.findViewById(R.id.mapView);
        _mapView.onCreate(mapViewBundle);
        _mapView.getMapAsync(this);
    }

    /**
     * Called after onCreate and after onStart.
     */
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

    /**
     * Called when the fragment has resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        _mapView.onResume();
    }

    /**
     * Called when the fragment has started, after onCreate.
     */
    @Override
    public void onStart() {
        super.onStart();
        _mapView.onStart();
    }

    /**
     * Called when the fragment has stopped.
     */
    @Override
    public void onStop() {
        super.onStop();
        _mapView.onStop();
    }

    /**
     * Called when the GoogleMap is ready.
     * @param map The GoogleMap.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng location = getLocationFromAddress(getContext(), _address);

        Marker marker = map.addMarker(new MarkerOptions()
            .position(location)
            .title(_address));
        marker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    /**
     * Called when the fragment is paused.
     */
    @Override
    public void onPause() {
        _mapView.onPause();
        super.onPause();
    }

    /**
     * Called when the fragment is destroyed.
     */
    @Override
    public void onDestroy() {
        _mapView.onDestroy();
        super.onDestroy();
    }

    /**
     * Called when low on memory.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        _mapView.onLowMemory();
    }

    /**
     * Gets the co-ordinates for the given address.
     * @param context The application context.
     * @param address The address.
     * @return The co-ordinates for the given address.
     */
    private LatLng getLocationFromAddress(Context context, String address) {
        Geocoder coder = new Geocoder(context);
        List<Address> addresses = null;
        LatLng p1 = null;

        try {
            addresses = coder.getFromLocationName(address, 1);
            if (addresses == null) {
                return null;
            }

            Address location = addresses.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }
}


