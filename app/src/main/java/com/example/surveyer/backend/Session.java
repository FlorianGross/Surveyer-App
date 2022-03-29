package com.example.surveyer.backend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Session {
    @PrimaryKey(autoGenerate = true)
    public int sessionId;
    @ColumnInfo(name = "session_owner")
    public String sessionOwner;
    @ColumnInfo(name = "session_open")
    public boolean sessionOpen;
    @ColumnInfo(name = "session_participants")
    public String[] sessionParticipants;

}
