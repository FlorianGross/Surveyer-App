package com.example.surveyer.backend;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<SessionJSON> fromStringToRefreshSession(String value) {
        Type listType = new TypeToken<List<SessionJSON>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<SurveyJSON> fromStringToRefreshSurvey(String value) {
        Type listType = new TypeToken<List<SurveyJSON>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRefreshSessionToString(List<SessionJSON> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static String fromRefreshSurveyToString(List<SurveyJSON> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static List<String> fromStringToList(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListToString(List<String> value) {
        return new Gson().toJson(value);
    }

}
