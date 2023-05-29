package com.example.labgameracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {
    final int INIT_AMOUNT = 10000;
    TextView tvCurrentAmount, resultNoti;
    SeekBar sbPlayer1;
    SeekBar sbPlayer2;
    SeekBar sbPlayer3;
    Button btnStart;
    Button btnReset;
    CheckBox checkboxPlayer1, checkboxPlayer2, checkboxPlayer3;
    EditText etPayNumber;
    int totalWin = 0;
    int totalLose = 0;
    public int numberchecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bindings
        tvCurrentAmount = findViewById(R.id.tvCurrentAmount);
        resultNoti = findViewById(R.id.resultNoti);
        sbPlayer1 = findViewById(R.id.seekbarPlayer1);
        sbPlayer2 = findViewById(R.id.seekbarPlayer2);
        sbPlayer3 = findViewById(R.id.seekbarPlayer3);
        sbPlayer1.setEnabled(false);
        sbPlayer2.setEnabled(false);
        sbPlayer3.setEnabled(false);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        int color = ContextCompat.getColor(this,R.color.orange);
        checkboxPlayer1 = findViewById(R.id.checkboxPlayer1);
        checkboxPlayer2 = findViewById(R.id.checkboxPlayer2);
        checkboxPlayer3 = findViewById(R.id.checkboxPlayer3);
        etPayNumber = findViewById(R.id.etPayNumber);
        // Logic code
        tvCurrentAmount.setText(INIT_AMOUNT+"");
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.music);
        MakePlayerRun(mediaPlayer,this);
        ResetPlayer();

    }

    void MakePlayerRun(MediaPlayer mediaPlayer,Context context) {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkboxPlayer1.isChecked() == false && checkboxPlayer2.isChecked() == false && checkboxPlayer3.isChecked() == false){
                    Toast.makeText(context, "Please choose one character", Toast.LENGTH_SHORT).show();
                    return;
                }

                int currentAmount = Integer.parseInt(tvCurrentAmount.getText().toString());
                if(etPayNumber.getText().toString().equals("")){
                    Toast.makeText(context, "You must fill bet amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkboxPlayer1.isChecked()) { numberchecked++;}
                if (checkboxPlayer2.isChecked()) { numberchecked++;}
                if (checkboxPlayer3.isChecked()) { numberchecked++;}
                int betAmount = Integer.parseInt(etPayNumber.getText().toString()) * numberchecked;
                if(betAmount > currentAmount){
                    Toast.makeText(context, "Can not bet larger than your balance." +
                            "\nYou have: " + currentAmount +
                            "\nYou bet: " + betAmount, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(currentAmount <= 0){
                    Toast.makeText(context, "Your balance can not play more,we will give you 10000 again. \n+ 10000", Toast.LENGTH_LONG).show();
                    tvCurrentAmount.setText("10000");
                    return;
                }
                if(betAmount == 0){
                    Toast.makeText(context, "Your bet amount must be larger than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                resultNoti.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                disable();
                Random random = new Random();
                int progress1 = 0;
                int progress2 = 0;
                int progress3 = 0;
                int height = 100;
                int thumbWidth = 200;
                final int minBound = 70;
                final int maxBound = 95;
                final int animationDuration = 12000;
                boolean flag = true;
                while (flag) {
                    progress1 = minBound + random.nextInt(maxBound-minBound) + 1;
                    progress2 = minBound + random.nextInt(maxBound-minBound) + 1;
                    progress3 = minBound + random.nextInt(maxBound-minBound) + 1;
                    if (progress1 == maxBound || progress2 == maxBound || progress3 == maxBound) {
                        flag = false;
                    }
                }
                final int progressX1 = progress1;
                final int progressX2 = progress2;
                final int progressX3 = progress3;
                int a = 0 ;
                int currentMoney = calculateBetMoney(progressX1, progressX2, progressX3);

                String result =  ReslutMessage(progress1, progress2, progress3);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(11000);
                            tvCurrentAmount.setText(currentMoney+"");
                        } catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            resultNoti.setText(result);
                        }
                    }
                });

                ObjectAnimator animation = ObjectAnimator.ofInt(sbPlayer1, "progress", 10, progress1);
                animation.setDuration(animationDuration);
                animation.start();
                ObjectAnimator animation1 = ObjectAnimator.ofInt(sbPlayer2, "progress", 10, progress2);
                animation1.setDuration(animationDuration);
                animation1.start();
                ObjectAnimator animation2 = ObjectAnimator.ofInt(sbPlayer3, "progress", 10, progress3);
                animation2.setDuration(animationDuration);
                animation2.start();
                thread.start();
            }
        });
    }
    void ResetPlayer() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable();
                sbPlayer1.setProgress(10, true);
                sbPlayer2.setProgress(10, true);
                sbPlayer3.setProgress(10, true);
                totalLose = 0;
                totalWin = 0;
                resultNoti.setText("Result...");
                resultNoti.setVisibility(View.INVISIBLE);
            }
        });
    }
    int calculateBetMoney(int progress1, int progress2, int progress3) {
        boolean checkedPlayer1 = checkboxPlayer1.isChecked();
        boolean checkedPlayer2 = checkboxPlayer2.isChecked();
        boolean checkedPlayer3 = checkboxPlayer3.isChecked();
        int currentMoney = Integer.parseInt(tvCurrentAmount.getText().toString());
        if (!etPayNumber.getText().toString().equals("")) {
            int betMoney = Integer.parseInt(etPayNumber.getText().toString());

            if (checkedPlayer1) {
                if (progress1 >= progress2 && progress1 >= progress3) {
                    currentMoney = currentMoney + betMoney;
                    totalWin += betMoney;

                } else {
                    currentMoney = currentMoney - betMoney;
                    totalLose -= betMoney;

                }
            }
            if (checkedPlayer2) {
                if (progress2 >= progress1 && progress2 >= progress3) {
                    currentMoney = currentMoney + betMoney;
                    totalWin += betMoney;
                } else {
                    currentMoney = currentMoney - betMoney;
                    totalLose -= betMoney;
                }
            }
            if (checkedPlayer3) {
                if (progress3 >= progress1 && progress3 >= progress2) {
                    currentMoney = currentMoney + betMoney;
                    totalWin += betMoney;
                } else{
                    currentMoney = currentMoney - betMoney;
                    totalLose -= betMoney;
                }
            }
        }
        return currentMoney;
    }
    private void disable(){
        checkboxPlayer1.setEnabled(false);
        checkboxPlayer2.setEnabled(false);
        checkboxPlayer3.setEnabled(false);
    }
    private void enable(){
        etPayNumber.setText("0");
        checkboxPlayer1.setEnabled(true);
        checkboxPlayer2.setEnabled(true);
        checkboxPlayer3.setEnabled(true);
    }
    private boolean IsWin(int pg1, int pg2, int pg3) {
        return (pg1 >= pg2 && pg1 >= pg3);
    }
    private  String ReslutMessage(int pg1, int pg2, int pg3) {
        String s = "";
        boolean win1 = false;
        boolean win2 = false;
        boolean win3 = false;
        if (IsWin(pg1, pg2, pg3)) { win1 = true;}
        if (IsWin(pg2, pg1, pg3)) { win2 = true;}
        if (IsWin(pg3, pg2, pg1)) { win3 = true;}
        s += "The player(s) "
                + (win1 ? 1: " ")
                + (win2 ? 2: " ")
                + (win3 ? 3: " ")
                + " won\n"
                + "+"+ totalWin
                + "\n"+ totalLose;
        return s;
    }
}