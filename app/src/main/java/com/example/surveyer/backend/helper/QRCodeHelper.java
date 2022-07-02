package com.example.surveyer.backend.helper;

public class QRCodeHelper {
    public static String generateBarCode(String text) {
        return "https://chart.googleapis.com/chart?cht=qr&chs=300x300&chl=" + text;
    }
}
