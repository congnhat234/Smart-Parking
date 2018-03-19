package com.smartparking.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ListView simpleList;
    String List[] = {"Slot 1", "Slot 2", "Slot 3"};
    int flags[] = {R.drawable.car, R.drawable.greencar, R.drawable.redcar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleList = (ListView) findViewById(R.id.lv);
        final CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), List, flags);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                flags[i] = R.drawable.redcar;
                customAdapter.notifyDataSetChanged();
            }
        });

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference myRef = database.getReference();
//
//        myRef.setValue("Hello, World!");
//
        for (int i = 0; i <= 3; i++) {
            myRef.child("Devpro").child("key " + i).setValue("Gia tri " + i);
        }

//        Firebase.setAndroidContext(this);


//        Firebase myFirebaseRef = new Firebase("https://devpro-hello.firebaseio.com/");
//        for (int i = 0; i <= 10; i++) {
//            myRef.child("Devpro").child("key " + i).setValue("Value " + i);
//        }

        // Cấu hình ListView
//        final ArrayList<String> name = new ArrayList<String>();
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
        myRef.child("Devpro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                name.add(dataSnapshot.getValue().toString());
//
//                adapter.notifyDataSetChanged();
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
