package com.example.labyrinth;

import android.graphics.Point;

public class Labyrinth {
    private Cell[][]matrix;
    private Point exitPoint;
    public Labyrinth(Cell[][] matrix, Point exitPoint){
        this.matrix=matrix;
        this.exitPoint=exitPoint;
    }

    public Cell elementAt(Point p){
        return matrix[p.y][p.x];
    }

    public Point getExitPoint() {
        return exitPoint;
    }

    public Point getSize(){
        return new Point(matrix[0].length,matrix.length);
    }
}
