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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.helper.SurveyHelper;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.notifications.Fragment_Survey;
import com.example.surveyer.ui.session.Session;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    Button button;
    DashboardViewModel dashboardViewModel;
    SocketLiveData socketLiveData;
    ArrayList<SessionJSON> session = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.sessionHolder);
        button = view.findViewById(R.id.session_start_button);
        dashboardViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(DashboardViewModel.class);
        socketLiveData = dashboardViewModel.getSocketLiveData();
        socketLiveData.observe(requireActivity(), socketEventModelObserver);
        socketLiveData.connect();


        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Session.class);
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DashboardAdapter(session));
        getAllSessions();
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        dashboardViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSESSIONS, object)));
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(DashboardFragment.class, "getSocket: " + socketEventModel.toString());
        String toJSONString = socketEventModel.getPayloadAsString();
        try {
            JSONObject jsonObject = new JSONObject(toJSONString);
            if (jsonObject.has("events") && jsonObject.getJSONArray("events").length() > 0) {
                ArrayList<SessionJSON> newSession = SurveyHelper.getSessionListFromObject(jsonObject);
                session = newSession;
                recyclerView.setAdapter(new DashboardAdapter(session));
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}