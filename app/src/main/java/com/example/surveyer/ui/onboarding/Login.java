package com.example.surveyer.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.UserJSON;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.WebSocketHelper;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.ui.Navigations;

public class Login extends Fragment {
    SocketLiveData socketLiveData;
    EditText editPassword, editUsername;
    Button login, register, anonymous;

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
        socketLiveData = SocketLiveData.get();

        anonymous.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Navigations.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            try {
                socketLiveData.sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, WebSocketHelper.loginUser(new UserJSON(username, password))));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            });
        register.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Register()).commit();
        });
    }

}