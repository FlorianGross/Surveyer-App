package com.example.surveyer.backend.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SurveyDao {
    @Query("SELECT * FROM Survey")
    List<Survey> getAllSurveys();

    @Query("SELECT * FROM Survey WHERE surveyId = :surveyId")
    Survey getSurvey(int surveyId);

    @Insert
    void insertSurvey(Survey survey);

    @Update
    void updateSurvey(Survey survey);

    @Delete
    void deleteSurvey(Survey survey);

    @Query("DELETE FROM Survey")
    void deleteAllSurveys();

}
