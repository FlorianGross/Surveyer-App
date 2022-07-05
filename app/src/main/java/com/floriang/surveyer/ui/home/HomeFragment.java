package com.floriang.surveyer.ui.home;

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

import com.floriang.surveyer.R;
import com.floriang.surveyer.backend.helper.SurveyHelper;
import com.floriang.surveyer.backend.json.PayloadJSON;
import com.floriang.surveyer.backend.json.SurveyJSON;
import com.floriang.surveyer.backend.models.pojo.SocketEventModel;
import com.floriang.surveyer.backend.util.DebugUtil;
import com.floriang.surveyer.backend.util.PreferenceUtil;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    ArrayList<SurveyJSON> surveys;

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
        surveys = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HomeAdapter(surveys));
    }

    private void getSurveys() {try {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        homeViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSURVEYS, object)));
    } catch (Exception e) {
        DebugUtil.debug(HomeFragment.class, e.toString());
    }
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(HomeFragment.class, "New Socket event: " + socketEventModel.toString());
        try{
            JSONObject jsonObject = new JSONObject(socketEventModel.getPayloadAsString());
            if(jsonObject.getString("type").equals("Refresh")){
                System.out.println("Refresh");
                getSurveys();
            }else if(jsonObject.has("surveys") && jsonObject.getString("result").equals("Surveys")){
                JSONArray array = jsonObject.getJSONArray("surveys");
                surveys = SurveyHelper.getSurveyListFromJSONArray(array);
                ArrayList<SurveyJSON> sortedSurveys = new ArrayList<>();
                for(SurveyJSON survey : surveys){
                    if(survey.isSurveyOpened()){
                        if(!Arrays.asList(survey.participants).contains(PreferenceUtil.getDeviceId())){
                            sortedSurveys.add(survey);
                        }
                    }
                }
                if(sortedSurveys.size()> 0) {
                    recyclerView.setAdapter(new HomeAdapter(sortedSurveys));
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    };
}