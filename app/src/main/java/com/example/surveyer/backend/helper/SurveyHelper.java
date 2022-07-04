package com.example.surveyer.backend.helper;

import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SurveyHelper {
    public static ArrayList<SurveyJSON> getSurveyListFromJSONArray(JSONArray array) {
        ArrayList<SurveyJSON> surveyList = new ArrayList<>();
        try {
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
                if (jsonObject.has("allowEnthaltung")) {
                    survey.allowEnthaltung = jsonObject.getBoolean("allowEnthaltung");
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
            if (jsonObject.has("allowEnthaltung")) {
                survey.allowEnthaltung = jsonObject.getBoolean("allowEnthaltung");
            }
            if (jsonObject.has("surveyDescription")) {
                survey.surveyDescription = jsonObject.getString("surveyDescription");
            }
            if (jsonObject.has("surveyDeny")) {
                JSONArray denyArray = jsonObject.getJSONArray("surveyDeny");
                survey.surveyDeny = new String[denyArray.length()];
                for (int i = 0; i < denyArray.length(); i++) {
                    survey.surveyDeny[i] = denyArray.getString(i);
                }
            }
            if (jsonObject.has("surveyApprove")) {
                JSONArray approveArray = jsonObject.getJSONArray("surveyApprove");
                survey.surveyApprove = new String[approveArray.length()];
                for (int i = 0; i < approveArray.length(); i++) {
                    survey.surveyApprove[i] = approveArray.getString(i);
                }
            }
            if (jsonObject.has("surveyNotParicipate")) {
                JSONArray notParticipateArray = jsonObject.getJSONArray("surveyNotParicipate");
                survey.surveyNotParicipate = new String[notParticipateArray.length()];
                for (int i = 0; i < notParticipateArray.length(); i++) {
                    survey.surveyNotParicipate[i] = notParticipateArray.getString(i);
                }
            }
            if (jsonObject.has("participants")) {
                JSONArray participantsArray = jsonObject.getJSONArray("participants");
                survey.participants = new String[participantsArray.length()];
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

    public static SessionJSON getSessionFromJSONOBject(JSONObject jsonObject) {
        SessionJSON session = new SessionJSON();
        ArrayList<String> participants = new ArrayList<>();
        ArrayList<String> surveys = new ArrayList<>();
        try {
            if (jsonObject.has("_id")) {
                session.id = jsonObject.getString("_id");
            }
            if (jsonObject.has("owner")) {
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
            }
            if (jsonObject.has("surveys")) {
                JSONArray denyArray = jsonObject.getJSONArray("surveys");
                session.surveys = new String[denyArray.length()];
                for (int i = 0; i < denyArray.length(); i++) {
                    session.surveys[i] = denyArray.getString(i);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return session;
    }

    public static ArrayList<SessionJSON> getSessionListFromJSONArray(JSONArray array) {
        ArrayList<SessionJSON> list = new ArrayList<>();
        ArrayList<String> participantsArray = new ArrayList<>();
        ArrayList<String> surveysArray = new ArrayList<>();
        System.out.println("Array length: " + array.length());
        try {
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
        } catch (Exception e) {
            System.out.println("Error in parsing JSON");
        }
        return list;
    }

    public static ArrayList<SessionJSON> getSessionAndSurveyFromObject(JSONObject obj) {
        ArrayList<SessionJSON> sessionList = new ArrayList<>();
        ArrayList<SurveyJSON> surveyList = new ArrayList<>();
        try {
            if (obj.has("sessions")) {
                JSONArray sessions = obj.getJSONArray("sessions");
                for (int i = 0; i < sessions.length(); i++) {
                    JSONObject jsonObject = sessions.getJSONObject(i);
                    SessionJSON sessionJSON = getSessionFromJSONOBject(jsonObject);
                    sessionList.add(sessionJSON);
                }
            }
            if (obj.has("surveys")) {
                JSONArray arr = obj.getJSONArray("surveys");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    SurveyJSON surveyJSON = getSurveyFromJSONOBject(jsonObject);

                    surveyList.add(surveyJSON);
                }
            }
            return addSurveysToSessions(sessionList, surveyList);
        } catch (Exception e) {
            System.out.println("Error in parsing JSON" + e);
        }
        return sessionList;
    }

    public static ArrayList<SessionJSON> addSurveysToSessions(ArrayList<SessionJSON> sessionList, ArrayList<SurveyJSON> surveyList) {
        for (int i = 0; i < sessionList.size(); i++) {
            SurveyJSON[] surveys = new SurveyJSON[surveyList.size()];
            int it = 0;
            for (int j = 0; j < surveyList.size(); j++) {
                if (sessionList.get(i).id.equals(surveyList.get(j).surveySession)) {
                    surveys[it] = surveyList.get(j);
                    System.out.println("Survey: " + surveyList.get(j).surveyID + " added to Session: " + sessionList.get(i).id);
                    it++;
                }
            }
            sessionList.get(i).surveyArray = surveys;
        }
        return sessionList;
    }
}
