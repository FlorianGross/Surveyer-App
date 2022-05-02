package com.example.surveyer.ui.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.surveyer.R;

public class Survey_Create extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_create);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.survey_type, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        switch (item) {
            case "Ja/Nein": {

            }
            break;
            case "Ja/Nein/Enthaltung": {

            }
            break;
            case "Dafür/Dagegen/Enthaltung": {

            }
            break;
            case "Dafür/Dagegen": {

            }
            break;
            case "Eigenes":{

            }
            break;
            default:{

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}