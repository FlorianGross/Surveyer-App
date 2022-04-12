package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.surveyer.R;
import com.example.surveyer.backend.JSON.UserJSON;
import com.example.surveyer.backend.WebSocketHelper;

public class Register extends AppCompatActivity {

    EditText editPassword, editUsername, editEmail;
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editPassword = findViewById(R.id.passwordInput);
        editUsername = findViewById(R.id.usernameInput);
        register = findViewById(R.id.registerButton2);
        login = findViewById(R.id.loginButton2);
        editEmail = findViewById(R.id.emailInput);

        WebSocketHelper helper = WebSocketHelper.getInstance();

        register.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String email = editEmail.getText().toString();
            if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
                return;
            }

            UserJSON user = new UserJSON(username, password, email);
            helper.registerUser(user);
            if(awaitResponse()) {
                Intent intent = new Intent(Register.this, Navigations.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            intent.putExtra("username", editUsername.getText().toString()).putExtra("password", editPassword.getText().toString());
            startActivity(intent);
        });
    }

    private boolean awaitResponse() {
        WebSocketHelper helper = WebSocketHelper.getInstance();
        for(int i = 0; i < 10; i++){
            System.out.println("waiting for response");
            if(helper.status == WebSocketHelper.STATUS_ERROR){
                return false;
            }
            if(helper.status == WebSocketHelper.STATUS_REGISTERED){
                helper.status = WebSocketHelper.STATUS_STANDARD;
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("failed to register");
        return false;
    }
}