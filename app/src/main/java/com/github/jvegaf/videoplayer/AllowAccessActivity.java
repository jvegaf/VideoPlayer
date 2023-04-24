package com.github.jvegaf.videoplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AllowAccessActivity extends AppCompatActivity {

    Button allowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);

        allowBtn = findViewById(R.id.allow_access_btn);

        allowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AllowAccessActivity.this, "Allow Acess", Toast.LENGTH_SHORT).show();
            }
        });
    }
}