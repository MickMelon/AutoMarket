package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.ViewModelProviders;
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
import com.mickmelon.carshare.presentation.viewmodels.AdvertBrowserViewModel;

/**
 * The fragment for controlling the advert browser layout.
 */
public class AdvertBrowserFragment extends Fragment {
    /**
     * The advert selected listener used to handle the case when an advert is selected.
     */
    private OnAdvertSelectedListener _advertSelectedListener;


    /**
     * The linear layout that contains the populated adverts.
     */
    private LinearLayout _linearLayout;

    /**
     * Called when the fragment is about to be created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertbrowser, container, false);
    }

    /**
     * Called after the fragment has been created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _linearLayout = view.findViewById(R.id.linear_layout);

        AdvertBrowserViewModel viewModel = ViewModelProviders.of(this).get(AdvertBrowserViewModel.class);
        viewModel.getAdverts().observe(this, adverts -> {
            for (Advert advert : adverts) {
                final int advertId = advert.getAdvertId();
                LinearLayout layout = new LinearLayout(getContext());

                TextView textView = new TextView(getContext());
                textView.setText(advert.getVehicleReg());

                Button button = new Button(getContext());
                button.setText("View " + advert.getAdvertId());
                button.setOnClickListener(v -> _advertSelectedListener.onAdvertSelected(advertId));

                layout.addView(textView);
                layout.addView(button);
                _linearLayout.addView(layout);
            }
        });
    }

    /**
     * Sets the OnAdvertSelectedListener.
     * @param listener The OnAdvertSelectedListener.
     */
    public void setOnAdvertSelectedListener(OnAdvertSelectedListener listener) {
        _advertSelectedListener = listener;
    }

    /**
     * The interface for the advert selected listener.
     */
    public interface OnAdvertSelectedListener {
        void onAdvertSelected(int position);
    }
}