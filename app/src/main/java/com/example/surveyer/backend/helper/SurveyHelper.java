package com.example.surveyer.backend.helper;

import android.widget.ArrayAdapter;

import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SurveyHelper {
    public static ArrayList<SurveyJSON> getSurveyListFromObject(JSONObject obj) {
        ArrayList<SurveyJSON> surveyList = new ArrayList<>();
        try {
            JSONArray array = obj.getJSONArray("events");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                SurveyJSON survey = new SurveyJSON();
                if (jsonObject.has("_id")) {
                    survey.surveyID = jsonObject.getString("_id");
                }
                if (jsonObject.has("creator")) {
                    survey.creator = jsonObject.getString("creator");
                }
                if (jsonObject.has("surveyName")) {
                    survey.surveyName = jsonObject.getString("surveyName");
                }
                if (jsonObject.has("surveySession")) {
                    survey.surveySession = jsonObject.getString("surveySession");
                }
                if (jsonObject.has("surveyOpened")) {
                    survey.surveyOpened = jsonObject.getBoolean("surveyOpened");
                }
                if (jsonObject.has("surveyDescription")) {
                    survey.surveyDescription = jsonObject.getString("surveyDescription");
                }
                surveyList.add(survey);
                System.out.println("Survey: " + survey.surveyID + " " + survey.surveyName + " " + survey.surveySession + " " + survey.surveyOpened);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return surveyList;
    }

    public static SurveyJSON getSurveyFromJSONOBject(JSONObject jsonObject) {
        SurveyJSON survey = new SurveyJSON();
        try {
            if (jsonObject.has("_id")) {
                survey.surveyID = jsonObject.getString("_id");
            }
            if (jsonObject.has("creator")) {
                survey.creator = jsonObject.getString("creator");
            }
            if (jsonObject.has("surveyName")) {
                survey.surveyName = jsonObject.getString("surveyName");
            }
            if (jsonObject.has("surveySession")) {
                survey.surveySession = jsonObject.getString("surveySession");
            }
            if (jsonObject.has("surveyOpened")) {
                survey.surveyOpened = jsonObject.getBoolean("surveyOpened");
            }
            if (jsonObject.has("surveyDescription")) {
                survey.surveyDescription = jsonObject.getString("surveyDescription");
            }
            if (jsonObject.has("surveyDeny")) {
                JSONArray denyArray = jsonObject.getJSONArray("surveyDeny");
                for (int i = 0; i < denyArray.length(); i++) {
                    survey.surveyDeny[i] = denyArray.getString(i);
                }
            }
            if (jsonObject.has("surveyApprove")) {
                JSONArray approveArray = jsonObject.getJSONArray("surveyApprove");
                for (int i = 0; i < approveArray.length(); i++) {
                    survey.surveyApprove[i] = approveArray.getString(i);
                }
            }
            if (jsonObject.has("surveyNotParicipate")) {
                JSONArray notParticipateArray = jsonObject.getJSONArray("surveyNotParicipate");
                for (int i = 0; i < notParticipateArray.length(); i++) {
                    survey.surveyNotParicipate[i] = notParticipateArray.getString(i);
                }
            }
            if (jsonObject.has("participants")) {
                JSONArray participantsArray = jsonObject.getJSONArray("participants");
                for (int i = 0; i < participantsArray.length(); i++) {
                    survey.participants[i] = participantsArray.getString(i);
                }
            }
            System.out.println("Survey: " + survey.surveyID + " " + survey.surveyName + " " + survey.surveySession + " " + survey.surveyOpened);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return survey;
    }


    public static SessionJSON getSessionFromJSONOBject (JSONObject jsonObject) {
        SessionJSON session = new SessionJSON();
        ArrayList<String> participants = new ArrayList<>();
        ArrayList<String> surveys = new ArrayList<>();
        try {
            jsonObject = jsonObject.getJSONObject("event");
            if (jsonObject.has("_id")) {
                session.id = jsonObject.getString("_id");
            }
            if(jsonObject.has("owner")){
                session.owner = jsonObject.getString("owner");
            }
            if (jsonObject.has("name")) {
                session.name = jsonObject.getString("name");
            }
            if (jsonObject.has("description")) {
                session.description = jsonObject.getString("description");
            }
            if (jsonObject.has("isActive")) {
                session.isActive = jsonObject.getBoolean("isActive");
            }

            if (jsonObject.has("participants")) {
                JSONArray participantsArray = jsonObject.getJSONArray("participants");
                session.participants = new String[participantsArray.length()];
                for (int i = 0; i < participantsArray.length(); i++) {
                    session.participants[i] = participantsArray.getString(i);
                }
                for (int i = 0; i < session.participants.length; i++) {
                    System.out.println("Participant: " + session.participants[i]);
                }

            }
            if (jsonObject.has("surveys")) {
                JSONArray denyArray = jsonObject.getJSONArray("surveys");
                session.surveys = new String[denyArray.length()];
                for (int i = 0; i < denyArray.length(); i++) {
                    session.surveys[i] = denyArray.getString(i);
                    System.out.println("Survey: " + session.surveys[i]);
                }
            }
          } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }


    public static ArrayList<SessionJSON> getSessionListFromObject(JSONObject obj) {
        ArrayList<SessionJSON> list = new ArrayList<>();
        ArrayList<String> participantsArray = new ArrayList<>();
        ArrayList<String> surveysArray = new ArrayList<>();
        try {
            if (obj.has("events")) {
                JSONArray array = obj.getJSONArray("events");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    SessionJSON sessionJSON = new SessionJSON();
                    if (jsonObject.has("name")) {
                        sessionJSON.name = jsonObject.getString("name");
                    }
                    if (jsonObject.has("_id")) {
                        sessionJSON.id = jsonObject.getString("_id");
                    }
                    if (jsonObject.has("description")) {
                        sessionJSON.description = jsonObject.getString("description");
                    }
                    if (jsonObject.has("isActive")) {
                        sessionJSON.isActive = jsonObject.getBoolean("isActive");
                    }
                    if (jsonObject.has("owner")) {
                        sessionJSON.owner = jsonObject.getString("owner");
                    }
                    if (jsonObject.has("participants")) {
                        JSONArray participants = jsonObject.getJSONArray("participants");
                        for (int j = 0; j < participants.length(); j++) {
                            participantsArray.add(participants.getString(j));
                        }
                        sessionJSON.participants = participantsArray.toArray(new String[0]);
                    }
                    if (jsonObject.has("surveys")) {
                        JSONArray surveys = jsonObject.getJSONArray("surveys");
                        for (int j = 0; j < surveys.length(); j++) {
                            surveysArray.add(surveys.getString(j));
                        }
                        sessionJSON.surveys = surveysArray.toArray(new String[0]);
                    }
                    list.add(sessionJSON);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in parsing JSON");
        }
        return list;
    }
}
