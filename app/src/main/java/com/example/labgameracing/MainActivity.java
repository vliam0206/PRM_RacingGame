package com.example.labgameracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    final int INIT_AMOUNT = 10000;
    TextView tvCurrentAmount;
    SeekBar sbPlayer1;
    SeekBar sbPlayer2;
    SeekBar sbPlayer3;
    Button btnStart;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bindings
        tvCurrentAmount = findViewById(R.id.tvCurrentAmount);
        sbPlayer1 = findViewById(R.id.seekbarPlayer1);
        sbPlayer2 = findViewById(R.id.seekbarPlayer2);
        sbPlayer3 = findViewById(R.id.seekbarPlayer3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        int color = ContextCompat.getColor(this,R.color.orange);
        // Logic code
        tvCurrentAmount.setText(INIT_AMOUNT+"");
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.music);
        MakePlayerRun(mediaPlayer);
        ResetPlayer();
    }

    void MakePlayerRun(MediaPlayer mediaPlayer) {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Random random = new Random();
                int progress1 = 0;
                int progress2 = 0;
                int progress3 = 0;
                int height = 100;
                int thumbWidth = 200;
                boolean flag = true;
                while (flag) {
                    progress1 = 70 + random.nextInt(30) + 1;
                    progress2 = 70 + random.nextInt(30) + 1;
                    progress3 = 70 + random.nextInt(30) + 1;
                    if (progress1 == 100 || progress2 == 100 || progress3 == 100) {
                        flag = false;
                    }
                }
                ObjectAnimator animation = ObjectAnimator.ofInt(sbPlayer1, "progress", 0, progress1);
                animation.setDuration(12000);
                animation.start();
                ObjectAnimator animation1 = ObjectAnimator.ofInt(sbPlayer2, "progress", 0, progress2);
                animation1.setDuration(12000);
                animation1.start();
                ObjectAnimator animation2 = ObjectAnimator.ofInt(sbPlayer3, "progress", 0, progress3);
                animation2.setDuration(12000);
                animation2.start();
            }
        });
    }
    void ResetPlayer() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbPlayer1.setProgress(0, true);
                sbPlayer2.setProgress(0, true);
                sbPlayer3.setProgress(0, true);
            }
        });
    }
}