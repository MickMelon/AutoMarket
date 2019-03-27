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
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.ToastHelper;

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
        postAdvertButton.setOnClickListener(v -> submitPostAdvertForm());
    }

    /**
     * Submits the post advert form.
     */
    private void submitPostAdvertForm() {
        String vehicleReg = _vehicleReg.getText().toString();
        String description = _description.getText().toString();
        Double price = Double.parseDouble(_price.getText().toString());
        int sellerId = Identity.getCurrentUser().getSellerId();

        boolean success = _dataAccess.adverts().addAdvert(new Advert(-1, vehicleReg, description, price, sellerId, ""));
        if (success) {
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new AdvertBrowserFragment(), true);
        } else {
            ToastHelper.showToast(getContext(), "There was an issue posting the advert.");
        }
    }
}
