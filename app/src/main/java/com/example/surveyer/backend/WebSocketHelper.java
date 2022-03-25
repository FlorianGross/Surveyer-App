package com.example.surveyer.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.text.format.Formatter;

import androidx.annotation.NonNull;

import com.example.surveyer.AdminView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketHelper {
    private static WebSocketHelper INSTANCE;
    public WebSocket webSocket;
    public WebSocketListener listener;
    public String sessionID = "", userID = "";

    public static WebSocketHelper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new WebSocketHelper();
        }
        return INSTANCE;
    }

    public static String getWifiIp(Context context) {
        System.out.println("Get Wifi IP");
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String returnString =  "ws://" + Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":3000";
        System.out.println(returnString);
        returnString = "ws://141.69.101.17:3000";
        return returnString;
    }

    public void connectToSocket(Context context){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getWifiIp(context)).build();
        webSocket = client.newWebSocket(request, new MyListener(context));
    }

    public void registerUser(){
        try {
            JSONObject answer = new JSONObject();
            answer.put("Type", "registerUser");
            webSocket.send(answer.toString());
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void initSession() {
        System.out.println("Init Session");
        try {
            JSONObject jo = new JSONObject();
            jo.put("Type", "startSession");
            webSocket.send(jo.toString());
            System.out.println(jo);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void closeSession(){

    }

    public String startSurvey(){
        return "";

    }


    private static class MyListener extends WebSocketListener{
        public MyListener(Context context) {
            this.context = context;
        }

        Context context;
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(text);
                System.out.println(jsonObject);
                useJSON(jsonObject, context);
            }catch (Exception e){
                System.out.println("Error parsing JSONObject: " + e);
            }
        }

        private void useJSON(JSONObject jsonObject, Context context) throws JSONException {
            switch(jsonObject.getString("Type")){
                case "Answer":{
                    if (jsonObject.getString("Result").equals("Client Registered Successful")) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        prefs.edit().putString("uid", jsonObject.getString("uid")).apply();
                    }
                    break;
                }
                case "startSession": WebSocketHelper.getInstance().initSession(); break;
                default:System.out.println(jsonObject.toString()); break;
            }
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
        }
    }
}
