package com.example.labyrinth;

import android.graphics.Point;
import android.util.Size;

import java.util.function.Consumer;

public class GameLogic {
    Types type;
    Point heroPoint=new Point();
    Labyrinth labyrinth;
    LabyrinthGenerator l;
    private Consumer<Point> heroPositionChangeListener = (point -> {});

    public GameLogic(Size size, Types type) {
        this(size, type, System.currentTimeMillis());
    }

    public GameLogic(Size size, Types type, long seed){
        l = new LabyrinthGenerator(size.getWidth(), size.getHeight(), seed);
        this.type = type;
        restart();
    }

    public void setOnHeroPositionChangeListener(Consumer<Point> heroPositionChangeListener){
        this.heroPositionChangeListener = heroPositionChangeListener;
    }

    public void restart(){
        heroPoint.set(1,1);
        l.generate();
        this.labyrinth=l.getLabyrinth();
    }

    public final boolean couldTurnMove(){
        int countFreeCells=0;
        if (labyrinth.elementAt(new Point(heroPoint.x,heroPoint.y+1))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x,heroPoint.y-1))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x+1,heroPoint.y))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x-1,heroPoint.y))==0){countFreeCells++;}
        return (countFreeCells<3 && type== Types.Classic);
    }

    public Labyrinth getLabyrinth(){
        return labyrinth;
    }

    public Point getHeroPoint(){
        return heroPoint;
    }

    public void move(Direction direction) {
        Point nextItem = new Point(heroPoint);

        do {
            direction.updatePoint(nextItem);
            if (labyrinth.elementAt(nextItem) == 0) {
                moveHeroToNewPoint(nextItem);
            } else {
                return;
            }
        } while (couldTurnMove() && !checkWin());
    }

    private void moveHeroToNewPoint(Point nextItem) {
        heroPoint = new Point(nextItem);
        updateView();
    }

    private void updateView() {
        heroPositionChangeListener.accept(heroPoint);
    }

    public boolean checkWin(){
        return heroPoint.equals(labyrinth.getExitPoint());
    }
}
