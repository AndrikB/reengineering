package com.example.labyrinth.game;

import android.graphics.Point;
import android.util.Size;

import com.example.labyrinth.Cell;
import com.example.labyrinth.Direction;
import com.example.labyrinth.Labyrinth;
import com.example.labyrinth.LabyrinthGenerator;
import com.example.labyrinth.Type;

import java.util.function.Consumer;

public abstract class GameLogic {
    private final Size size;
    private long seed;
    private Point heroPoint = new Point();
    private Labyrinth labyrinth;
    private Consumer<Point> heroPositionChangeListener = (point -> {});


    public static GameLogic of(Size size, Type type) {
        return of(size, type, System.currentTimeMillis());
    }

    public static GameLogic of(Size size, Type type, long seed) {
        switch (type) {
            case Hard:
                return new HardGameLogic(size, seed);
            case Classic:
            case Medium:
                return new ClassicGameLogic(size, seed);
            default:
                throw new RuntimeException(type + " not found");
        }
    }

    protected GameLogic(Size size, long seed) {
        this.size = size;
        this.seed = seed;
        restart();
    }

    public void setOnHeroPositionChangeListener(Consumer<Point> heroPositionChangeListener) {
        this.heroPositionChangeListener = heroPositionChangeListener;
    }

    public void restart() {
        heroPoint.set(1, 1);
        this.labyrinth = LabyrinthGenerator.generate(size.getWidth(), size.getHeight(), seed);
        seed = System.currentTimeMillis();
    }

    protected final boolean couldTurnMove() {
        int countFreeCells = 0;
        for (Direction direction : Direction.values()) {
            if (labyrinth.elementAt(direction.updatePoint(new Point(heroPoint))) == Cell.CAN_MOVE_TO)
                countFreeCells++;
        }
        return (countFreeCells < 3);
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
        } while (shouldContinueMove());
    }

    protected boolean shouldContinueMove(){
        return !checkWin() && couldTurnMove();
    }

    private void moveHeroToNewPoint(Point nextItem) {
        heroPoint = new Point(nextItem);
        heroPositionChangeListener.accept(heroPoint);
    }

    public boolean checkWin() {
        return heroPoint.equals(labyrinth.getExitPoint());
    }
}
