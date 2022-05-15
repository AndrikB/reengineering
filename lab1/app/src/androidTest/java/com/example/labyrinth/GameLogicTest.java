package com.example.labyrinth;

import android.graphics.Point;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicTest {

    private int x=5,y=5;
    private GameLogic g1=new GameLogic(x,y, Types.Classic, 1000);

    @Test
    public void getHeroPoint(){
        assertEquals(g1.getHeroPoint(),new Point(1,1));
    }

    @Test
    public void move(){
        Point heroPoint=g1.getHeroPoint();
        g1.move(Direction.DOWN);     assertEquals(heroPoint, g1.getHeroPoint());     assertFalse(g1.checkWin());
        g1.move(Direction.RIGHT);    assertNotEquals(heroPoint, g1.getHeroPoint());  assertFalse(g1.checkWin());
        g1.move(Direction.LEFT);     assertEquals(heroPoint, g1.getHeroPoint());     assertFalse(g1.checkWin());

        g1.move(Direction.LEFT);     assertNotEquals(heroPoint, g1.getHeroPoint());  assertFalse(g1.checkWin());
        g1.move(Direction.UP);       assertNotEquals(heroPoint, g1.getHeroPoint());  assertFalse(g1.checkWin());
        g1.move(Direction.RIGHT);    assertNotEquals(heroPoint, g1.getHeroPoint());  assertFalse(g1.checkWin());
        g1.move(Direction.DOWN);     assertEquals(heroPoint, g1.getHeroPoint());     assertFalse(g1.checkWin());

        g1.move(Direction.RIGHT);    assertFalse(g1.checkWin());
        g1.move(Direction.DOWN);     assertFalse(g1.checkWin());
        g1.move(Direction.DOWN);     assertFalse(g1.checkWin());
        g1.move(Direction.LEFT);     assertFalse(g1.checkWin());
        g1.move(Direction.LEFT);     assertTrue(g1.checkWin());
    }

}
