package com.example.surveyer.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
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
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.session.Session;
import com.google.gson.JsonObject;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    Button button, openSession;
    DashboardViewModel dashboardViewModel;
    SocketLiveData socketLiveData;
    ArrayList<SessionJSON> session = new ArrayList<>();
    ArrayList<SurveyJSON> survey = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.sessionHolder);
        button = view.findViewById(R.id.session_start_button);
        openSession = view.findViewById(R.id.open_session);
        dashboardViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(DashboardViewModel.class);
        socketLiveData = dashboardViewModel.getSocketLiveData();
        socketLiveData.observe(requireActivity(), socketEventModelObserver);
        socketLiveData.connect();
        openSession.setOnClickListener(v -> scanQRCode());

        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Session.class);
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DashboardAdapter(session));
        getAllSessions();
    }

    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan QR code");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    final ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Scan Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Beitreten", (dialog, which)-> joinSession(result.getContents()));
            builder.setNegativeButton("Abbrechen", (dialog, which)->{
                dialog.cancel();
            });
            builder.show();
        }
    });

    private void joinSession(String contents) {
        JsonObject obj = new JsonObject();
        obj.addProperty("sessionId", contents);
        obj.addProperty("uid", PreferenceUtil.getDeviceId());
        socketLiveData.sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_JOINSESSION, obj)));
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        dashboardViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSESSIONSANDSUREYS, object)));
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(DashboardFragment.class, "getSocket: " + socketEventModel.toString());
        String toJSONString = socketEventModel.getPayloadAsString();
        try {
            JSONObject jsonObject = new JSONObject(toJSONString);
            if (jsonObject.has("surveys") && jsonObject.has("sessions")) {
                session = SurveyHelper.getSessionAndSurveyFromObject(jsonObject);
                for (SessionJSON s : session) {
                    DebugUtil.debug(DashboardFragment.class, s.toString());
                    if(s.surveyArray != null) {
                       System.out.println("Lenght: " + s.surveyArray.length);
                    }
                }
                recyclerView.setAdapter(new DashboardAdapter(session));
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    };
}