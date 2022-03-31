package com.example.surveyer.backend;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.fasterxml.jackson.databind.DeserializationFeature;
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

    public static WebSocketHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebSocketHelper();
        }
        return INSTANCE;
    }

    public static String getWifiIp() {
        return "ws://141.69.98.79:3000";
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
                    if (jsonObject.getString("Result").equals("callRefresh")) {
                        WebSocketHelper.getInstance().callRefresh();
                    }
                    break;
                }
                case "startSession":
                    WebSocketHelper.getInstance().initSession();
                    break;
                case "Refresh": {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                    if (jsonObject.getString("Refresh").equals("AllSurvey")) {
                        try {
                            RefreshSurveyJSON lib = mapper.readValue(jsonObject.toString(), RefreshSurveyJSON.class);
                            refreshAllSurvey(lib, context);
                        } catch (Exception e) {
                            System.out.println("Error parsing JSONObject: " + e);
                        }
                    }
                    if (jsonObject.getString("Refresh").equals("AllSessions")) {
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
                    System.out.println("Case Default");
                    break;
            }
        }

        public void refreshAllSurvey(RefreshSurveyJSON result, Context context) {
            System.out.println("Refresh Survey");
            SurveyJSON[] resultJSON = result.result;
            for (SurveyJSON survey : resultJSON) {
                SurveyDatabase db = Room.databaseBuilder(context, SurveyDatabase.class, "Survey").build();
                SurveyDao dao = db.surveyDao();
                dao.insertSurvey(new Survey(survey.surveyID, survey.surveyName, survey.surveyDescription, survey.creator, survey.surveyApprove, survey.surveyDeny, survey.surveyNotParicipate, survey.surveyOpened, survey.surveySession, Converters.fromArrayToString(survey.participants)));

            }
        }

        public void refreshAllSessions(RefreshSessionJSON result, Context context) {
            System.out.println("Refresh Session");
            SessionJSON[] sessions = result.result;
            for (SessionJSON session : sessions) {
                SessionDatabase db = Room.databaseBuilder(context, SessionDatabase.class, "Session")
                        .build();
                SessionDao dao = db.sessionDao();
                dao.insertSession(new Session(session.id, session.owner, Converters.fromArrayToString(session.participants), session.isActive));

            }

        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
        }
    }

    private void callRefresh() {
        webSocket.send("{\"Type\":\"refreshAll\"}");
    }
}
