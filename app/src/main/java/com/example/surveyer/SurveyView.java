package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import com.github.mikephil.charting.charts.PieChart;

public class SurveyView extends AppCompatActivity {
    Button approve, deny, skip;
    int approveValue = 0, denyValue = 0, enhaltungValue = 0;
    PieChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view);

        approve = findViewById(R.id.approve_button);
        deny = findViewById(R.id.deny_button);
        skip = findViewById(R.id.not_participate_button);
        chart = findViewById(R.id.any_chart_view);

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        approve.setOnClickListener(view -> approveValue++);
        deny.setOnClickListener(view -> {

        });
        skip.setOnClickListener(view -> {

        });
    }
}