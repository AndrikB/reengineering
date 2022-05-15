package com.example.labyrinth;

import android.graphics.Point;
import android.util.Size;

import java.util.function.Consumer;

public class GameLogic {
    private final Size size;
    private final Types type;
    private final long seed;
    private Point heroPoint = new Point();
    private Labyrinth labyrinth;
    private Consumer<Point> heroPositionChangeListener = (point -> {});

    public GameLogic(Size size, Types type) {
        this(size, type, System.currentTimeMillis());
    }

    public GameLogic(Size size, Types type, long seed) {
        this.size = size;
        this.type = type;
        this.seed = seed;
        restart();
    }

    public void setOnHeroPositionChangeListener(Consumer<Point> heroPositionChangeListener) {
        this.heroPositionChangeListener = heroPositionChangeListener;
    }

    public void restart() {
        heroPoint.set(1, 1);
        this.labyrinth = LabyrinthGenerator.generate(size.getWidth(), size.getHeight(), seed);
    }

    public final boolean couldTurnMove(){
        if (type == Types.Hard) return false;
        int countFreeCells=0;
        for (Direction direction : Direction.values()) {
            if (labyrinth.elementAt(direction.updatePoint(new Point(heroPoint))) == Cell.CAN_MOVE_TO)
                countFreeCells++;
        }
        return (countFreeCells<3);
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public Point getHeroPoint() {
        return heroPoint;
    }

    public void move(Direction direction) {
        Point nextItem = new Point(heroPoint);
        do {
            direction.updatePoint(nextItem);

            if (labyrinth.elementAt(nextItem) == Cell.CAN_MOVE_TO) {
                moveHeroToNewPoint(nextItem);
            } else {
                return;
            }
        } while (couldTurnMove() && !checkWin());
    }

    private void moveHeroToNewPoint(Point nextItem) {
        heroPoint = new Point(nextItem);
        heroPositionChangeListener.accept(heroPoint);
    }

    public boolean checkWin() {
        return heroPoint.equals(labyrinth.getExitPoint());
    }
}
