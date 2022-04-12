package com.example.surveyer.backend.models;

import androidx.annotation.NonNull;

import com.example.surveyer.App;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel {
    public static <T extends BaseModel>T fromJson(String json, Class<T>typeOf){
        return App.getGson().fromJson(json, typeOf);
    }

    @NonNull
    @Override
    public String toString() {
        return App.getGson().toJson(this);
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject(toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
