package com.example.krestikinoliki;

import kotlin.random.Random;

public class NormalRandom {
    public static int range(int min, int max) {
        if (max <= min) return 0;

        return Random.Default.nextInt(min, max);
    }

    public static float range(float min, float max) {
        if (max <= min) return 0;

        return min + Random.Default.nextFloat() * (max - min);
    }

    public static boolean getChance(float percentage) {
        return range(0.0f, 1.0f) <= percentage;
    }

    @SafeVarargs
    public static <T> T choice(T... objects) {
        return objects[range(0, objects.length)];
    }
}
