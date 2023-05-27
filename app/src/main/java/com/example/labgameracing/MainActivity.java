package com.example.labgameracing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    CheckBox checkboxPlayer1, checkboxPlayer2, checkboxPlayer3;
    EditText etPayNumber;
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
        checkboxPlayer1 = findViewById(R.id.checkboxPlayer1);
        checkboxPlayer2 = findViewById(R.id.checkboxPlayer2);
        checkboxPlayer3 = findViewById(R.id.checkboxPlayer3);
        etPayNumber = findViewById(R.id.etPayNumber);

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
                tvCurrentAmount.setText(calculateBetMoney(progress1, progress2, progress3)+"");
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

    int calculateBetMoney(int progress1, int progress2, int progress3) {
        boolean checkedPlayer1 = checkboxPlayer1.isChecked();
        boolean checkedPlayer2 = checkboxPlayer2.isChecked();
        boolean checkedPlayer3 = checkboxPlayer3.isChecked();
        int betMoney = Integer.parseInt(etPayNumber.getText().toString());
        int currentMoney = Integer.parseInt(tvCurrentAmount.getText().toString());

        if (checkedPlayer1){
            if (progress1 < progress2 || progress1 < progress3) {
                currentMoney = currentMoney - betMoney;
            }else if (progress1 >= progress2 && progress1 >= progress3){
                currentMoney = currentMoney + betMoney;
            }
        }
        if (checkedPlayer2){
            if (progress2 < progress1 || progress2 < progress3){
                currentMoney = currentMoney - betMoney;
            }else if (progress2 >= progress1 && progress2 >= progress3) {
                currentMoney = currentMoney + betMoney;
            }
        }
        if (checkedPlayer3){
            if (progress3 < progress1 || progress3 < progress2){
                currentMoney = currentMoney - betMoney;
            }else if (progress3 >= progress1 && progress3 >= progress2) {
                currentMoney = currentMoney + betMoney;
            }
        }

        return currentMoney;
    }
}