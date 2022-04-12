package com.example.surveyer.backend.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM Session")
    List<Session> getAllSessions();

    @Query("SELECT * FROM Session WHERE sessionID = :sessionID")
    Session getSession(int sessionID);

    @Insert
    void insertSession(Session session);

    @Update
    void updateSession(Session session);

    @Delete
    void deleteSession(Session session);

}
