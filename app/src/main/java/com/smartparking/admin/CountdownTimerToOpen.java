package com.smartparking.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CountdownTimerToOpen extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseAuth auth;
    CountDownTimer countDownTimer;
    TextView mTextField;
    int id_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);
        QRCodeWriter writer = new QRCodeWriter();
        ImageView qr_code = findViewById(R.id.img);
        try {
            ByteMatrix bitMatrix = writer.encode("123456", BarcodeFormat.QR_CODE, 512, 512);
            int width = 512;
            int height = 512;
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)==0)
                        bmp.setPixel(x, y, Color.BLACK);
                    else
                        bmp.setPixel(x, y, Color.WHITE);
                }
            }
            qr_code.setImageBitmap(bmp);
        } catch (WriterException e) {
            //Log.e("QR ERROR", ""+e);

        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sensors");
        mTextField = findViewById(R.id.mTextField);

        Bundle b = getIntent().getExtras();
        id_sensor = b.getInt("id_sensor");

        System.out.println(id_sensor);

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Sensor a = dataSnapshot.getValue(Sensor.class);
                if(a.getId()==id_sensor && a.getStatus().equals("1")){
                    countDownTimer.cancel();
                    Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);
                    startActivity(intent);
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

        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText("Bạn phải đến bãi đỗ xe trong: " + millisUntilFinished / 1000 + "s nữa!");
            }

            public void onFinish() {
                mTextField.setText("Hết thời gian!");

                Intent intent = new Intent(CountdownTimerToOpen.this, MainActivity.class);

                Sensor sensor = new Sensor(id_sensor, "Sensor " + id_sensor, "0", "");
                myRef.child(String.valueOf(sensor.getId())).setValue(sensor);
                startActivity(intent);
            }
        }.start();
    }
}
