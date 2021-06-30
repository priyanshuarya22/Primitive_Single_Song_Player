package com.example.primitivesinglesongplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    //initialising views
    SeekBar sb;
    Button b1, b2, b3;
    MediaPlayer mp;
    CountDownTimer ct;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating objects
        sb = findViewById(R.id.sb1);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        //setting listener for play button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //condition to check if music is currently being played
                if (check) {
                    //setting check to false to stop the following code from executing if the user clicks play button again while music is being played
                    check = false;
                    //creating object for music
                    mp = MediaPlayer.create(MainActivity.this, R.raw.sample);
                    //start playing music
                    mp.start();
                    //setting max for seekbar
                    sb.setMax(mp.getDuration());
                    //setting countdown timer
                    ct = new CountDownTimer(mp.getDuration(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //setting seekbar progress to current position of song
                            sb.setProgress(mp.getCurrentPosition());
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    //starting countdown timer
                    ct.start();
                }
            }
        });
        //setting listener for play/pause button
        b2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //checking if the music is being played
                if(b2.getText().equals("Pause")) {
                    //pausing the song
                    mp.pause();
                    //stopping the timer
                    ct.cancel();
                    //changing text on button to resume
                    b2.setText("Resume");
                }
                //if music is not being played
                else {
                    //starting the music
                    mp.start();
                    //starting the timer
                    ct.start();
                    //changing text on button to pause
                    b2.setText("Pause");
                }
            }
        });
        //setting listener for stop button
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopping the music
                mp.stop();
                //canceling the timer
                ct.cancel();
                //setting seek bar progress to 0
                sb.setProgress(0);
                //changing the value of check to indicate the music has stopped
                check = true;
            }
        });
        //setting listener for seek bar
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //checking if the user has changed the progress of bar
                if(fromUser) {
                    //seeking music to current progress of bar
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}