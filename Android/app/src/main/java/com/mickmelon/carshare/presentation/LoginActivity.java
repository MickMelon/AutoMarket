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

public class LoginActivity extends AppCompatActivity {
    private EditText _email;
    private EditText _password;
    private Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _email = findViewById(R.id.editText_Email);
        _password = findViewById(R.id.editText_Password);
        _loginButton = findViewById(R.id.button_Login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLoginForm();
            }
        });
    }

    private void submitLoginForm() {
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        boolean success = Identity.login(email, password);
        if (success) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            ToastHelper.showToast(this, "Incorrect email or password.");
        }
    }
}
