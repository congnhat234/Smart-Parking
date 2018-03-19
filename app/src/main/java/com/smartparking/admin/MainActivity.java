package com.smartparking.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvData = (ListView) findViewById(R.id.lvData);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference myRef = database.getReference().child("S");
//
//        myRef.setValue("Hello, World!");
//
//        for (int i = 0; i <= 3; i++) {
//            myRef.child("Devpro").child("key " + i).setValue("Gia tri " + i);
//        }

        Sensor sensor = new Sensor("sensor1","Sensor 1",0);
        myRef.child(sensor.getId()).setValue(sensor);
        Sensor sensor2 = new Sensor("sensor2","Sensor 2",0);
        myRef.child(sensor2.getId()).setValue(sensor2);


//        Firebase.setAndroidContext(this);


//        Firebase myFirebaseRef = new Firebase("https://devpro-hello.firebaseio.com/");
//        for (int i = 0; i <= 10; i++) {
//            myRef.child("Devpro").child("key " + i).setValue("Value " + i);
//        }

        // Cấu hình ListView
//        final ArrayList<String> name = new ArrayList<>();
//        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, name);
//        lvData.setAdapter(adapter);
//
//
//        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });


        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                name.add(dataSnapshot.getValue().toString());
//
//                adapter.notifyDataSetChanged();

                Sensor a = dataSnapshot.getValue(Sensor.class);
                System.out.println(a.getId() + " " + a.getName() + " " + a.getStatus());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                name.add(dataSnapshot.getValue().toString());
//                name.add("test");
//                adapter.notifyDataSetChanged();

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
