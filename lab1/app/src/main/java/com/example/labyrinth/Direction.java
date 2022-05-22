package com.example.labyrinth;

import android.graphics.Point;

import java.util.function.Consumer;

public enum Direction {
    UP(point -> point.y--),
    LEFT(point -> point.x--),
    RIGHT(point -> point.x++),
    DOWN(point -> point.y++);

    private final Consumer<Point> direction;

    Direction(Consumer<Point> direction) {
        this.direction = direction;
    }

    public Point updatePoint(Point point) {
        direction.accept(point);
        return point;
    }

}
