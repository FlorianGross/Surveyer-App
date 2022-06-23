package com.example.surveyer.backend.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.surveyer.App;

import java.util.UUID;


public class PreferenceUtil {
    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_DEVICE_ID = "device_id";
    @SuppressLint("ApplySharedPref")
    public static String getDeviceId() {
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(KEY_DEVICE_ID)){
            sharedPreferences.edit().putString(KEY_DEVICE_ID, null).commit();
        }
        return sharedPreferences.getString(KEY_DEVICE_ID, null);
    }


}
