package com.example.krestikinoliki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class GameEndActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        setWinInformation();
    }

    public void selectMenu(View view) {
        playButtonSound();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishActivity(0);
    }

    public void selectRestart(View view) {
        playButtonSound();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finishActivity(0);
    }

    private void playButtonSound() {
        MediaPlayer musicPlayer = MediaPlayer.create(GameEndActivity.this, R.raw.button_press);
        musicPlayer.start();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setWinInformation() {
        ImageView imageInfo = findViewById(R.id.imageInfo);
        TextView textInfo = findViewById(R.id.textInfo);

        Drawable icon;
        String text;
        int audioFile = R.raw.game_won;

        switch (GameSettings.WinInfo) {
            case Cross:
                icon = getDrawable(R.drawable.tiktok_krest);
                text = getString(R.string.cross_win);
                break;
            case Zero:
                icon = getDrawable(R.drawable.zero);
                text = getString(R.string.zero_win);
                break;
            case Human:
                icon = getDrawable(R.drawable.human);
                text = getString(R.string.human_win);
                break;
            case Bot:
                icon = getDrawable(R.drawable.ai);
                text = getString(R.string.ai_win);
                audioFile = R.raw.game_lose;
                break;
            default:
                icon = getDrawable(R.drawable.no_one);
                audioFile = R.raw.game_no_one;
                text = getString(R.string.no_one_win);
                break;
        }

        MediaPlayer musicPlayer = MediaPlayer.create(GameEndActivity.this, audioFile);
        musicPlayer.start();

        imageInfo.setImageDrawable(icon);
        textInfo.setText(text);
    }
}