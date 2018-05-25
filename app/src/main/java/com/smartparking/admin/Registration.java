package com.smartparking.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Registration extends AppCompatActivity {
    EditText edt1;
    TextView tv;
    Button btnnext;
    DatabaseReference myRef;
    DatabaseReference myRefBooked;

    private Spinner spinner1, spinner2;
    private String[] Loaixe;
    private ArrayAdapter<String> spinnerAdapter;
    private String[] Gio;
    private ArrayAdapter<String> spinnerAdapter2;
    int id_sensor;
    Sensor sensor;
    Booked booked;
    String biensoxe;
    String loaixe;
    String giovao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sensors");
        myRefBooked = database.getReference("booked");

        edt1 = findViewById(R.id.edt1);
        tv = findViewById(R.id.tv);
        btnnext = findViewById(R.id.btn);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        id_sensor = b.getInt("id_sensor");
        sensor = (Sensor) i.getSerializableExtra("sensor_obj");

        spinner1 = findViewById(R.id.spinner1);
        Loaixe = getResources().getStringArray(R.array.Xe);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Loaixe);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner1.setAdapter(spinnerAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    loaixe = Loaixe[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
        giovao = dateformat.format(c.getTime());
        tv.setText(giovao);
    }

    public void Next(View view) {
        if (edt1.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nhập biển số xe!", Toast.LENGTH_SHORT).show();
        } else {
            biensoxe = edt1.getText().toString();
            booked = new Booked(biensoxe,giovao,loaixe,sensor.getUsername(),sensor.getName());
            String key = myRefBooked.push().getKey();
            myRefBooked.child(key).setValue(booked);

            Intent intent = new Intent(Registration.this, CountdownTimerToOpen.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id_sensor", id_sensor);
            intent.putExtras(bundle);

            intent.putExtra("booked_obj", booked);
            intent.putExtras(bundle);
            myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
            startActivity(intent);
        }
    }
}
