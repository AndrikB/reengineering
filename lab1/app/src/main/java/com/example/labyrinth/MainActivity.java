package com.example.labyrinth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class MainActivity extends Activity {
    public static String EXTRA_MESSAGE = "Game type";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.buttonClickEasy)
                .setOnClickListener((view) -> startNewGame(Types.Classic));

        findViewById(R.id.buttonClickHard)
                .setOnClickListener((view) -> startNewGame(Types.Hard));
    }

    private void startNewGame(Types type) {
        Intent intent = new Intent(this, Screen.class);
        intent.putExtra(EXTRA_MESSAGE, type);

        this.startActivity(intent);
    }
}