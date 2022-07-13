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
import com.floriang.surveyer.ui.Navigations;

import java.util.Objects;

public class Register extends Fragment {

    EditText editPassword, editUsername, editEmail, editShownName;
    Button login, register;
    RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPassword = view.findViewById(R.id.passwordInput);
        editUsername = view.findViewById(R.id.usernameInput);
        register = view.findViewById(R.id.registerButton2);
        login = view.findViewById(R.id.loginButton2);
        editEmail = view.findViewById(R.id.emailInput);
        editShownName = view.findViewById(R.id.shownNameInput_register);
        registerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(RegisterViewModel.class);
        registerViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        registerViewModel.getSocketLiveData().connect();

        register.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String email = editEmail.getText().toString();
            String shownName = editShownName.getText().toString();
            if(username.isEmpty() || password.isEmpty() || email.isEmpty() || shownName.isEmpty()){
                return;
            }
            registerViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_REGISTER, new UserJSON(username, password, email, shownName, false)), SocketEventModel.LOC_REGISTER));
        });

        login.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit());
    }

    private void onSuccess(){

        startActivity(Navigations.getNavigationIntent(getActivity()));
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        if (Objects.equals(socketEventModel.getLocation(), SocketEventModel.LOC_REGISTER) || socketEventModel.getLocation() == null) {
            DebugUtil.debug(Register.class, "New Socket event: " + socketEventModel.toString());
            handleMessage(socketEventModel);
        }
    };

    private void handleMessage(SocketEventModel socketEventModel) {
        if (socketEventModel.getType() == SocketEventModel.TYPE_OUTGOING) {
            return;
        }
        try {
            if (socketEventModel.getEvent().equals(SocketEventModel.EVENT_MESSAGE)) {
                SocketAnswerModel model = SocketAnswerModel.fromJson(socketEventModel.getPayloadAsString(), SocketAnswerModel.class);
                if (model.getResult().equals("Success")) {
                    System.out.println("Success");
                    onSuccess();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}