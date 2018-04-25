package com.smartparking.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragment extends AppCompatActivity {
    public abstract Fragment createFragment ();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager faManager = getSupportFragmentManager();
        Fragment fragment = faManager.findFragmentById(R.id.fragment_content);
        if(fragment == null) {
            fragment = createFragment();
            faManager.beginTransaction();  
        }
    }
}
