package com.example.surveyer.backend;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketHelper {
    private static WebSocketHelper INSTANCE;
    public WebSocket webSocket;
    public WebSocketListener listener;
    public String sessionID = "", userID = "";

    public static WebSocketHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebSocketHelper();
        }
        return INSTANCE;
    }

    public static String getWifiIp(Context context) {
        return "ws://10.0.2.2";
    }

    public void connectToSocket(Context context) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getWifiIp(context)).build();
        webSocket = client.newWebSocket(request, new MyListener(context));
        ;
    }

    public void registerUser() {
        try {
            JSONObject answer = new JSONObject();
            answer.put("Type", "registerUser");
            webSocket.send(answer.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void initSession() {
        System.out.println("Init Session");
        try {
            JSONObject jo = new JSONObject();
            jo.put("Type", "startSession");
            webSocket.send(jo.toString());
            System.out.println(jo);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void closeSession() {

    }

    public String startSurvey() {
        return "";

    }

    private static class MyListener extends WebSocketListener {
        public MyListener(Context context) {
            this.context = context;
        }

        Context context;

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(text);
                System.out.println(jsonObject);
                useJSON(jsonObject, context);
            } catch (Exception e) {
                System.out.println("Error parsing JSONObject: " + e);
            }
        }

        private void useJSON(JSONObject jsonObject, Context context) throws JSONException {
            switch (jsonObject.getString("Type")) {
                case "Answer": {
                    System.out.println(jsonObject.toString());
                    break;
                }
                case "startSession":
                    WebSocketHelper.getInstance().initSession();
                    break;
                case "Refresh": {
                    System.out.println("Refresh");
                    ObjectMapper mapper = new ObjectMapper();
                    if (jsonObject.getString("Refresh").equals("AllSurveys")) {
                        try {
                            RefreshSurveyJSON lib = mapper.readValue(jsonObject.toString(), RefreshSurveyJSON.class);
                            refreshAllSurvey(lib, context);
                        } catch (Exception e) {
                            System.out.println("Error parsing JSONObject: " + e);
                        }
                    } else if (jsonObject.getString("Refresh").equals("AllSessions")) {
                        try {

                            RefreshSessionJSON lib = mapper.readValue(jsonObject.toString(), RefreshSessionJSON.class);
                            refreshAllSessions(lib, context);
                        } catch (Exception e) {
                            System.out.println("Error parsing JSONObject: " + e);
                        }
                    }
                    break;
                }
                default:
                    System.out.println(jsonObject.toString());
                    break;
            }
        }

        public void refreshAllSurvey(RefreshSurveyJSON result, Context context) throws JSONException {
            System.out.println("Refresh Survey");
            result.result.forEach(survey -> {
                SurveyDatabase db = Room.databaseBuilder(context, SurveyDatabase.class, "Survey").build();
                SurveyDao dao = db.surveyDao();
                dao.insertSurvey(new Survey(survey.surveyID, survey.surveyName, survey.surveyDescription, survey.creator, survey.surveyApprove, survey.surveyDeny, survey.surveyNotParicipate, survey.surveyOpened, survey.surveySession, survey.participants));
            });
        }

        public void refreshAllSessions(RefreshSessionJSON result, Context context) {
            System.out.println("Refresh Session");
            result.result.forEach(session -> {
                SessionDatabase db = Room.databaseBuilder(context, SessionDatabase.class, "Session")
                        .build();
                SessionDao dao = db.sessionDao();
                dao.insertSession(new Session(session.id, session.owner, session.participants, session.isActive));
            });
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
        }
    }
}
