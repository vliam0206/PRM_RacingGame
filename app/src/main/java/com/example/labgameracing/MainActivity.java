package com.example.labgameracing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

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

        // Logic code
        tvCurrentAmount.setText(INIT_AMOUNT+"");
        MakePlayerRun();
        ResetPlayer();
    }

    void MakePlayerRun() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int progress1 = 0;
                int progress2 = 0;
                int progress3 = 0;
                boolean flag = true;
                while (flag) {
                    progress1 = random.nextInt(100-20+1)+20;
                    progress2 = random.nextInt(100-20+1)+20;
                    progress3 = random.nextInt(100-20+1)+20;
                    if (progress1==100 || progress2==100 || progress3==100) {
                        flag = false;
                    }
                }
                sbPlayer1.setProgress(progress1, true);
                sbPlayer2.setProgress(progress2, true);
                sbPlayer3.setProgress(progress3, true);
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