package com.example.surveyer.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.helper.SurveyHelper;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.MainActivity;
import com.example.surveyer.ui.Navigations;
import com.example.surveyer.ui.notifications.Fragment_Survey;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class SurveyView extends AppCompatActivity {
    String surveyID;
    Button approve, deny, skip;
    int approveValue = 0, denyValue = 0, enhaltungValue = 0;
    PieChart chart;
    SurveyJSON survey = null;
    SurveyViewModel viewModel;
    SocketLiveData socketLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view);
        App.setInForeground(true);
        surveyID = getIntent().getStringExtra("surveyId");
        if (surveyID == null) {
            startActivity(Navigations.getNavigationIntent(this));
        }
        approve = findViewById(R.id.approve_button);
        deny = findViewById(R.id.deny_button);
        skip = findViewById(R.id.not_participate_button);
        chart = findViewById(R.id.any_chart_view);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SurveyViewModel.class);
        socketLiveData = viewModel.getSocketLiveData();
        socketLiveData.observe(this, socketEventModelObserver);
        socketLiveData.connect();
        setValues();
        chart.setUsePercentValues(false);
        chart.setDrawEntryLabels(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.setData(setChartData(getApplicationContext()));

        approve.setOnClickListener(view -> {
            approveValue++;
            disableButtons();
            System.out.println(approveValue);
            reloadChart();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        deny.setOnClickListener(view -> {
            denyValue++;
            disableButtons();
            System.out.println(denyValue);
            reloadChart();
        });
        skip.setOnClickListener(view -> {
            disableButtons();
            enhaltungValue++;
            System.out.println(enhaltungValue);
            reloadChart();
        });
        getSurvey();
    }

    void getSurvey() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        object.addProperty("surveyId", surveyID);
        viewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETSURVEYFROMID, object)));
    }

    private void disableButtons() {
        approve.setEnabled(false);
        deny.setEnabled(false);
        skip.setEnabled(false);
    }

    private void setValues() {
        if (survey != null) {
            denyValue = survey.getSurveyDeny().length;
            approveValue = survey.getSurveyApprove().length;
            enhaltungValue = survey.getSurveyNotParicipate().length;
            if(survey.getAllowEnthaltung() != null) {
                skip.setEnabled(survey.getAllowEnthaltung());
            }
        } else {
            denyValue = 0;
            approveValue = 0;
            enhaltungValue = 0;
        }
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(SurveyView.class, "getSocket: " + socketEventModel.toString());
        try {
            JSONObject object = new JSONObject(socketEventModel.getPayloadAsString());
            if(object.getString("type").equals("Refresh")) {
                System.out.println("Refresh");
                getSurvey();
            }else if (object.has("survey") && object.getString("result").equals("Survey")) {
                survey = SurveyHelper.getSurveyFromJSONOBject(object.getJSONObject("survey"));
                if (survey.getSurveyDeny() != null) {
                    denyValue = survey.getSurveyDeny().length;
                }
                if (survey.getSurveyApprove() != null) {
                    approveValue = survey.getSurveyApprove().length;
                }
                if (survey.getSurveyNotParicipate() != null) {
                    enhaltungValue = survey.getSurveyNotParicipate().length;
                }
                if (survey.getParticipants() != null) {
                    String[] participants = survey.getParticipants();
                    for (String participant : participants) {
                        if (participant.equals(PreferenceUtil.getDeviceId())) {
                            disableButtons();
                        }
                    }
                }
                reloadChart();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private PieData setChartData(Context context) {
        PieDataSet pieData = new PieDataSet(getData(), "Survey Results");
        pieData.setColors(new int[]{R.color.color1, R.color.color2, R.color.color3}, context);
        pieData.setValueTextSize(20);
        return new PieData(pieData);

    }

    private void reloadChart() {
        chart.setData(setChartData(getApplicationContext()));
        chart.invalidate();
    }

    private List<PieEntry> getData() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(approveValue, "Dafür"));
        entries.add(new PieEntry(denyValue, "Dagegen"));
        entries.add(new PieEntry(enhaltungValue, "Enthaltung"));
        return entries;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }
}