package com.smartparking.admin;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener authListener;
    ListView simpleList;
    TextView mTextField, tv_tk;
    Button signOut, thanhtoan;
    DatabaseReference myRef;
    DatabaseReference myRefWarning;
    CustomAdapter customAdapter;
    boolean check_booked = false;
    boolean check_available[] = {true, true, true, true};
    boolean warning_fire = false;
    String List[] = {"Slot 1", "Slot 2", "Slot 3", "Slot 4"};
    int flags[] = {R.drawable.car, R.drawable.greencar, R.drawable.redcar, R.drawable.car};
    Booked booked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(warning_fire == false){
                    if (check_booked == false && check_available[i] == true) {
                        createDialog(i);
                    } else {
                        if (check_booked == true && check_available[i] == true) {
                            showToast("Bạn chỉ được đặt 1 chỗ");
                        } else if (check_available[i] == false) {
                            showToast("Chỗ đã đặt, vui lòng chọn chỗ khác");
                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bãi đỗ xe đang gặp sự cố!");
                    builder.setIcon(R.drawable.redcar);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();
                }

            }
        });

        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
                String giora = dateformat.format(c.getTime());
                String[] str = giora.split(":");
                int timeout = Integer.parseInt(str[0])*60 + Integer.parseInt(str[1]);
                Intent i = getIntent();
                booked = (Booked) i.getSerializableExtra("booked_obj");
                String[] str2 = booked.getGiovao().split(":");
                int timein = Integer.parseInt(str2[0])*60 + Integer.parseInt(str2[1]);
                int sotien = (timeout - timein)*100;
                createDialog_Checkout(sotien);
            }
        });

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                String user = auth.getCurrentUser().getEmail();
                if(a.getId()>0){
                    if(a.getStatus().equals("0")) {
                        if(a.getUsername().equals("")){
                            flags[a.getId() - 1] = R.drawable.greencar;
                            check_available[a.getId() - 1] = true;
                        } else {
                            if(a.getUsername().equals(user)){
                                flags[a.getId() - 1] = R.drawable.car;
                                check_available[a.getId() - 1] = false;
                                thanhtoan.setVisibility(View.VISIBLE);
                            } else {
                                flags[a.getId() - 1] = R.drawable.redcar;
                                check_available[a.getId() - 1] = false;
                            }
                        }
                    } else {
                        if(a.getUsername().equals("")){
                            flags[a.getId() - 1] = R.drawable.redcar;
                            check_available[a.getId() - 1] = false;
                        } else {
                            if(a.getUsername().equals(user)){
                                flags[a.getId() - 1] = R.drawable.car;
                                check_available[a.getId() - 1] = false;
                                thanhtoan.setVisibility(View.VISIBLE);
                            } else {
                                flags[a.getId() - 1] = R.drawable.redcar;
                                check_available[a.getId() - 1] = false;
                            }
                        }
                    }
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                String user = auth.getCurrentUser().getEmail();
                thanhtoan.setVisibility(View.INVISIBLE);
                if(a.getStatus().equals("0")) {
                    if(a.getUsername().equals("")){
                        flags[a.getId() - 1] = R.drawable.greencar;
                        check_available[a.getId() - 1] = true;
                    } else {
                        if(a.getUsername().equals(user)){
                            flags[a.getId() - 1] = R.drawable.car;
                            check_available[a.getId() - 1] = false;
                            thanhtoan.setVisibility(View.VISIBLE);
                        } else {
                            flags[a.getId() - 1] = R.drawable.redcar;
                            check_available[a.getId() - 1] = false;
                        }
                    }
                } else {
                    if(a.getUsername().equals("")){
                        flags[a.getId() - 1] = R.drawable.redcar;
                        check_available[a.getId() - 1] = false;
                    } else {
                        if(a.getUsername().equals(user)){
                            flags[a.getId() - 1] = R.drawable.car;
                            check_available[a.getId() - 1] = false;
                            thanhtoan.setVisibility(View.VISIBLE);
                        } else {
                            flags[a.getId() - 1] = R.drawable.redcar;
                            check_available[a.getId() - 1] = false;
                        }
                    }
                }
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
        myRefWarning.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String fire = (String) dataSnapshot.getValue();
                if(fire.equals("0")) {
                    showNotify();
                    warning_fire = true;
                } else {
                    warning_fire = false;
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
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public void init(){
        signOut = findViewById(R.id.sign_out);
        thanhtoan = findViewById(R.id.thanhtoan);
        progressBar = findViewById(R.id.progressBar);
        simpleList = findViewById(R.id.lv);
        mTextField = findViewById(R.id.mTextField);
        tv_tk = findViewById(R.id.tv_tk);
        customAdapter = new CustomAdapter(getApplicationContext(), List, flags);
        simpleList.setAdapter(customAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sensors");
        myRefWarning = database.getReference("warning");
        Sensor sensor4 = new Sensor(4, "Slot 4", "1", "");
        myRef.child(String.valueOf(sensor4.getId())).setValue(sensor4);
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

    private void createDialog(final int j) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đặt chỗ");
        builder.setMessage("Bạn có muốn đặt trước chỗ này?");
        builder.setIcon(R.drawable.greencar);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                FirebaseUser user = auth.getCurrentUser();
                String userStr = user.getEmail();
                Sensor sensor = new Sensor((j + 1), "Slot " + (j + 1), "0", userStr);
                //myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                //customAdapter.notifyDataSetChanged();
                Intent intent = new Intent(MainActivity.this, Registration.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id_sensor", (j + 1));
                intent.putExtra("sensor_obj", sensor);
                intent.putExtras(bundle);
                startActivity(intent);
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
    private void createDialog_Checkout(final int sotien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thanh toán");
        builder.setMessage("Bạn có muốn thanh toán " + sotien + "đ ?");
        builder.setIcon(R.drawable.greencar);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                FirebaseUser user = auth.getCurrentUser();
                String userStr = user.getEmail();
                tv_tk.setText("Số tiền trong TK: " + (100000 - sotien));
                showToast("Bạn đã thanh toán " + sotien + "đ");
                thanhtoan.setVisibility(View.INVISIBLE);
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

    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showNotify(){
        System.out.println("warning");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.redcar)
                        .setContentTitle("Cánh báo")
                        .setContentText("Bãi đỗ xe có cháy!")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setDefaults(Notification.DEFAULT_VIBRATE);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, LoginActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(LoginActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        100,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification =  mBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        mNotificationManager.notify(12345, notification);
    }

}
