package com.floriang.surveyer.backend.json;

import androidx.annotation.NonNull;

public class VoteJSON {
    public final String uid;
    public final String surveyID;
    public final int sendID;
    public static final int APPROVE = 0;
    public static final int DENY = 1;
    public static final int ABSTAIN = 2;

    public VoteJSON(String uid, String surveyID, int sendID) {
        this.uid = uid;
        this.surveyID = surveyID;
        this.sendID = sendID;
    }

    @NonNull
    @Override
    public String toString() {
        return "VoteJSON{" +
                "uid='" + uid + '\'' +
                ", surveyID='" + surveyID + '\'' +
                ", sendID=" + sendID;
    }
}
