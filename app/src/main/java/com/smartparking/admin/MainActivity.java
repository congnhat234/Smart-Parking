package com.smartparking.admin;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference myRef = database.getReference("Devpro");
//
//        myRef.setValue("Hello, World!");
//
//        for (int i = 0; i <= 5; i++) {
//            myFirebaseRef.child("Devpro").child("key " + i).setValue("Value " + i);
//        }

//        Firebase.setAndroidContext(this);
        lvData = (ListView) findViewById(R.id.lvData);

//        Firebase myFirebaseRef = new Firebase("https://devpro-hello.firebaseio.com/");
//        for (int i = 0; i <= 10; i++) {
//            myFirebaseRef.child("Devpro").child("key " + i).setValue("Value " + i);
//        }

        // Cấu hình ListView
        final ArrayList<String> name = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1);
        lvData.setAdapter(adapter);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // value = dataSnapshot.getValue(String.class);
                name.add(dataSnapshot.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(Tag, "Failed to read value.", error.toException());
            }
        });
    }
}
