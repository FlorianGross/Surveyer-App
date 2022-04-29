package com.example.surveyer.ui.onboarding;

import android.content.Intent;
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

import com.example.surveyer.R;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.UserJSON;
import com.example.surveyer.backend.models.pojo.SocketAnswerModel;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.ui.Navigations;


public class Login extends Fragment {
    EditText editPassword, editUsername;
    Button login, register, anonymous;
    LoginViewModel loginViewModel;
    boolean isLoggedIn = false;

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
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(LoginViewModel.class);
        loginViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        loginViewModel.getSocketLiveData().connect();

        anonymous.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Navigations.class);
            startActivity(intent);
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
                    isLoggedIn = true;
                    System.out.println("Success");
                    onSuccess();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}