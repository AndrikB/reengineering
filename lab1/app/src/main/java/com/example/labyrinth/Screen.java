package com.example.labyrinth;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.core.view.GestureDetectorCompat;

public class Screen extends Activity {

    Point displaySize = new Point();

    private DrawView view;
    private GameLogic game;

    private GestureDetectorCompat gestureDetectorCompat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Types type = (Types) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        Size size = getFieldSize();
        game = new GameLogic(size, type);

        view = new DrawView(this, displaySize, size, type);
        setContentView(view);
        startNewGame();

        SwipeGestureDetector gestureListener = new SwipeGestureDetector(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
    }

    private Size getFieldSize() {
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(displaySize);
        int squareSize = 30;
        int width = displaySize.x / squareSize;
        int height = displaySize.y / squareSize;
        return new Size(width, height);
    }


    public void move(Move move) {
        Point lastHeroPoint;
        do {
            lastHeroPoint = game.getHeroPoint();
            if (game.move(move)) {
                openDialog();
            }
            view.setHero(game.getHeroPoint());
            view.invalidate();
        } while (lastHeroPoint != game.heroPoint && game.couldNextMove());

        view.setHero(game.getHeroPoint());
        view.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    public void startNewGame() {
        game.restart();
        view.setLabyrinth(game.getLabyrinth());
        view.setHero(game.getHeroPoint());
        view.invalidate();
    }

    public void returnToMainMenu() {
        finish();
    }

    public void openDialog() {
        AlertDialogWindow.showDialogWindow(this);
    }
}
