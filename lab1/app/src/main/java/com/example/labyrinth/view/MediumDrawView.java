package com.example.labyrinth.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Size;

import com.example.labyrinth.Labyrinth;

@SuppressLint("ViewConstructor")
public class MediumDrawView extends DrawView {
    private boolean[][] cellWasVisited;

    public MediumDrawView(Context context, Point screenSize, Size countCell) {
        super(context, screenSize, countCell);
        restart();
    }

    @Override
    protected void drawCellIfNeeded(int i, int j, Canvas canvas) {
        if (cellWasVisited[i][j])
            drawRect(i, j, canvas);
    }

    @Override
    public void restart() {
        this.cellWasVisited = new boolean[countCell.getHeight()][countCell.getWidth()];
    }

    @Override
    public void setHero(Point heroPoint) {
        super.setHero(heroPoint);
        setCellAndNeighbourWasVisited(heroPoint);
    }

    @Override
    public void setLabyrinth(Labyrinth labyrinth) {
        super.setLabyrinth(labyrinth);
        setCellAndNeighbourWasVisited(labyrinth.getExitPoint());
    }

    private void setCellAndNeighbourWasVisited(Point point) {
        setCellAndNeighbourWasVisited(point.x, point.y);
    }

    private void setCellAndNeighbourWasVisited(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                safetySetCellWasVisited(i, j);
            }
        }
    }

    private void safetySetCellWasVisited(int i, int j) {
        if (i >= 0 && j >= 0 && i < countCell.getWidth() && j < countCell.getHeight())
            cellWasVisited[j][i] = true;
    }
}
