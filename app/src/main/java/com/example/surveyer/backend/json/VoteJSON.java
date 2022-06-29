package com.example.surveyer.backend.json;

public class VoteJSON {
    public String uid;
    public String surveyID;
    public int sendID;
    public static int APPROVE = 0;
    public static int DENY = 1;
    public static int ABSTAIN = 2;

    public VoteJSON(String uid, String surveyID, int sendID) {
        this.uid = uid;
        this.surveyID = surveyID;
        this.sendID = sendID;
    }

}
