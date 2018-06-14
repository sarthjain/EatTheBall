package com.example.sarth.eattheball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View v)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    //disable reverse button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
