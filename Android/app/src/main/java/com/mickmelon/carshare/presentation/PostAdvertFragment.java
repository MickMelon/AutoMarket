package com.mickmelon.carshare.presentation;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mickmelon.carshare.Identity;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.IntentHelper;
import com.mickmelon.carshare.util.ToastHelper;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * The fragment used to control the post advert layout.
 */
public class PostAdvertFragment extends Fragment {
    /**
     * The instance of DataAccess used to interact with the database.
     */
    private DataAccess _dataAccess;

    /**
     * The input vehicle reg.
     */
    private EditText _vehicleReg;

    /**
     * The input description.
     */
    private EditText _description;

    /**
     * The input price.
     */
    private EditText _price;

    private Button _pickPhotoButton;

    private Bitmap _pickedImage;

    /**
     * Called when the fragment is about to be created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_postadvert, container, false);
    }

    /**
     * Called after the fragment has been created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // If the user is logged in then they shouldn't be able to view this.
        if (!Identity.isLoggedIn()) {
            ActivityHelper.showMainActivity(getContext());
            return;
        }

        _dataAccess = DataAccess.getInstance();

        _vehicleReg = view.findViewById(R.id.editText_VehicleReg);
        _description = view.findViewById(R.id.editText_Description);
        _price = view.findViewById(R.id.editText_Price);

        Button postAdvertButton = view.findViewById(R.id.button_PostAdvert);
        _pickPhotoButton = view.findViewById(R.id.button_PickPhoto);
        postAdvertButton.setOnClickListener(v -> submitPostAdvertForm());
        _pickPhotoButton.setOnClickListener(v -> IntentHelper.pickPhoto(this));
    }

    /**
     * Submits the post advert form.
     */
    private void submitPostAdvertForm() {
        boolean errors = false;
        String vehicleReg = _vehicleReg.getText().toString();
        String description = _description.getText().toString();
        Double price = 0.0;

        try {
            price = Double.parseDouble(_price.getText().toString());
        } catch (NumberFormatException ex) {
            _price.setError("Please enter a price.");
            errors = true;
            _price.requestFocus();
            return;
        }

        // Carry out form validation
        if (price <= 0) {
            _price.setError("Please enter a price.");
            errors = true;
            _price.requestFocus();
        }

        if (description.length() < 3) {
            _description.setError("Description must be at least 3 characters.");
            errors = true;
            _description.requestFocus();
        }

        if (vehicleReg.length() < 2) {
            _vehicleReg.setError("Vehicle reg must be at least 2 characters.");
            errors = true;
            _vehicleReg.requestFocus();
        }

        if (errors) return;

        int sellerId = Identity.getCurrentUser().getSellerId();

        Advert advert = new Advert(-1, vehicleReg, description, price, sellerId, "");

        int advertId = _dataAccess.adverts().addAdvert(advert);
        if (advertId != -1) {
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new AdvertBrowserFragment(), true);

            if (_pickedImage != null) {
                _dataAccess.adverts().addAdvertImageBitmap(advertId, _pickedImage);
            }

        } else {
            ToastHelper.showToast(getContext(), "There was an issue posting the advert.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentHelper.SELECT_PHOTO && resultCode == RESULT_OK && intent != null) {
            Uri pickedImage = intent.getData();
            _pickedImage = null;

            try {
                _pickedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImage);
                _pickPhotoButton.setBackgroundColor(Color.GREEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
