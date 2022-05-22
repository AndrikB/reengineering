package com.example.labyrinth;

public enum Cell {
    CAN_MOVE_TO(true),
    WALL(true),
    NOT_VISITED_YET(false),
    NOT_PERMANENT_WALL(false);

    private final boolean isPermanent;

    Cell(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public boolean isPermanent() {
        return isPermanent;
    }
}
