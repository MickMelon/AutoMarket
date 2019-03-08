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

public class RegisterFragment extends Fragment {
    private EditText _email;
    private EditText _name;
    private EditText _password;
    private EditText _phoneNumber;
    private EditText _website;
    private EditText _location;
    private EditText _description;
    private Button _registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegisterForm();
            }
        });
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
