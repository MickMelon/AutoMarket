package com.mickmelon.carshare.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;

import java.util.List;

public class AdvertBrowserFragment extends Fragment {
    private OnAdvertSelectedListener _advertSelectedListener;
    private DataAccess _dataAccess;
    private LinearLayout _linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _dataAccess = DataAccess.getInstance();
        return inflater.inflate(R.layout.fragment_advertbrowser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _linearLayout = view.findViewById(R.id.linear_layout);
        populateAdverts();
    }

    public void setOnAdvertSelectedListener(OnAdvertSelectedListener listener) {
        _advertSelectedListener = listener;
    }

    public void populateAdverts() {
        List<Advert> adverts = _dataAccess.adverts().getAllAdverts();

        for (Advert advert : adverts) {
            final int advertId = advert.getAdvertId();
            LinearLayout layout = new LinearLayout(getContext());

            TextView textView = new TextView(getContext());
            textView.setText(advert.getVehicleReg());

            Button button = new Button(getContext());
            button.setText("View " + advert.getAdvertId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _advertSelectedListener.onAdvertSelected(advertId);
                }
            });

            layout.addView(textView);
            layout.addView(button);
            _linearLayout.addView(layout);
        }
    }

    public interface OnAdvertSelectedListener {
        void onAdvertSelected(int position);
    }
}