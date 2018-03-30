package com.smartparking.admin;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CountdownTimerToOpen extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseAuth auth;
    CountDownTimer countDownTimer;
    TextView mTextField;
    Button btn_open;
    int id_sensor = 0;
    boolean check_booked = false;
    boolean check_available = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mTextField = findViewById(R.id.mTextField);
        btn_open = findViewById(R.id.btn_open);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id_sensor = bundle.getInt("id_sensor", 0);

        }

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                FirebaseUser user = auth.getCurrentUser();
                String userStr = user.getEmail();
                check_booked = true;
                check_available = false;
                Sensor sensor = new Sensor(id_sensor,"Sensor " + id_sensor,1,userStr);
                //myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("check_booked", check_booked);
                bundle.putBoolean("check_available", check_available);
                bundle.putInt("id_sensor", id_sensor);
                bundle.putParcelable("sensor", (Parcelable) sensor);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setText("Time up!");
                Sensor sensor = new Sensor(id_sensor,"Sensor " + id_sensor,0,"");
                myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                check_booked = false;
                check_available = true;
                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("check_booked", check_booked);
                bundle.putBoolean("check_available", check_available);
                bundle.putInt("id_sensor", id_sensor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }.start();
    }
}
