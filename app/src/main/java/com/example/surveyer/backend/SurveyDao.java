package com.example.surveyer.backend;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SurveyDao {
    @Query("SELECT * FROM Survey")
    public List<Survey> getAllSurveys();

    @Query("SELECT * FROM Survey WHERE surveyId = :surveyId")
    public Survey getSurvey(int surveyId);

    @Insert
    public void insertSurvey(Survey survey);

    @Update
    public void updateSurvey(Survey survey);

    @Delete
    public void deleteSurvey(Survey survey);

    @Query("DELETE FROM Survey")
    public void deleteAllSurveys();

}
