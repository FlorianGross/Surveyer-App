package com.example.surveyer.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.ui.session.Session;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    Button button;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.sessionHolder);
        button = view.findViewById(R.id.session_start_button);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Session.class);
            startActivity(intent);
        });
        SessionJSON session = new SessionJSON("1", "1", new String[]{"1"}, new String[]{"1"}, true, 1);
        SessionJSON[] sessions = {
                session, session
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DashboardAdapter(sessions));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}