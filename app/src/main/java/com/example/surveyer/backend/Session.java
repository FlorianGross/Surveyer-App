package com.example.surveyer.backend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Session {
    @PrimaryKey(autoGenerate = false)
    public String sessionId;
    @ColumnInfo(name = "session_owner")
    public String sessionOwner;
    @ColumnInfo(name = "session_participants")
    public List<String> sessionParticipants;
    @ColumnInfo(name = "session_open")
    public boolean sessionOpen;

    public Session(String id , String sessionOwner,List<String> sessionParticipants, boolean sessionOpen) {
        this.sessionId = id;
        this.sessionOwner = sessionOwner;
        this.sessionOpen = sessionOpen;
        this.sessionParticipants = sessionParticipants;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionOwner() {
        return sessionOwner;
    }

    public void setSessionOwner(String sessionOwner) {
        this.sessionOwner = sessionOwner;
    }

    public boolean isSessionOpen() {
        return sessionOpen;
    }

    public void setSessionOpen(boolean sessionOpen) {
        this.sessionOpen = sessionOpen;
    }

    public List<String> getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(List<String> sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }
}
