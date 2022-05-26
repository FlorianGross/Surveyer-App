package com.example.surveyer.backend.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Session {
    @PrimaryKey
    @NonNull
    public String sessionId;
    @ColumnInfo(name = "session_owner")
    public String sessionOwner;
    @ColumnInfo(name = "session_participants")
    public String sessionParticipants;
    @ColumnInfo(name = "session_open")
    public boolean sessionOpen;

    public Session(@NonNull String sessionId, String sessionOwner, String sessionParticipants, boolean sessionOpen) {
        this.sessionId = sessionId;
        this.sessionOwner = sessionOwner;
        this.sessionParticipants = sessionParticipants;
        this.sessionOpen = sessionOpen;
    }

    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(@NonNull String sessionId) {
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

    public String getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(String sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }
}
