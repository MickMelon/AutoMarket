package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.presentation.viewmodels.ViewAdvertViewModel;
import com.mickmelon.carshare.util.FragmentHelper;

public class ViewAdvertFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewadvert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView name = view.findViewById(R.id.textView_Name);
        TextView description = view.findViewById(R.id.textView_Description);
        TextView price = view.findViewById(R.id.textView_Price);

        TextView sellerName = view.findViewById(R.id.textView_SellerName);

        Bundle args = getArguments();
        int advertId = args.getInt("Position");

        final String[] emailAddress = new String[1];
        final String[] reg = new String[1];
        final String[] phoneNumber = new String[1];
        final String[] websiteUrl = new String[1];

        ViewAdvertViewModel viewModel = ViewModelProviders.of(this).get(ViewAdvertViewModel.class);
        LiveData<Advert> advertLiveData = viewModel.getAdvert(advertId);
        advertLiveData.observe(this, advert -> {
            name.setText(advert.getVehicleReg());
            description.setText(advert.getDescription());
            price.setText(String.valueOf(advert.getPrice()));

            reg[0] = advert.getVehicleReg();
        });

        viewModel.getSeller().observe(this, seller -> {
            sellerName.setText(seller.getName());

            emailAddress[0] = seller.getEmail();
            phoneNumber[0] = seller.getPhoneNumber();
            websiteUrl[0] = seller.getWebsite();
        });

        // Do buttons for email, call, website intents
        Button sellerButton = view.findViewById(R.id.button_Seller);

        sellerButton.setOnClickListener(v -> {
            SellerFragment sellerFragment = new SellerFragment();
            Bundle sellerArgs = new Bundle();
            sellerArgs.putInt("SellerId", advertLiveData.getValue().getSellerId());
            sellerFragment.setArguments(sellerArgs);
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), sellerFragment, true);
        });
    }
}