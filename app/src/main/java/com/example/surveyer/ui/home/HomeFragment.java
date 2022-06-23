package com.example.surveyer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.util.PreferenceUtil;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView idTest = view.findViewById(R.id.idTest);
        idTest.setText(PreferenceUtil.getDeviceId());
        recyclerView = view.findViewById(R.id.recyclerView);

        SurveyJSON[] surveys = {
          new SurveyJSON("1", "1", "Flo", "Description", true, "Name", 0,0,0,new String[0], 0),
                new SurveyJSON("1", "1", "Flo", "Description", true, "Name", 0,0,0,new String[0], 0)
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HomeAdapter(surveys));


    }

}