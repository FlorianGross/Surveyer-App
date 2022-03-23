package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.ui.ChartCredits;

import java.util.ArrayList;
import java.util.List;

public class SurveyView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Dafür", 1));
        data.add(new ValueDataEntry("Dagegen", 1));
        data.add(new ValueDataEntry("Enthaltung", 1));

        pie.data(data);

        pie.title("Umfrage");

        pie.labels().position("inside");
        pie.legend().title().enabled();
        ChartCredits credits = pie.credits();
        credits.enabled(false);
        anyChartView.setChart(pie);
    }
}