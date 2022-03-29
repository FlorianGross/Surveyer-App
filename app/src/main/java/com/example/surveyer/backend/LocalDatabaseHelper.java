package com.example.surveyer.backend;

public class LocalDatabaseHelper {

    private static LocalDatabaseHelper instance;

    private LocalDatabaseHelper() {
    }

    public static LocalDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new LocalDatabaseHelper();
        }
        return instance;
    }

    WebSocketHelper webSocketHelper = WebSocketHelper.getInstance();


    public void refreshLocalSurveyDatabase(String uid) {

    }


}

