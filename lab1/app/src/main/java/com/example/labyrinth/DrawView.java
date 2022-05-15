package com.example.labyrinth;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;
import android.view.View;

import static java.lang.Math.min;

enum Type {
    Classic, Hard
}

@SuppressLint("ViewConstructor")
public class DrawView extends View {

    private final Type type;
    private final Size countCell;

    private final float cellWidth;
    private final float cellHeight;

    private Point heroPoint;

    private final Paint paintField = new Paint();
    private final Paint paintHero = new Paint();
    private final Paint paintLight = new Paint();

    private Labyrinth labyrinth;

    public DrawView(Context context, Point screenSize, Size countCell, Type type) {
        super(context);

        paintField.setColor(Color.GRAY);
        paintHero.setColor(Color.RED);
        paintLight.setColor(Color.YELLOW);
        this.setBackgroundColor(Color.BLACK);

        this.countCell = countCell;
        this.type = type;
        this.cellWidth = ((float) screenSize.x) / countCell.getWidth();
        this.cellHeight = ((float) screenSize.y) / countCell.getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (labyrinth.getExitPoint().x < 2 && labyrinth.getExitPoint().y < 2) return;

        if (type == Type.Hard) {
            for (int i = 0; i < countCell.getHeight(); i++)
                for (int j = 0; j < countCell.getWidth(); j++)
                    if (Math.min(Math.pow(heroPoint.x - j, 2.0) + Math.pow(heroPoint.y - i, 2),
                            Math.pow(labyrinth.getExitPoint().x - j, 2.0) + Math.pow(labyrinth.getExitPoint().y - i, 2)) < 6)
                        drawRect(i, j, canvas);
        } else if (type == Type.Classic) {
            for (int i = 0; i < countCell.getHeight(); i++)
                for (int j = 0; j < countCell.getWidth(); j++)
                    drawRect(i, j, canvas);
        }

        canvas.drawCircle((heroPoint.x + 0.5f) * cellWidth, (heroPoint.y + 0.5f) * cellHeight,
                    min(cellWidth, cellHeight) / 2,
                    paintHero);
    }

    private void drawRect(int i, int j, Canvas canvas) {
        Paint paint = labyrinth.elementAt(new Point(j, i)) == Cell.WALL ? paintField : paintLight;
        canvas.drawRect(j * cellWidth, i * cellHeight,
                (j + 1) * cellWidth, (i + 1) * cellHeight,
                paint);
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    public void setHero(Point heroPoint) {
        this.heroPoint = heroPoint;
    }
}
