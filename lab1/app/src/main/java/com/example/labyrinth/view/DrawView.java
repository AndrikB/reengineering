package com.example.labyrinth.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;
import android.view.View;

import static java.lang.Math.min;

import com.example.labyrinth.Cell;
import com.example.labyrinth.Labyrinth;
import com.example.labyrinth.Type;

@SuppressLint("ViewConstructor")
public abstract class DrawView extends View {

    protected final Size countCell;

    private final float cellWidth;
    private final float cellHeight;

    protected Point heroPoint;

    private final Paint paintField = new Paint();
    private final Paint paintHero = new Paint();
    private final Paint paintLight = new Paint();

    protected Labyrinth labyrinth;

    public static DrawView of(Context context, Point screenSize, Size countCell, Type type){
        switch (type){
            case Hard: return new HardDrawView(context, screenSize, countCell);
            case Classic: return new ClassicDrawView(context, screenSize, countCell);
            case Medium: return new MediumDrawView(context, screenSize, countCell);
            default: throw new RuntimeException(type + " not found");
        }
    }

    protected DrawView(Context context, Point screenSize, Size countCell) {
        super(context);

        paintField.setColor(Color.GRAY);
        paintHero.setColor(Color.RED);
        paintLight.setColor(Color.YELLOW);
        this.setBackgroundColor(Color.BLACK);

        this.countCell = countCell;
        this.cellWidth = ((float) screenSize.x) / countCell.getWidth();
        this.cellHeight = ((float) screenSize.y) / countCell.getHeight();
    }

    @Override
    public final void onDraw(Canvas canvas) {
        for (int i = 0; i < countCell.getHeight(); i++)
            for (int j = 0; j < countCell.getWidth(); j++)
                drawCellIfNeeded(i, j, canvas);

        canvas.drawCircle((heroPoint.x + 0.5f) * cellWidth, (heroPoint.y + 0.5f) * cellHeight,
                min(cellWidth, cellHeight) / 2,
                paintHero);
    }

    protected abstract void drawCellIfNeeded(int i, int j, Canvas canvas);

    protected void drawRect(int i, int j, Canvas canvas) {
        Paint paint = labyrinth.elementAt(new Point(j, i)) == Cell.WALL ? paintField : paintLight;
        canvas.drawRect(j * cellWidth, i * cellHeight,
                (j + 1) * cellWidth, (i + 1) * cellHeight,
                paint);
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    public void restart(){}

    public void setHero(Point heroPoint) {
        this.heroPoint = heroPoint;
    }
}
