package com.example.acer.timer;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewController extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer timer;
    Button button;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_controller);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button = (Button) findViewById(R.id.start);
        timerTextView = (TextView) findViewById(R.id.textView);


        seekBar.setMax(500);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void onStartPressed(View view){
       if (counterIsActive == false){
           counterIsActive = true;
           seekBar.setEnabled(false);
           button.setText("Stop");
           button.setBackgroundColor(Color.RED);
           timer =  new CountDownTimer(seekBar.getProgress()*1000+100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int)millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.sound);
                mediaPlayer.start();
                reset();

            }
        }.start();
       }else {
           if (mediaPlayer!= null && mediaPlayer.isPlaying()){
               mediaPlayer.stop();
           }
           reset();

       }
    }
    public void reset(){

        timerTextView.setText("0:30");
        seekBar.setProgress(30);
        timer.cancel();
        button.setText("Start");
        button.setBackgroundColor(Color.rgb(129,170,202));
        seekBar.setEnabled(true);
        counterIsActive = false;
    }
    public void updateTimer(int secondLeft){
        int minutes = (int)secondLeft / 60;
        int seconds = secondLeft - minutes * 60;
        String secondString =  Integer.toString(seconds);
        if (seconds <= 9){
            secondString = "0"+ secondString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secondString);
    }
}
