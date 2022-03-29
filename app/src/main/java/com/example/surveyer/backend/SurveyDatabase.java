package com.example.surveyer.backend;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class}, version = 1)
public abstract class SurveyDatabase extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
