package com.example.surveyer.backend.Util;


import android.net.Uri;

import com.example.surveyer.BuildConfig;

import okhttp3.HttpUrl;

public final class Constants {

    private static final String HTTP_SCHEME = "http", SOCKET_SCHEME = "ws://";
    private static final String HOST = "141.69.97.24:3000";
    private static final String HTTP_VERSION = "v1";


    public static Uri getSocketUrl() {
        return new Uri.Builder()
                .scheme(SOCKET_SCHEME)
                .authority(HOST)
                .build();
    }

    public static String getHttpUrl() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(HTTP_SCHEME)
                .host(HOST)
                .addPathSegment(HTTP_VERSION)
                .addPathSegment("users")
                .build();
        return httpUrl + "/";
    }

    public static String buildHttpUrlWithAppendPaths(String paths) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(HTTP_SCHEME)
                .host(HOST)
                .addPathSegment(HTTP_VERSION)
                .addPathSegment("users")
                .addPathSegments(paths).build();
        return httpUrl.toString();
    }
}