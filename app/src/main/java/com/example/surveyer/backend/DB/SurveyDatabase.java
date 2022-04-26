package com.example.surveyer.backend.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Survey.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)

public abstract class SurveyDatabase extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
