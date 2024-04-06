package com.example.krestikinoliki;

import android.util.Log;

public class Debug {
    public static void log(Object message) {
        Log.d("DEBUG", message.toString());
    }
}
