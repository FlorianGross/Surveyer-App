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
                if(jsonObject.has("participants")){
                    JSONArray participants = jsonObject.getJSONArray("participants");
                    survey.participants = new String[participants.length()];
                    for(int j = 0; j < participants.length(); j++){
                        survey.participants[j] = participants.getString(j);
                    }
                }else{
                    survey.participants = new String[0];
                }
                if(jsonObject.has("surveyApproved")) {
                    JSONArray surveyApproved = jsonObject.getJSONArray("surveyApproved");
                    survey.surveyApprove = new String[surveyApproved.length()];
                    for (int j = 0; j < surveyApproved.length(); j++) {
                        survey.surveyApprove[j] = surveyApproved.getString(j);
                    }
                }else{
                    survey.surveyApprove = new String[0];
                }
                if(jsonObject.has("surveyDeny")) {
                    JSONArray surveyDenied = jsonObject.getJSONArray("surveyDeny");
                    survey.surveyDeny = new String[surveyDenied.length()];
                    for (int j = 0; j < surveyDenied.length(); j++) {
                        survey.surveyDeny[j] = surveyDenied.getString(j);
                    }
                }else{
                    survey.surveyDeny = new String[0];
                }
                if(jsonObject.has("surveyNotParicipate")){
                    JSONArray surveyNotParticipate = jsonObject.getJSONArray("surveyNotParicipate");
                    survey.surveyNotParicipate = new String[surveyNotParticipate.length()];
                    for(int j = 0; j < surveyNotParticipate.length(); j++){
                        survey.surveyNotParicipate[j] = surveyNotParticipate.getString(j);
                    }
                }else{
                    survey.surveyNotParicipate = new String[0];
                }

                surveyList.add(survey);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return survey;
    }

    public static SessionJSON getSessionFromJSONOBject(JSONObject jsonObject) {
        SessionJSON session = new SessionJSON();
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
                    session.participants[i] = participantsArray.getJSONObject(i).getString("username");
                }
            }
            if (jsonObject.has("surveys")) {
                JSONArray surveys = jsonObject.getJSONArray("surveys");
                session.surveyArray = new SurveyJSON[surveys.length()];
                for (int j = 0; j < surveys.length(); j++){
                    session.surveyArray[j] = getSurveyFromJSONOBject(surveys.getJSONObject(j));
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
                    sessionJSON.surveyArray = new SurveyJSON[surveys.length()];
                    for (int j = 0; j < surveys.length(); j++){
                        sessionJSON.surveyArray[j] = getSurveyFromJSONOBject(surveys.getJSONObject(j));
                    }
                }
                list.add(sessionJSON);
            }
        } catch (Exception e) {
            System.out.println("Error in parsing JSON");
        }
        return list;
    }

}
