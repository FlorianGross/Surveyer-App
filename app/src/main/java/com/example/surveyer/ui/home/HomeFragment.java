package com.example.surveyer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.json.UserJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.Onboarding.Login;
import com.example.surveyer.ui.Onboarding.LoginViewModel;

import com.google.gson.JsonObject;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    SurveyJSON[] surveys;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        homeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(HomeViewModel.class);
        homeViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        homeViewModel.getSocketLiveData().connect();
        getSurveys();
        surveys = new SurveyJSON[]{
          new SurveyJSON(),
                new SurveyJSON(),
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HomeAdapter(surveys));
    }

    private void getSurveys() {try {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        homeViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSURVEYS, object)));
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(Login.class, "New Socket event: " + socketEventModel.toString());

    };

}