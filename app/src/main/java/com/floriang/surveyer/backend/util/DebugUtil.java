package com.floriang.surveyer.backend.util;

import android.util.Log;

public final class DebugUtil {
    @SuppressWarnings("rawtypes")
    public static void debug(Class caller, String message) {
        Log.d(caller.getSimpleName(), message);
    }

    @SuppressWarnings("rawtypes")
    public static void warn(Class caller, String message) {
        Log.w(caller.getSimpleName(), message);
    }
}
