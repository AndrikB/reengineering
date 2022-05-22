package com.example.labyrinth.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Size;

@SuppressLint("ViewConstructor")
public class ClassicDrawView extends DrawView {
    public ClassicDrawView(Context context, Point screenSize, Size countCell) {
        super(context, screenSize, countCell);
    }

    @Override
    protected void drawCellIfNeeded(int i, int j, Canvas canvas) {
        drawRect(i, j, canvas);
    }
}
