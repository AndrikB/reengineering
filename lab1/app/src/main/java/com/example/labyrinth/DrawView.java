package com.example.labyrinth;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;
import android.view.View;

import static java.lang.Math.min;

enum Types{
        Classic, Hard
    }

public class DrawView extends View {

    private Types type;
    private Point heroPoint;
    private Size countCell;
    private float cellWidth, cellHeight;
    private Paint paintField=new Paint();
    private Paint paintHero=new Paint();
    private Paint paintLight=new Paint();
    private Labyrinth labyrinth;



    public DrawView(Context context, Point screenSize, Size countCell, Types type) {
        super(context);
        paintField.setColor(Color.GRAY);
        paintHero.setColor(Color.RED);
        paintLight.setColor(Color.YELLOW);
        this.setBackgroundColor(Color.BLACK);
        this.countCell=countCell;
        this.type = type;
        cellWidth=((float)screenSize.x)/countCell.getWidth();
        cellHeight=((float)screenSize.y)/countCell.getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(labyrinth.getExitPoint().x<2&&labyrinth.getExitPoint().y<2)return;

        if(type==Types.Hard) {
            for (int i = 0; i < countCell.getHeight(); i++)
                for (int j = 0; j < countCell.getWidth(); j++)
                    if (Math.min(Math.pow(heroPoint.x - j, 2.0) + Math.pow(heroPoint.y - i, 2),
                            Math.pow(labyrinth.getExitPoint().x - j, 2.0) + Math.pow(labyrinth.getExitPoint().y - i, 2)) < 6)
                        drawRect(i, j, canvas);
        }
        else if (type==Types.Classic){
            for (int i = 0; i < countCell.getHeight(); i++)
                for (int j = 0; j < countCell.getWidth(); j++)
                    drawRect(i, j, canvas);
        }
    }

    private void drawRect(int i, int j, Canvas canvas){
        if (labyrinth.elementAt(new Point(j,i))==1)
            canvas.drawRect(j*cellWidth,i*cellHeight,(j+1)*cellWidth,(i+1)*cellHeight, paintField);
        else
            canvas.drawRect(j*cellWidth,i*cellHeight,(j+1)*cellWidth,(i+1)*cellHeight, paintLight);

        if (heroPoint.equals(j,i)){
            canvas.drawCircle((j+0.5f)*cellWidth,(i+0.5f)*cellHeight,min(cellWidth, cellHeight)/2, paintHero);
        }
    }

    public void setLabyrinth(Labyrinth labyrinth){
        this.labyrinth=labyrinth;
    }

    public void setHero(Point heroPoint){
        this.heroPoint=heroPoint;
    }

    public void setGameType(Types type){
        this.type=type;
    }

}
