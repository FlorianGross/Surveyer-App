package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.surveyer.App;
import com.example.surveyer.R;

public class Login extends AppCompatActivity {

    EditText editPassword, editUsername;
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        App.setInForeground(true);
        editPassword = findViewById(R.id.passwordInput);
        editUsername = findViewById(R.id.usernameInput);
        login = findViewById(R.id.registerButton);
        register = findViewById(R.id.loginButton);

        login.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

        });
        register.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            intent.putExtra("username", editUsername.getText().toString()).putExtra("password", editPassword.getText().toString());
            startActivity(intent);
        });
    }
    public static Intent getLoginIntent(Context context){
        return new Intent(context, Login.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }
}