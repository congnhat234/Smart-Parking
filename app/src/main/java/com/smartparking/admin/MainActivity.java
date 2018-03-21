package com.smartparking.admin;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView simpleList;
    TextView mTextField;
    final ArrayList<Sensor> listSensors = new ArrayList<>();
    String List[] = {"Slot 1", "Slot 2", "Slot 3","Slot 4"};
    int flags[] = {R.drawable.car, R.drawable.greencar, R.drawable.redcar, R.drawable.car};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("S");
        for(int i=1; i<=4; i++){
            Sensor sensor = new Sensor(i,"Sensor " + i,0);
            myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children){
                    Sensor sensor = child.getValue(Sensor.class);
                    listSensors.add(sensor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        simpleList = findViewById(R.id.lv);
        mTextField = findViewById(R.id.mTextField);
        final CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), List, flags);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Sensor sensor = new Sensor(i+1,"Sensor " + i+1,2);
                myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        mTextField.setText("done!");
                        Sensor sensor = new Sensor(i+1,"Sensor " + i+1,1);
                        myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                    }
                }.start();
            }
        });

        System.out.println(listSensors.size());

        for (Sensor s : listSensors){
            System.out.println(s.getName() + "----");
        }

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                if(a.getStatus()==0) flags[a.getId()-1] = R.drawable.redcar;
                else if(a.getStatus()==1) flags[a.getId()-1] = R.drawable.greencar;
                else flags[a.getId()-1] = R.drawable.car;
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                if(a.getStatus()==0) flags[a.getId()-1] = R.drawable.redcar;
                else if(a.getStatus()==1) flags[a.getId()-1] = R.drawable.greencar;
                else flags[a.getId()-1] = R.drawable.car;
                customAdapter.notifyDataSetChanged();
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

    }
}
