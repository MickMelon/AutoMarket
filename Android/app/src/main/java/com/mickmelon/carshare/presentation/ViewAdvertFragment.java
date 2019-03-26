package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.presentation.viewmodels.ViewAdvertViewModel;
import com.mickmelon.carshare.util.FragmentHelper;

/**
 * The fragment for controlling the ViewAdvert layout.
 */
public class ViewAdvertFragment extends Fragment {
    /**
     * Called when the fragment is created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewadvert, container, false);
    }

    /**
     * Called after the fragment is created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        populateLayout(view);
    }

    /**
     * Populates the layout with the details from the advert that should be specified in the
     * bundle arguments.
     * @param view The view passed to onViewCreated.
     */
    private void populateLayout(View view) {
        // Get the TextViews.
        TextView name = view.findViewById(R.id.textView_Name);
        TextView description = view.findViewById(R.id.textView_Description);
        TextView price = view.findViewById(R.id.textView_Price);
        TextView sellerName = view.findViewById(R.id.textView_SellerName);

        // Get the arguments that should be passed to this fragment.
        Bundle args = getArguments();
        int advertId = args.getInt("Position");

        // Set these values like this so that they can be used within the observe functions.
        final String[] reg = new String[1];

        // Get the ViewAdvertViewModel
        ViewAdvertViewModel viewModel = ViewModelProviders.of(this).get(ViewAdvertViewModel.class);

        // Setup the advert observer.
        LiveData<Advert> advertLiveData = viewModel.getAdvert(advertId);
        advertLiveData.observe(this, advert -> {
            name.setText(advert.getVehicleReg());
            description.setText(advert.getDescription());
            price.setText(String.valueOf(advert.getPrice()));

            reg[0] = advert.getVehicleReg();
        });

        // Setup the seller observer.
        viewModel.getSeller().observe(this, seller -> {
            sellerName.setText(seller.getName());
        });

        setupSellerButton(view, advertLiveData.getValue().getSellerId());
    }

    /**
     * Sets up the seller button. When the seller button is clicked, the user will be taken to the
     * appropriate seller view.
     * @param view The view passed to onViewCreated.
     * @param sellerId The Seller ID.
     */
    private void setupSellerButton(View view, int sellerId) {
        Button sellerButton = view.findViewById(R.id.button_Seller);

        sellerButton.setOnClickListener(v -> {
            SellerFragment sellerFragment = new SellerFragment();
            Bundle sellerArgs = new Bundle();
            sellerArgs.putInt("SellerId", sellerId);
            sellerFragment.setArguments(sellerArgs);
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), sellerFragment, true);
        });
    }
}