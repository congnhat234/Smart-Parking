package com.smartparking.admin;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        DatabaseReference myRef = database.getReference();
//
//        myRef.setValue("Hello, World!");
//
        for (int i = 0; i <= 5; i++) {
            myRef.child("Devpro").child("key " + i).setValue("Value " + i);
        }

//        Firebase.setAndroidContext(this);


//        Firebase myFirebaseRef = new Firebase("https://devpro-hello.firebaseio.com/");
//        for (int i = 0; i <= 10; i++) {
//            myRef.child("Devpro").child("key " + i).setValue("Value " + i);
//        }

        // Cấu hình ListView
        final ArrayList<String> name = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, name);
        lvData.setAdapter(adapter);

        // Read from the database
        myRef.child("Devpro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                name.add(dataSnapshot.getValue().toString());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                name.add(dataSnapshot.getValue().toString());
                name.add("test");
                adapter.notifyDataSetChanged();
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
