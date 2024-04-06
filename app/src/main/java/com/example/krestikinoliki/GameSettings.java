package com.example.krestikinoliki;

public class GameSettings {
    public enum GameType {
        Game_1x1,
        Game_Bot
    }

    public enum GameSide {
        Zero,
        Cross,
        None
    }

    public enum GameWin {
        Cross,
        Zero,
        Human,
        Bot,
        None
    }


    public static GameType SelectedGameType;

    public static GameWin WinInfo;

    public static GameSide getRandomSide() {
        return NormalRandom.choice(GameSettings.GameSide.Cross, GameSettings.GameSide.Zero);
    }

    public static final float BOT_THINK_TIMER = 2;

    public static float BotDifficulty = 0.5f;

    private static final int BOT_DIFFICULTY_MODIFIER = 100;

    public static int getBotDifficulty() {
        return (int)(BotDifficulty * BOT_DIFFICULTY_MODIFIER);
    }

    public static final Combination[] WIN_COMBINATIONS = new Combination[] {
            new Combination(new Vector2(0, 0), new Vector2(0, 1), new Vector2(0, 2)),
            new Combination(new Vector2(1, 0), new Vector2(1, 1), new Vector2(1, 2)),
            new Combination(new Vector2(2, 0), new Vector2(2, 1), new Vector2(2, 2)),

            new Combination(new Vector2(0, 0), new Vector2(1, 0), new Vector2(2, 0)),
            new Combination(new Vector2(0, 1), new Vector2(1, 1), new Vector2(2, 1)),
            new Combination(new Vector2(0, 2), new Vector2(1, 2), new Vector2(2, 2)),

            new Combination(new Vector2(0, 0), new Vector2(1, 1), new Vector2(2, 2)),
            new Combination(new Vector2(2, 0), new Vector2(1, 1), new Vector2(0, 2)),
    };
}