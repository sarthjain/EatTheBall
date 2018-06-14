package com.example.sarth.eattheball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by sarth on 9/25/2017.
 */

public class Sound
{
    private AudioAttributes audioAttributes;

    private static SoundPool soundpool;
    private static int hitSound;
    private static int overSound;

    Sound(Context context){

        //Soundpool is deprecated from API 21(lollipop)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundpool =new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(2)
                    .build();
        }
        else {
            soundpool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        hitSound = soundpool.load(context, R.raw.hit, 1);
        overSound = soundpool.load(context, R.raw.over, 1);
    }

    public void playHitSound()
    {
        soundpool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playOverSound()
    {
        soundpool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
