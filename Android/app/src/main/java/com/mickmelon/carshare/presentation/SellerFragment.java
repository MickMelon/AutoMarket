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
import com.mickmelon.carshare.core.Seller;
import com.mickmelon.carshare.presentation.viewmodels.SellerViewModel;
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;

public class SellerFragment extends Fragment {
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

        Button callButton = view.findViewById(R.id.button_Call);
        Button emailButton = view.findViewById(R.id.button_Email);
        Button websiteButton = view.findViewById(R.id.button_Website);
        Button mapButton = view.findViewById(R.id.button_Map);

        callButton.setOnClickListener(v -> makePhoneCall(sellerLiveData.getValue().getPhoneNumber()));
        emailButton.setOnClickListener(v -> composeEmail(sellerLiveData.getValue().getEmail(), "Enquiry"));
        websiteButton.setOnClickListener(v -> openWebPage(sellerLiveData.getValue().getWebsite()));
        mapButton.setOnClickListener(v -> ActivityHelper.showActivity(getContext(), MapsActivity.class));
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
}
