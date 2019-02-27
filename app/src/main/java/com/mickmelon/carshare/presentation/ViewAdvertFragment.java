package com.mickmelon.carshare.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;

public class ViewAdvertFragment extends Fragment {
    private DataAccess _dataAccess;

    private TextView _name;
    private TextView _description;
    private TextView _price;
    private ImageView _image;

    private TextView _sellerName;
    private TextView _sellerDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewadvert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _dataAccess = DataAccess.getInstance();

        _name = view.findViewById(R.id.textView_Name);
        _description = view.findViewById(R.id.textView_Description);
        _price = view.findViewById(R.id.textView_Price);
        _image = view.findViewById(R.id.imageView_Car);

        _sellerName = view.findViewById(R.id.textView_SellerName);
        _sellerDescription = view.findViewById(R.id.textView_SellerDescription);

        Bundle args = getArguments();
        int advertId = args.getInt("Position");

        Advert advert = _dataAccess.adverts().getAdvertById(advertId);
        if (advert != null) {
            populateView(advert);
        } else {
            // do something ( this shouldn't happen though )
        }

        // Do buttons for email, call, website intents
    }

    private void populateView(Advert advert) {
        _name.setText(advert.getVehicleReg());
        _description.setText(advert.getDescription());
        _price.setText(String.valueOf(advert.getPrice()));

        _sellerName.setText(advert.getSeller().getName());
        _sellerDescription.setText(advert.getSeller().getDescription());
    }
}
