package com.example.labyrinth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.core.view.GestureDetectorCompat;

public class Screen extends Activity
        implements View.OnClickListener{


    Point displaySize=new Point();

    private final int squareSize=30;
    private int width;
    private int height;

    private DrawView view;
    private GameLogic game;
    private Types type=Types.Classic;

    private GestureDetectorCompat gestureDetectorCompat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        this.type=(Types)bundle.getSerializable(MainActivity.EXTRA_MESSAGE);

        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(displaySize);
        width=displaySize.x/squareSize; height=displaySize.y/squareSize;
        game=new GameLogic(width,height, type);

        view=new DrawView(this, displaySize, new Size(width,height));
        setContentView(view);
        view.setGameType(type);
        startNewGame();
        SwipeGestureDetector gestureListener =new SwipeGestureDetector(this);
        gestureDetectorCompat=new GestureDetectorCompat(this, gestureListener);

    }


    public void Move(Move move){
        Point lastHeroPoint;
        do {
            lastHeroPoint=game.getHeroPoint();
            if (game.move(move)){
                openDialog();
            }
            view.setHero(game.getHeroPoint());
            view.invalidate();
        }while (lastHeroPoint!=game.heroPoint&&game.couldNextMove());

        view.setHero(game.getHeroPoint());
        view.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
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

    @Override
    public void onClick(View view) { }


}
