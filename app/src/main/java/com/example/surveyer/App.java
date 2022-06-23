package com.example.surveyer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static OkHttpClient okHttpClient;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        okHttpClient = new OkHttpClient.Builder().build();
        gson = new GsonBuilder().create();
    }

    public static Context getContext() {
        return context;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static Gson getGson() {
        return gson;
    }

}
