package com.mickmelon.carshare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mickmelon.carshare.Identity;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.util.ToastHelper;

public class LoginFragment extends Fragment {
    private EditText _email;
    private EditText _password;
    private Button _loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _email = view.findViewById(R.id.editText_Email);
        _password = view.findViewById(R.id.editText_Password);
        _loginButton = view.findViewById(R.id.button_Login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLoginForm();
            }
        });
    }

    /**
     * Called when the login form is submitted.
     */
    private void submitLoginForm() {
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        boolean success = Identity.login(email, password);
        if (success) {
            //startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            ToastHelper.showToast(getContext(), "Incorrect email or password.");
        }
    }
}
