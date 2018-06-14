package com.example.sarth.eattheball;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scorelabel;
    private TextView startlabel;
    private ImageView black;
    private ImageView monster;
    private ImageView orange;
    private ImageView pink;

    //Size
    private int frameheight;
    private int boxSize;
    private int boxSizeW;
    private int screenWidth;
    private int screenHeight;

    //position
    private float monsy;
    private float orangex;
    private float orangey;
    private float blackx;
    private float blacky;
    private float pinkx;
    private float pinky;

    //status
    private boolean action_flag;
    private boolean start_flag;

    //Center of balls
    private float orangeMidX;
    private float orangeMidY;
    private float blackMidX;
    private float blackMidY;
    private float pinkMidX;
    private float pinkMidY;

    //Total score
    private int score=0;

    //initialize class
    Handler handler = new Handler();
    Timer timer = new Timer();
    Sound sound;

    //speed
    private int monsSpeed;
    private int speedOrange;
    private int speedBlack;
    private int speedPink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new Sound(this);

        scorelabel = (TextView) findViewById(R.id.score);
        startlabel = (TextView) findViewById(R.id.start_label);
        black = (ImageView) findViewById(R.id.black);
        monster = (ImageView) findViewById(R.id.monster);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);

        //get the size of the screen
        WindowManager win = getWindowManager();
        Display dis = win.getDefaultDisplay();
        Point point = new Point();

        dis.getSize(point);

        screenWidth = point.x;
        screenHeight = point.y;

        monsSpeed = Math.round(screenHeight / 55F);
        speedOrange = Math.round(screenWidth / 50F);
        speedPink = Math.round(screenWidth / 35F);
        speedBlack = Math.round(screenWidth / 40F);

        Log.v("SpeedBox",monsSpeed+"");
        Log.v("SpeedOrange",speedOrange+"");
        Log.v("SpeedPink",speedPink+"");
        Log.v("SpeedBlack",speedBlack+"");

        //Move the balls out of the screen
        black.setX(-80f);
        black.setY(-80f);
        orange.setX(-80f);
        orange.setY(-80f);
        pink.setX(-80f);
        pink.setY(-80f);

    }

    //move monster
    private void changepos()
    {

        scorecheck();

        //orange
        orangex -=speedOrange;
        if(orangex < 0)
        {
            orangex = screenWidth+20;
            orangey =(float) Math.floor(Math.random() * (frameheight - orange.getHeight()));
        }
        orange.setX(orangex);
        orange.setY(orangey);

        //black
        blackx -=speedBlack;
        if(blackx < 0)
        {
            blackx = screenWidth+10;
            blacky =(float) Math.floor(Math.random() * (frameheight - black.getHeight()));
        }
        black.setX(blackx);
        black.setY(blacky);

        //orange
        pinkx -=speedPink;
        if(pinkx < 0)
        {
            pinkx = screenWidth+5;
            pinky =(float) Math.floor(Math.random() * (frameheight - pink.getHeight()));
        }
        pink.setX(pinkx);
        pink.setY(pinky);

        //Move box
        //Touch
        if(action_flag==true)
            monsy -=monsSpeed;
        //Release
        else if(action_flag==false)
            monsy +=monsSpeed;

        //check the monster position
        if(monsy<0)
            monsy=0;
        else if(monsy > frameheight-boxSize)
            monsy = frameheight-boxSize;
        monster.setY(monsy);

        scorelabel.setText("Score : "+ score);
    }

    public void scorecheck()
    {

        //orange
        orangeMidX = orangex + orange.getWidth()/2;
        orangeMidY = orangey + orange.getHeight()/2;

        if( 0 <= orangeMidX && orangeMidX <=boxSizeW
                && monsy <= orangeMidY && orangeMidY <= monsy+boxSize)
        {
            score += 20;
            orangex =-10;
            sound.playHitSound();
        }

        //black
        blackMidX = blackx + black.getWidth()/2;
        blackMidY = blacky + black.getHeight()/2;

        if( 0 <= blackMidX && blackMidX <=boxSizeW
                && monsy <= blackMidY && blackMidY <= monsy+boxSize)
        {
            timer.cancel();
            timer=null;
            sound.playOverSound();
            Intent i = new Intent(getApplicationContext(),result.class);
            i.putExtra("SCORE",score);
            startActivity(i);
        }

        //pink
        pinkMidX =pinkx + pink.getWidth()/2;
        pinkMidY = pinky + pink.getHeight()/2;

        if( 0 <= pinkMidX && pinkMidX <=boxSizeW
                && monsy <= pinkMidY && pinkMidY <= monsy+boxSize)
        {
            score += 40;
            pinkx =-10;
            sound.playHitSound();
        }

    }

    public boolean onTouchEvent(MotionEvent me){

        if(start_flag == false) {

            start_flag=true;
            startlabel.setVisibility(View.GONE);

            //get the height of the frame
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameheight = frame.getHeight();

            boxSize = monster.getHeight();
            boxSizeW =monster.getWidth();
            //get the height of box
            monsy = monster.getY();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changepos();
                        }
                    });
                }
            },0,20);
        }
        else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flag = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }
        return true;
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
