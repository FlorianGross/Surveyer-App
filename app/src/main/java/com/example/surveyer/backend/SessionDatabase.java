package com.example.surveyer.backend;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Session.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class SessionDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
}
