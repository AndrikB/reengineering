package com.example.labyrinth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;

import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.lang.reflect.Field;

public class DrawViewTest {

    private Point screenSize=new Point(10,10);
    private Size countCell=new Size(5,5);
    @Test
    public void onCreate() throws NoSuchFieldException, IllegalAccessException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DrawView drawView = new DrawView(appContext,screenSize,countCell);
        Field paintField = drawView.getClass().
                getDeclaredField("paintField");
        paintField.setAccessible(true);
        Field paintHero = drawView.getClass().
                getDeclaredField("paintHero");
        paintHero.setAccessible(true);
        Field paintLight = drawView.getClass().
                getDeclaredField("paintLight");
        paintLight.setAccessible(true);

        assertEquals(((Paint)paintField.get(drawView)).getColor(), Color.GRAY);
        assertEquals(((Paint)paintHero.get(drawView)).getColor(), Color.RED);
        assertEquals(((Paint)paintLight.get(drawView)).getColor(), Color.YELLOW);



    }
}
