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
import com.mickmelon.carshare.util.FragmentHelper;
import com.mickmelon.carshare.util.ToastHelper;

/**
 * The login fragment used to control the login layout.
 */
public class LoginFragment extends Fragment {
    /**
     * The input email.
     */
    private EditText _email;

    /**
     * The input password.
     */
    private EditText _password;

    /**
     * The login button view.
     */
    private Button _loginButton;

    /**
     * Called when the fragment is about to be created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    /**
     * Called after the fragment has been created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (Identity.isLoggedIn()) {
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new AdvertBrowserFragment(), true);
            return;
        }

        _email = view.findViewById(R.id.editText_Email);
        _password = view.findViewById(R.id.editText_Password);
        _loginButton = view.findViewById(R.id.button_Login);

        _loginButton.setOnClickListener(v -> submitLoginForm());
    }

    /**
     * Called when the login form is submitted.
     */
    private void submitLoginForm() {
        boolean errors = false;
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        // Form validation
        if (password.length() < 3) {
            _password.setError("Password must be at least 3 characters.");
            errors = true;
            _password.requestFocus();
        }

        if (email.length() < 3 || !email.contains("@")) {
            _email.setError("Email must be at least 3 characters and contain @ symbol.");
            errors = true;
            _email.requestFocus();
        }

        // If there were errors, do not attempt to login
        if (errors) return;

        // Form was fine, so attempt to login
        boolean success = Identity.login(email, password);
        if (success) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            FragmentHelper.showFragment((AppCompatActivity) getActivity(), new AdvertBrowserFragment(), true);
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).setupMenu();
            }
        } else {
            ToastHelper.showToast(getContext(), "Incorrect email or password.");
        }
    }
}
