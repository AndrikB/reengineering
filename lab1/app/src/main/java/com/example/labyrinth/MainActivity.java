package com.example.labyrinth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class MainActivity extends Activity
        implements View.OnClickListener {
    public static String EXTRA_MESSAGE="com.example.labyrinth.MESSAGE";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View v = findViewById(R.id.buttonClickEasy);
        v.setOnClickListener(this);
        View vh = findViewById(R.id.buttonClickHard);
        vh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Types type= Types.Classic;
        if(view.getId() == R.id.buttonClickEasy){
            type= Types.Classic;
        }

        if(view.getId() == R.id.buttonClickHard){
            type= Types.Hard;
        }

        if (view.getId() == R.id.buttonClickEasy||
                view.getId() == R.id.buttonClickHard)
        {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, Screen.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_MESSAGE, type);
            intent.putExtras(bundle);

            //start the second Activity
            this.startActivity(intent);
        }
    }
}
