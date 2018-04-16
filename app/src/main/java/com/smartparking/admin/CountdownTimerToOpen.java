package com.smartparking.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CountdownTimerToOpen extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseAuth auth;
    CountDownTimer countDownTimer;
    TextView mTextField;
    Button btn_open;
    int id_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sensors");
        mTextField = findViewById(R.id.mTextField);
        btn_open = findViewById(R.id.btn_open);

        Bundle b = getIntent().getExtras();
        id_sensor = b.getInt("id_sensor");

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

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                if(a.getId()==id_sensor && a.getStatus().equals("1")){
                    countDownTimer.cancel();
                    Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setText("Time up!");

                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);

                Sensor sensor = new Sensor(id_sensor, "Sensor " + id_sensor, "0", "");
                myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                startActivity(intent);
            }
        }.start();
    }
}
