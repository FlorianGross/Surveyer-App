package com.example.surveyer.backend.DB;

import androidx.room.TypeConverter;

import com.example.surveyer.backend.JSON.SessionJSON;
import com.example.surveyer.backend.JSON.SurveyJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<SessionJSON> fromStringToSession(String[] value) {
        Type listType = new TypeToken<List<SessionJSON>>() {
        }.getType();

        List<SessionJSON> list = new ArrayList<>();
        for (String s : value) {
            list.add(new Gson().fromJson(s, listType));
        }
        return list;
    }

    @TypeConverter
    public static List<SurveyJSON> fromStringToSurvey(String[] value) {
        Type listType = new TypeToken<List<SurveyJSON>>() {
        }.getType();

        List<SurveyJSON> list = new ArrayList<>();
        for (String s : value) {
            list.add(new Gson().fromJson(s, listType));
        }
        return list;
    }

    @TypeConverter
    public static String fromSessionToString(List<SessionJSON> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static String fromSurveyToString(List<SurveyJSON> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static List<String> fromStingToArray(String value) {
        Type listType = new TypeToken<String[]>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayToString(String[] value) {
        return new Gson().toJson(value);
    }

}
