package com.example.surveyer;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class WebSocketHelper {

    public static String getWifiIp(Context context) {
        System.out.println("Get Wifi IP");
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String returnString =  "ws://" + Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":3000";
        System.out.println(returnString);
        returnString = "ws://141.69.97.10:3000";
        return returnString;
    }

}
