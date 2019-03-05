package com.mickmelon.carshare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mickmelon.carshare.Identity;
import com.mickmelon.carshare.R;
import com.mickmelon.carshare.util.ToastHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText _email;
    private EditText _name;
    private EditText _password;
    private EditText _phoneNumber;
    private EditText _website;
    private EditText _location;
    private EditText _description;
    private Button _registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _email = findViewById(R.id.editText_Email);
        _name = findViewById(R.id.editText_Name);
        _password = findViewById(R.id.editText_Password);
        _phoneNumber = findViewById(R.id.editText_PhoneNumber);
        _website = findViewById(R.id.editText_Website);
        _location = findViewById(R.id.editText_Location);
        _description = findViewById(R.id.editText_Description);
        _registerButton = findViewById(R.id.button_Register);

        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegisterForm();
            }
        });
    }

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
            ToastHelper.showToast(this, "Account registered successfully.");
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            // Already a user with that email
            ToastHelper.showToast(this, "That email address is already in use.");
        }
    }
}
