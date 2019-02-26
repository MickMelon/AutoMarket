package com.mickmelon.carshare.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.AdvertRepository;

import java.util.List;

public class AdvertBrowserFragment extends Fragment {
    private AdvertRepository _advertRepository;
    private LinearLayout _linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _advertRepository = new AdvertRepository();
        return inflater.inflate(R.layout.fragment_advertbrowser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _linearLayout = view.findViewById(R.id.linear_layout);
        populateAdverts();
    }

    public void populateAdverts() {
        List<Advert> adverts = _advertRepository.getAllAdverts();

        for (Advert advert : adverts) {
            TextView textView = new TextView(getContext());
            textView.setText(advert.getVehicleReg());
            _linearLayout.addView(textView);
        }
    }
}