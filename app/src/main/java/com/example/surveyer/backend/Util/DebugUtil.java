package com.example.surveyer.backend.Util;

import android.util.Log;

public final class DebugUtil {
    public static void debug(Class caller, String message) {
        Log.d(caller.getSimpleName(), message);
    }

    public static void warn(Class caller, String message) {
        Log.w(caller.getSimpleName(), message);
    }
}
