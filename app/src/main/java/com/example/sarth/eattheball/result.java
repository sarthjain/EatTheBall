package com.example.sarth.eattheball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        TextView highScore = (TextView) findViewById(R.id.highscore);

        int score = getIntent().getIntExtra("SCORE",0);
        scoreView.setText(score+"");

        SharedPreferences data = getSharedPreferences("DATA",MODE_PRIVATE);
        int high = data.getInt("HIGH_SCORE",0);

        if(score > high){
            highScore.setText("High Score : "+ score);

            //save
            SharedPreferences.Editor edit = data.edit();
            edit.putInt("HIGH_SCORE",score);
            edit.commit();
        }
        else
        {
            highScore.setText("High Score : "+high);
        }
    }

    public void tryAgain(View v)
    {
        Intent i = new Intent(getApplicationContext(),start.class);
        startActivity(i);
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
