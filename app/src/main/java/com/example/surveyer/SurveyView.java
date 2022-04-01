package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class SurveyView extends AppCompatActivity {
    String surveyID;
    Button approve, deny, skip;
    int approveValue = 0, denyValue = 0, enhaltungValue = 0;
    PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view);

        surveyID = getIntent().getStringExtra("surveyID");

        approve = findViewById(R.id.approve_button);
        deny = findViewById(R.id.deny_button);
        skip = findViewById(R.id.not_participate_button);
        chart = findViewById(R.id.any_chart_view);
        Context context = getApplicationContext();
        chart.setUsePercentValues(false);
        chart.setDrawEntryLabels(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.setData(setChartData(context));
        approve.setOnClickListener(view -> {
            approveValue++;
            disableButtons();
            System.out.println(approveValue);
            reloadChart();
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
    }

    private void disableButtons() {
        approve.setEnabled(false);
        deny.setEnabled(false);
        skip.setEnabled(false);
    }

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
}