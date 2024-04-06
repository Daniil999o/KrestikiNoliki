package com.example.krestikinoliki;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Vector2 {
    public int x;
    public int y;

    public int maxX;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Vector2 other = (Vector2) obj;

        return this.x == other.x && this.y == other.y;
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public static Vector2 square(int size) {
        return new Vector2(size, size);
    }

    public static Vector2 error() {
        return new Vector2(-986448738, -758958348);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
