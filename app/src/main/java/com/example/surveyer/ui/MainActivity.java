package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.surveyer.App;
import com.example.surveyer.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setInForeground(true);

        new Handler().postDelayed(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent mainIntent = new Intent(MainActivity.this, Login.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}