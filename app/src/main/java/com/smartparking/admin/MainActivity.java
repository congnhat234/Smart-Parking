package com.smartparking.admin;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.support.v7.app.AlertDialog;

import android.os.CountDownTimer;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener authListener;
    ListView simpleList;
    TextView mTextField;
    Button signOut;
    DatabaseReference myRef;
    CustomAdapter customAdapter;
    boolean check_booked = false;
    boolean check_available[] = {true, true, true, true};
    final ArrayList<Sensor> listSensors = new ArrayList<>();
    String List[] = {"Slot 1", "Slot 2", "Slot 3","Slot 4"};
    int flags[] = {R.drawable.car, R.drawable.greencar, R.drawable.redcar, R.drawable.car};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut = findViewById(R.id.sign_out);
        progressBar = findViewById(R.id.progressBar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        for(int i=1; i<=3; i++){
            Sensor sensor = new Sensor(i,"Sensor " + i,0);
            myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
        }
        Sensor sensor = new Sensor(4,"Sensor 4",1);
        myRef.child(String.valueOf(sensor.getId())).setValue(sensor);

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
        customAdapter = new CustomAdapter(getApplicationContext(), List, flags);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(check_booked == false && check_available[i] == true){
                    createDialog(i);
                } else {
                    if (check_booked == true){
                        showToast("Bạn chỉ được đặt 1 chỗ");
                    } else if(check_available[i] == false) {
                        showToast("Chỗ đã đặt, vui lòng chọn chỗ khác");
                    }

                }


            }
        });

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                if(a.getStatus()==0) {
                    flags[a.getId()-1] = R.drawable.greencar;
                    check_available[a.getId()-1] = true;
                }
                else {
                    if(a.getStatus()==1) {
                        flags[a.getId()-1] = R.drawable.redcar;
                        check_available[a.getId()-1] = false;
                    }
                    else {
                        flags[a.getId()-1] = R.drawable.car;
                        check_available[a.getId()-1] = false;
                    }
                }
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
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }
    //sign out method
    public void signOut() {
        auth.signOut();
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void createDialog(final int j){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đặt chỗ");
        builder.setMessage("Bạn có muốn đặt trước chỗ này?");
        builder.setIcon(R.drawable.redcar);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                Sensor sensor = new Sensor(j+1,"Sensor " + j+1,2);
                List[j] = "My car";
                myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        mTextField.setText("Time up!");
                        Sensor sensor = new Sensor(j+1,"Sensor " + j+1,1);
                        myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                        check_booked = false;
                        check_available[j] = true;
                    }
                }.start();

                customAdapter.notifyDataSetChanged();
                check_booked = true;
                check_available[j] = false;
                showToast("YES");
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    public void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
