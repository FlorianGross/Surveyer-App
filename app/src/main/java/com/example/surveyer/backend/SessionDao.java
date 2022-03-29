package com.example.surveyer.backend;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM Session")
    public List<Session> getAllSessions();

    @Query("SELECT * FROM Session WHERE sessionID = :sessionID")
    public Session getSession(int sessionID);

    @Insert
    public void insertSession(Session session);

    @Update
    public void updateSession(Session session);

    @Delete
    public void deleteSession(Session session);

}
