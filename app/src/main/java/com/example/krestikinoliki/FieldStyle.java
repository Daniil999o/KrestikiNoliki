package com.example.krestikinoliki;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

public class FieldStyle {
    public Map<GameSettings.GameSide, Drawable> sideDrawables;

    public FieldStyle(Drawable zero, Drawable cross, Drawable none) {
        sideDrawables = new HashMap<>();

        sideDrawables.put(GameSettings.GameSide.Zero, zero);
        sideDrawables.put(GameSettings.GameSide.Cross, cross);
        sideDrawables.put(GameSettings.GameSide.None, none);
    }
}
