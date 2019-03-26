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
import com.mickmelon.carshare.util.ActivityHelper;
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.ToastHelper;

/**
 * The fragment used for controlling the register layout.
 */
public class RegisterFragment extends Fragment {
    /**
     * The input email.
     */
    private EditText _email;

    /**
     * The input name.
     */
    private EditText _name;

    /**
     * The input password.
     */
    private EditText _password;

    /**
     * The input phone number.
     */
    private EditText _phoneNumber;

    /**
     * The input website.
     */
    private EditText _website;

    /**
     * The input location.
     */
    private EditText _location;

    /**
     * The input description.
     */
    private EditText _description;

    /**
     * The register button view.
     */
    private Button _registerButton;

    /**
     * Called when the fragment is created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    /**
     * Called after the fragment has been created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // If the user is logged in then they shouldn't be able to view this.
        if (Identity.isLoggedIn()) {
            ActivityHelper.showMainActivity(getContext());
            return;
        }

        _email = view.findViewById(R.id.editText_Email);
        _name = view.findViewById(R.id.editText_Name);
        _password = view.findViewById(R.id.editText_Password);
        _phoneNumber = view.findViewById(R.id.editText_PhoneNumber);
        _website = view.findViewById(R.id.editText_Website);
        _location = view.findViewById(R.id.editText_Location);
        _description = view.findViewById(R.id.editText_Description);
        _registerButton = view.findViewById(R.id.button_Register);

        _registerButton.setOnClickListener(v -> submitRegisterForm());
    }

    /**
     * Called when the register form has been submitted.
     */
    private void submitRegisterForm() {
        String email = _email.getText().toString();
        String name = _name.getText().toString();
        String password = _password.getText().toString();
        String phoneNumber = _phoneNumber.getText().toString();
        String website = _website.getText().toString();
        String location = _location.getText().toString();
        String description = _description.getText().toString();

        boolean success = Identity.register(email, name, password, phoneNumber, website, location, description);
        if (success) {
            // Show the login fragment
            ToastHelper.showToast(getContext(), "Account registered successfully.");
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new LoginFragment(), true);
        } else {
            // Already a user with that email
            ToastHelper.showToast(getContext(), "That email address is already in use.");
        }
    }
}
