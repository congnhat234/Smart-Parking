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
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    EditText edt1;
    Button btnnext;

    private Spinner spinner1,spinner2;
    private String[] Loaixe;
    private ArrayAdapter<String> spinnerAdapter;
    private String[] Gio;
    private ArrayAdapter<String> spinnerAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edt1 = findViewById(R.id.edt1);
        btnnext = findViewById(R.id.btn);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        Loaixe = getResources().getStringArray(R.array.Xe);
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Loaixe);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner1.setAdapter(spinnerAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        Gio = getResources().getStringArray(R.array.Gio);
        spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Gio);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void Next(View view) {
        if (edt1.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nhập biển số xe!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Registration.this, CountdownTimerToOpen.class);
            startActivity(intent);
        }

    }
}
