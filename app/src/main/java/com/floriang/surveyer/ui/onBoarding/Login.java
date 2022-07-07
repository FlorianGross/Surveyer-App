package com.floriang.surveyer.ui.onBoarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.floriang.surveyer.R;
import com.floriang.surveyer.backend.json.PayloadJSON;
import com.floriang.surveyer.backend.json.UserJSON;
import com.floriang.surveyer.backend.models.pojo.SocketAnswerModel;
import com.floriang.surveyer.backend.models.pojo.SocketEventModel;
import com.floriang.surveyer.backend.util.DebugUtil;
import com.floriang.surveyer.backend.util.PreferenceUtil;
import com.floriang.surveyer.ui.Navigations;


public class Login extends Fragment {
    EditText editPassword, editUsername, editShownName;
    Button login, register, anonymous;
    LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPassword = view.findViewById(R.id.passwordInput);
        editUsername = view.findViewById(R.id.usernameInput);
        login = view.findViewById(R.id.registerButton);
        register = view.findViewById(R.id.loginButton);
        anonymous = view.findViewById(R.id.anonymButton);
        editShownName = view.findViewById(R.id.shownNameInput_login);
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(LoginViewModel.class);
        loginViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        loginViewModel.getSocketLiveData().connect();

        anonymous.setOnClickListener(v -> {
            String shownName = editShownName.getText().toString();
            if(shownName.isEmpty()){
            shownName = "Anonymous";
            }
            loginViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_REGISTER, new UserJSON(shownName))));
        });

        login.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            try {
                loginViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_LOGIN, new UserJSON(username, password))));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        register.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Register()).commit());
    }

    private void onSuccess(){
        startActivity(Navigations.getNavigationIntent(getActivity()));
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(Login.class, "New Socket event: " + socketEventModel.toString());
        handleMessage(socketEventModel);
    };

    private void handleMessage(SocketEventModel socketEventModel) {
        if (socketEventModel.getType() == SocketEventModel.TYPE_OUTGOING) {
            return;
        }
        try {
            if (socketEventModel.getEvent().equals(SocketEventModel.EVENT_MESSAGE)) {
                SocketAnswerModel model = SocketAnswerModel.fromJson(socketEventModel.getPayloadAsString(), SocketAnswerModel.class);
                if (model.getResult().equals("Success")) {
                    PreferenceUtil.setDeviceId(model.getUid());
                    System.out.println("Success");
                    onSuccess();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}