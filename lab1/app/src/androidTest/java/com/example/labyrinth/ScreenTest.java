package com.example.labyrinth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.view.Display;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.lang.reflect.Field;

import static com.example.labyrinth.MainActivity.EXTRA_MESSAGE;
import static org.junit.Assert.assertEquals;

public class ScreenTest {

    @Test
    public void onCreate() throws NoSuchFieldException, IllegalAccessException {
        Types type=Types.Classic;
        Point size=new Point(0,0);
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(appContext, Screen.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MESSAGE, type);
        intent.putExtras(bundle);

        Looper.prepare();

        Screen screen=new Screen();


        Field types = screen.getClass().
                getDeclaredField("type");
        types.setAccessible(true);
        Field displaySize = screen.getClass().
                getDeclaredField("displaySize");
        displaySize.setAccessible(true);
        assertEquals(type,  types.get(screen));
        assertEquals(size.x, ((Point)displaySize.get(screen)).x);
        assertEquals(size.y, ((Point)displaySize.get(screen)).y);

    }
}
