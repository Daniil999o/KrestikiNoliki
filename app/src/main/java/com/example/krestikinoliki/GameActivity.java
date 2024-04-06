package com.example.krestikinoliki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Field _gameField;
    private MediaPlayer _musicPlayer;


    private boolean _sideSet;
    private GameSettings.GameSide _currentGameSide;
    private GameSettings.GameSide _playerSide;

    private boolean _gameEnd;

    private ImageView _sideInfoIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _gameEnd = false;

        setContentView(R.layout.activity_game);

        _sideInfoIcon = findViewById(R.id.sideIcon);

        playMusic();
        createField();
        changeGameSide();
    }

    public void selectMenu(View view) {
        if (_gameEnd) return;

        _gameEnd = true;

        if (_musicPlayer != null && _musicPlayer.isPlaying())
            _musicPlayer.stop();

        playButtonSound();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishActivity(0);
    }

    public void setPosition(View view) {
        playButtonSound();

        if (GameSettings.SelectedGameType == GameSettings.GameType.Game_Bot && _currentGameSide != _playerSide)
            return;

        boolean allOk = _gameField.placeObject((ImageView) view, _currentGameSide);

        if (allOk)
            changeGameSide();
    }

    public void changeGameSide() {
        if (_gameField.isEnd())
            getWinners();

        if (!_sideSet) {
            _currentGameSide = GameSettings.getRandomSide();

            if (GameSettings.SelectedGameType == GameSettings.GameType.Game_Bot)
                _playerSide = GameSettings.getRandomSide();

            _sideSet = true;
        } else {
            _currentGameSide = _currentGameSide == GameSettings.GameSide.Cross ? GameSettings.GameSide.Zero : GameSettings.GameSide.Cross;
        }

        if (GameSettings.SelectedGameType == GameSettings.GameType.Game_Bot && _currentGameSide != _playerSide) {
            int timer = (int)(GameSettings.BOT_THINK_TIMER * NormalRandom.range(0.1f, 1.1f) * 1000f);

            if (NormalRandom.getChance(0.3f))
                timer *= NormalRandom.range(1, 3);

            new Handler().postDelayed(this::botPlace, timer);
        }

        updateInfoIcon();
    }

    private void botPlace() {
        _gameField.placeOnPosition(BotAI.getSolution(_gameField, _playerSide, _currentGameSide), _currentGameSide);
        changeGameSide();
    }

    private void playButtonSound() {
        MediaPlayer musicPlayer = MediaPlayer.create(GameActivity.this, R.raw.button_press);
        musicPlayer.start();
    }

    private void getWinners() {
        if (_gameEnd) return;

        _gameEnd = true;

        GameSettings.GameSide winner = _gameField.getWinner();

        if (GameSettings.SelectedGameType == GameSettings.GameType.Game_Bot) {
            GameSettings.WinInfo = winner == _playerSide ? GameSettings.GameWin.Human
                    : winner != GameSettings.GameSide.None ? GameSettings.GameWin.Bot
                    : GameSettings.GameWin.None;
        } else {
            GameSettings.WinInfo = winner == GameSettings.GameSide.Cross ? GameSettings.GameWin.Cross
                    : winner == GameSettings.GameSide.Zero ? GameSettings.GameWin.Zero
                    : GameSettings.GameWin.None;
        }

        if (_musicPlayer != null && _musicPlayer.isPlaying())
            _musicPlayer.stop();

        Intent intent = new Intent(this, GameEndActivity.class);
        startActivity(intent);
        this.finishActivity(0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateInfoIcon() {
        int drawableId;

        if (GameSettings.SelectedGameType == GameSettings.GameType.Game_Bot)
            drawableId = _currentGameSide == _playerSide ? R.drawable.human : R.drawable.ai;
        else
            drawableId = _currentGameSide == GameSettings.GameSide.Cross ? R.drawable.tiktok_krest : R.drawable.zero;

        _sideInfoIcon.setImageDrawable(getDrawable(drawableId));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createField() {
        ArrayList<ImageView> images = new ArrayList<>();

        //region GOVNO_CODE

        images.add(findViewById(R.id.pos0x0));
        images.add(findViewById(R.id.pos1x0));
        images.add(findViewById(R.id.pos2x0));
        images.add(findViewById(R.id.pos0x1));
        images.add(findViewById(R.id.pos1x1));
        images.add(findViewById(R.id.pos2x1));
        images.add(findViewById(R.id.pos0x2));
        images.add(findViewById(R.id.pos1x2));
        images.add(findViewById(R.id.pos2x2));

        //endregion GOVNO_CODE

        _gameField = new Field(Vector2.square(3), images);

        FieldStyle style = new FieldStyle(getDrawable(R.drawable.zero),
                getDrawable(R.drawable.tiktok_krest),
                getDrawable(R.color.transparent));

        _gameField.setStyle(style);

        _gameField.updateField();
    }

    private void playMusic() {
        _musicPlayer = MediaPlayer.create(GameActivity.this, NormalRandom.choice(R.raw.music0, R.raw.music1, R.raw.music2));

        _musicPlayer.setLooping(true);
        _musicPlayer.start();
    }
}