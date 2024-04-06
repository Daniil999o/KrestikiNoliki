package com.example.krestikinoliki;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotAI {
    private static final Map<Combination, Vector2> BotSolutions = new HashMap<Combination, Vector2>() {
        {
            // HORIZONTAL AXIS

            put(new Combination(new Vector2(0, 0), new Vector2(1, 0)), new Vector2(2, 0));
            put(new Combination(new Vector2(1, 0), new Vector2(2, 0)), new Vector2(0, 0));
            put(new Combination(new Vector2(0, 0), new Vector2(2, 0)), new Vector2(1, 0));

            put(new Combination(new Vector2(0, 1), new Vector2(1, 1)), new Vector2(2, 1));
            put(new Combination(new Vector2(1, 1), new Vector2(2, 1)), new Vector2(0, 1));
            put(new Combination(new Vector2(0, 1), new Vector2(2, 1)), new Vector2(1, 1));

            put(new Combination(new Vector2(0, 2), new Vector2(1, 2)), new Vector2(2, 2));
            put(new Combination(new Vector2(1, 2), new Vector2(2, 2)), new Vector2(0, 2));
            put(new Combination(new Vector2(0, 2), new Vector2(2, 2)), new Vector2(1, 2));

            // VERTICAL AXIS

            put(new Combination(new Vector2(0, 0), new Vector2(0, 1)), new Vector2(0, 2));
            put(new Combination(new Vector2(0, 1), new Vector2(0, 2)), new Vector2(0, 0));
            put(new Combination(new Vector2(0, 0), new Vector2(0, 2)), new Vector2(0, 1));

            put(new Combination(new Vector2(1, 0), new Vector2(1, 1)), new Vector2(1, 2));
            put(new Combination(new Vector2(1, 1), new Vector2(1, 2)), new Vector2(1, 0));
            put(new Combination(new Vector2(1, 0), new Vector2(1, 2)), new Vector2(1, 1));

            put(new Combination(new Vector2(2, 0), new Vector2(2, 1)), new Vector2(2, 2));
            put(new Combination(new Vector2(2, 1), new Vector2(2, 2)), new Vector2(2, 0));
            put(new Combination(new Vector2(2, 0), new Vector2(2, 2)), new Vector2(2, 1));

            // CROSS AXIS

            put(new Combination(new Vector2(0, 0), new Vector2(1, 1)), new Vector2(2, 2));
            put(new Combination(new Vector2(1, 1), new Vector2(2, 2)), new Vector2(0, 0));
            put(new Combination(new Vector2(0, 0), new Vector2(2, 2)), new Vector2(1, 1));

            put(new Combination(new Vector2(2, 0), new Vector2(1, 1)), new Vector2(0, 2));
            put(new Combination(new Vector2(0, 2), new Vector2(1, 1)), new Vector2(2, 0));
            put(new Combination(new Vector2(0, 2), new Vector2(2, 0)), new Vector2(1, 1));

//            // IMPOSSIBLE
//
//            put(new Combination(new Vector2(1, 1)), new Vector2(0, 0));
//            put(new Combination(new Vector2(1, 1)), new Vector2(2, 0));
//            put(new Combination(new Vector2(1, 1)), new Vector2(0, 2));
//            put(new Combination(new Vector2(1, 1)), new Vector2(2, 2));
        }
    };

    public static Vector2 getSolution(Field field, GameSettings.GameSide playerSide, GameSettings.GameSide botSide) {
        Vector2 firstSolution = getSideSolution(field, field.getSidePosition(playerSide));
        Vector2 secondSolution = getSideSolution(field, field.getSidePosition(botSide));

        if (!secondSolution.equals(Vector2.error()))
            return secondSolution;
        if (!firstSolution.equals(Vector2.error()))
            return firstSolution;

        if (field.isFree(new Vector2(1, 1)) && NormalRandom.getChance(GameSettings.BotDifficulty))
            return new Vector2(1, 1);

        return getRandomPosition(field);
    }

    private static Vector2 getSideSolution(Field field, Vector2[] positions) {
        if (positions.length <= 0)
            return Vector2.error();

        int tries = GameSettings.getBotDifficulty();

        for (int i = tries; i > 0; i--) {
            Vector2[] currentPositions = positions.length == 1 ? positions
                    : pickRandomVectors(positions, NormalRandom.range(1, positions.length + 1));

            Combination combo = new Combination(currentPositions);

            if (hasCombo(combo)) {
                Vector2 solution = getComboSolution(combo);

                if (field.isFree(solution))
                    return solution;
            }
        }

        return Vector2.error();
    }

    private static Vector2[] pickRandomVectors(Vector2[] array, int n) {
        List<Vector2> list = Arrays.asList(array);
        Collections.shuffle(list);

        Vector2[] answer = new Vector2[n];
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);

        return answer;

    }

    private static Vector2 getRandomPosition(Field field) {
        FieldElement randomElement = field.getRandomFreeElement();
        return randomElement != null ? randomElement.position : Vector2.error();
    }

    private static boolean hasCombo(Combination combo) {
        List<Combination> combinations = Arrays.asList(BotSolutions.keySet().toArray(new Combination[0]));
        Collections.shuffle(combinations);

        for (Combination x : combinations) {
            if (x.equals(combo))
                return true;
        }

        return false;
    }

    private static Vector2 getComboSolution(Combination combo) {
        List<Combination> combinations = Arrays.asList(BotSolutions.keySet().toArray(new Combination[0]));
        Collections.shuffle(combinations);

        for (Combination x : combinations) {
            if (x.equals(combo))
                return BotSolutions.get(x);
        }

        return Vector2.error();
    }
}
