package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.database.DataAccess;
import com.mickmelon.carshare.presentation.viewmodels.ViewAdvertViewModel;

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

        final String[] emailAddress = new String[1];
        final String[] reg = new String[1];
        final String[] phoneNumber = new String[1];
        final String[] websiteUrl = new String[1];

        ViewAdvertViewModel viewModel = ViewModelProviders.of(this).get(ViewAdvertViewModel.class);
        viewModel.getAdvert(advertId).observe(this, advert -> {
            _name.setText(advert.getVehicleReg());
            _description.setText(advert.getDescription());
            _price.setText(String.valueOf(advert.getPrice()));

            reg[0] = advert.getVehicleReg();
        });

        viewModel.getSeller().observe(this, seller -> {
            _sellerName.setText(seller.getName());
            _sellerDescription.setText(seller.getDescription());

            emailAddress[0] = seller.getEmail();
            phoneNumber[0] = seller.getPhoneNumber();
            websiteUrl[0] = seller.getWebsite();
        });

        // Do buttons for email, call, website intents
        Button call = view.findViewById(R.id.button_Call);
        Button email = view.findViewById(R.id.button_Email);
        Button website = view.findViewById(R.id.button_Website);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(phoneNumber[0]);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(emailAddress[0], "Enquiry for " + reg[0]);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(websiteUrl[0]);
            }
        });
    }

    /**
     * Sends an email.
     * @param address The email address to sent to.
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

    /**
     * Populates the advert view with all the required details.
     * @param advert The Advert.
     * @param seller The Seller that posted the Advert.
     */
    private void populateView(Advert advert, Seller seller) {
        _name.setText(advert.getVehicleReg());
        _description.setText(advert.getDescription());
        _price.setText(String.valueOf(advert.getPrice()));

        _sellerName.setText(seller.getName());
        _sellerDescription.setText(seller.getDescription());
    }
}