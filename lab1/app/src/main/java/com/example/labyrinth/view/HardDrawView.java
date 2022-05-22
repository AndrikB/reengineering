package com.example.labyrinth.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Size;

@SuppressLint("ViewConstructor")
public class HardDrawView extends DrawView {
    public HardDrawView(Context context, Point screenSize, Size countCell) {
        super(context, screenSize, countCell);
    }

    @Override
    protected void drawCellIfNeeded(int i, int j, Canvas canvas) {
        if (Math.min(Math.pow(heroPoint.x - j, 2.0) + Math.pow(heroPoint.y - i, 2),
                Math.pow(labyrinth.getExitPoint().x - j, 2.0) + Math.pow(labyrinth.getExitPoint().y - i, 2)) < 6)
            drawRect(i, j, canvas);
    }
}
