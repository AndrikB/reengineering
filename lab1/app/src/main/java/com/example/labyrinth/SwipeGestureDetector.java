package com.example.labyrinth;

import android.view.GestureDetector;
import android.view.MotionEvent;

import static java.lang.StrictMath.max;


public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 50;

    private final Screen activity;

    SwipeGestureDetector(Screen activity) {
        this.activity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaY = e2.getY() - e1.getY();
        float deltaX = e2.getX() - e1.getX();

        float deltaYAbs = Math.abs(deltaY);
        float deltaXAbs = Math.abs(deltaX);
        if (max(deltaXAbs, deltaYAbs) < SWIPE_MIN_DISTANCE)
            return false;

        activity.move(selectDirection(deltaY, deltaX, deltaYAbs, deltaXAbs));

        return true;
    }

    private Direction selectDirection(float deltaY, Float deltaX, float deltaYAbs, float deltaXAbs) {
        if (deltaXAbs > deltaYAbs) {
            return deltaX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return deltaY > 0 ? Direction.DOWN : Direction.UP;
        }
    }
}
