package com.example.labyrinth;

import android.graphics.Point;

public class GameLogic {
    Types type;
    Point heroPoint=new Point();
    Labyrinth labyrinth;
    LabyrinthGenerator l;
    public GameLogic(int width, int height, Types type){
        l=new LabyrinthGenerator(width,height);
        this.type=type;
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


    public final boolean couldNextMove(){
        int countFreeCells=0;
        if (labyrinth.elementAt(new Point(heroPoint.x,heroPoint.y+1))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x,heroPoint.y-1))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x+1,heroPoint.y))==0){countFreeCells++;}
        if (labyrinth.elementAt(new Point(heroPoint.x-1,heroPoint.y))==0){countFreeCells++;}
        return (countFreeCells<3&&type== Types.Classic);
    }

    public Labyrinth getLabyrinth(){
        return labyrinth;
    }

    public Point getHeroPoint(){
        return heroPoint;
    }

    public boolean move(Move move){
        Point nextItem=new Point(heroPoint);
        if (move == Move.RIGHT)nextItem.x++;
        if (move == Move.LEFT)nextItem.x--;
        if (move == Move.DOWN)nextItem.y++;
        if (move == Move.UP)nextItem.y--;
        if (labyrinth.elementAt(nextItem)==0) {
            heroPoint=nextItem;
            return  checkWin();

            //return false;//todo mb change
        }
        return false;
    }

    public boolean checkWin(){
        return heroPoint.equals(labyrinth.getExitPoint());
    }
}
