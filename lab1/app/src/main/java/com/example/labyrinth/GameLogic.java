package com.example.labyrinth;

import android.graphics.Point;
import android.util.Size;

public class GameLogic {
    private DrawView view;
    Types type;
    Point heroPoint=new Point();
    Labyrinth labyrinth;
    LabyrinthGenerator l;

    public GameLogic(Size size, Types type, DrawView view) {
        l = new LabyrinthGenerator(size.getWidth(), size.getHeight());
        this.type = type;
        this.view = view;
        restart();
    }

    public GameLogic(int width, int height, Types type, long seed){
        l=new LabyrinthGenerator(width,height, seed);
        this.type=type;
        restart(seed);
    }

    public void restart(){
        heroPoint.set(1,1);
        l.generate();
        this.labyrinth=l.getLabyrinth();
    }

    public void restart(long seed){
        heroPoint.set(1,1);
        l.generate(seed);
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
        view.setHero(heroPoint);
        view.invalidate();
    }

    public boolean checkWin(){
        return heroPoint.equals(labyrinth.getExitPoint());
    }
}
