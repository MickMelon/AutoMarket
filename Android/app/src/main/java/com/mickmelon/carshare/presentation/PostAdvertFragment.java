package com.mickmelon.carshare.presentation;

import android.os.Bundle;
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
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.ToastHelper;

public class PostAdvertFragment extends Fragment {
    private DataAccess _dataAccess;

    private EditText _vehicleReg;
    private EditText _description;
    private EditText _price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_postadvert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _dataAccess = DataAccess.getInstance();

        _vehicleReg = view.findViewById(R.id.editText_VehicleReg);
        _description = view.findViewById(R.id.editText_Description);
        _price = view.findViewById(R.id.editText_Price);

        Button postAdvertButton = view.findViewById(R.id.button_PostAdvert);
        postAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPostAdvertForm();
            }
        });
    }

    private void submitPostAdvertForm() {
        String vehicleReg = _vehicleReg.getText().toString();
        String description = _description.getText().toString();
        Double price = Double.parseDouble(_price.getText().toString());
        int sellerId = Identity.getCurrentUser().getSellerId();

        boolean success = _dataAccess.adverts().addAdvert(new Advert(-1, vehicleReg, description, price, sellerId));
        if (success) {
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new AdvertBrowserFragment(), true);
        } else {
            ToastHelper.showToast(getContext(), "There was an issue posting the advert.");
        }
    }
}
