package com.example.surveyer.backend;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketHelper {
    private static WebSocketHelper INSTANCE;
    public WebSocket webSocket;

    public static WebSocketHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebSocketHelper();
        }
        return INSTANCE;
    }

    public static String getWifiIp() {
        return "ws://10.0.2.2";
    }

    public void connectToSocket(Context context) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getWifiIp()).build();
        webSocket = client.newWebSocket(request, new MyListener(context));
    }

    public void registerUser() {
        try {
            JSONObject answer = new JSONObject();
            answer.put("Type", "registerUser");
            webSocket.send(answer.toString());
        } catch (Exception e) {
            System.out.println("Error registering User" + e);
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

    private static class MyListener extends WebSocketListener {
        public MyListener(Context context) {
            this.context = context;
        }

        final Context context;

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            try {
                JSONObject jsonObject = new JSONObject(text);
                System.out.println(jsonObject);
                useJSON(jsonObject, context);
            } catch (Exception e) {
                System.out.println("Error parsing JSONObject: " + e);
            }
        }

        private void useJSON(JSONObject jsonObject, Context context) throws JSONException {
            switch (jsonObject.getString("Type")) {
                case "Answer": {
                    System.out.println(jsonObject);
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
                    System.out.println(jsonObject);
                    break;
            }
        }

        public void refreshAllSurvey(RefreshSurveyJSON result, Context context) {
            System.out.println("Refresh Survey");
            List<SurveyJSON> resultJSON = Converters.fromStringToRefreshSurvey(result.result);
            for (SurveyJSON survey : resultJSON) {
                SurveyDatabase db = Room.databaseBuilder(context, SurveyDatabase.class, "Survey").build();
                SurveyDao dao = db.surveyDao();
                dao.insertSurvey(new Survey(survey.surveyID, survey.surveyName, survey.surveyDescription, survey.creator, survey.surveyApprove, survey.surveyDeny, survey.surveyNotParicipate, survey.surveyOpened, survey.surveySession, survey.participants));

            }
        }

        public void refreshAllSessions(RefreshSessionJSON result, Context context) {
            System.out.println("Refresh Session");
            List<SessionJSON> sessions = Converters.fromStringToRefreshSession(result.result);
            for (SessionJSON session : sessions) {
                SessionDatabase db = Room.databaseBuilder(context, SessionDatabase.class, "Session")
                        .build();
                SessionDao dao = db.sessionDao();
                dao.insertSession(new Session(session.id, session.owner, session.participants, session.isActive));

            }

        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
        }
    }
}
