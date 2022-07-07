package com.floriang.surveyer.backend.util;

import android.util.Log;

public final class DebugUtil {
    @SuppressWarnings("rawtypes")
    public static void debug(Class caller, String message) {
        Log.d(caller.getSimpleName(), message);
    }
}
