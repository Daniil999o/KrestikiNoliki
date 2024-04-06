package com.example.krestikinoliki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView _difficultyInfo;
    private SeekBar _difficultyBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        _difficultyInfo = findViewById(R.id.currentDifficultyInfo);
        _difficultyBar = findViewById(R.id.difficultyBar);

        _difficultyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                playButtonSound();
                updateProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        loadSettings();
    }

    public void select1x1(View view) {
        playButtonSound();
        goToGame(GameSettings.GameType.Game_1x1);
    }

    public void selectBot(View view) {
        playButtonSound();
        goToGame(GameSettings.GameType.Game_Bot);
    }

    public void selectExit(View view) {
        playButtonSound();
        this.finishAffinity();
    }

    private void playButtonSound() {
        MediaPlayer musicPlayer = MediaPlayer.create(MainActivity.this, R.raw.button_press);
        musicPlayer.start();
    }

    private void goToGame(GameSettings.GameType gameType) {
        GameSettings.SelectedGameType = gameType;

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finishActivity(0);
    }

    private void saveSettings() {
        SharedPreferences sharedPref = getPreferences(GameActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("BOT_DIFFICULTY", GameSettings.BotDifficulty);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void loadSettings() {
        SharedPreferences sharedPref = getPreferences(GameActivity.MODE_PRIVATE);
        GameSettings.BotDifficulty = sharedPref.getFloat("BOT_DIFFICULTY", 0.5f);

        _difficultyBar.setProgress((int)(GameSettings.BotDifficulty * 100));
        _difficultyInfo.setText(getString(R.string.bot_difficulty) + ": " + (int)(GameSettings.BotDifficulty * 100));
    }

    private void updateProgress() {
        GameSettings.BotDifficulty = _difficultyBar.getProgress() / 100f;

        saveSettings();
        loadSettings();
    }
}