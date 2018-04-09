package com.smartparking.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CountdownTimerToOpen extends AppCompatActivity {
    DatabaseReference myRef;
    CountDownTimer countDownTimer;
    TextView mTextField;
    Button btn_open;
    int id_sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mTextField = findViewById(R.id.mTextField);
        btn_open = findViewById(R.id.btn_open);

        System.out.println(id_sensor);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                mTextField.setText("Chao ban!");
                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                startActivity(intent);

            }
        });

        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setText("Time up!");
                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
