package com.example.krestikinoliki;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Combination {
    public Vector2[] positions;

    public Combination(Vector2... positions) {
        this.positions = positions;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + TextUtils.join(", ", positions) + ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Combination other = (Combination) obj;

        if (other.positions.length != positions.length)
            return false;

        for (int i = 0; i < positions.length; i++) {
            if (!positions[i].equals(other.positions[i]))
                return false;
        }

        return true;
    }
}
