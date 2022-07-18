package com.floriang.surveyer.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.floriang.surveyer.R;
import com.floriang.surveyer.backend.SocketLiveData;
import com.floriang.surveyer.backend.helper.SurveyHelper;
import com.floriang.surveyer.backend.json.PayloadJSON;
import com.floriang.surveyer.backend.json.SessionJSON;
import com.floriang.surveyer.backend.models.pojo.SocketEventModel;
import com.floriang.surveyer.backend.util.DebugUtil;
import com.floriang.surveyer.backend.util.PreferenceUtil;
import com.floriang.surveyer.ui.session.Session;
import com.google.gson.JsonObject;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    Button button, openSession;
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
        recyclerView.setAdapter(new DashboardAdapter(session, dashboardViewModel));
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
            builder.setNegativeButton("Abbrechen", (dialog, which)-> dialog.cancel());
            builder.show();
        }
    });

    private void joinSession(String contents) {
        JsonObject obj = new JsonObject();
        obj.addProperty("sessionId", contents);
        obj.addProperty("uid", PreferenceUtil.getDeviceId());
        socketLiveData.sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_JOINSESSION, obj), SocketEventModel.LOC_DASHBOARD));
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        dashboardViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSESSIONSANDSUREYS, object), SocketEventModel.LOC_DASHBOARD));
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        if (Objects.equals(socketEventModel.getLocation(), SocketEventModel.LOC_DASHBOARD) || socketEventModel.getLocation() == null) {
            DebugUtil.debug(DashboardFragment.class, "getSocket: " + socketEventModel);
            String toJSONString = socketEventModel.getPayloadAsString();
            try {
                JSONObject jsonObject = new JSONObject(toJSONString);
                if (jsonObject.has("sessions")) {
                    session = SurveyHelper.getSessionListFromJSONArray(jsonObject.getJSONArray("sessions"));
                    recyclerView.setAdapter(new DashboardAdapter(session, dashboardViewModel));
                } else if (jsonObject.has("session")) {
                    Toast.makeText(requireContext(), "Session erfolgreich beigetreten", Toast.LENGTH_SHORT).show();
                    getAllSessions();
                } else if (jsonObject.getString("type").equals("Refresh")) {
                    System.out.println("Refresh");
                    getAllSessions();
                }
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        }
    };
}