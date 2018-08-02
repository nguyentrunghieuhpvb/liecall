package com.playgirl.hieunt.liecall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llCaller, llCallNow, llRingTone, llTimer;
    private ImageView imgDeleteAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llCaller = findViewById(R.id.ll_caller);
        llCallNow = findViewById(R.id.ll_call_now);
        llRingTone = findViewById(R.id.ll_ring_tone);
        llTimer = findViewById(R.id.ll_timer);
        imgDeleteAvatar = findViewById(R.id.img_delete_avatar);

        llCaller.setOnClickListener(this);
        llCallNow.setOnClickListener(this);
        llRingTone.setOnClickListener(this);
        llTimer.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_caller:
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
                break;
            case R.id.ll_call_now:
                startActivity(new Intent(MainActivity.this,CallNow.class));
                break;
            case R.id.ll_ring_tone:
                startActivity(new Intent(MainActivity.this, RingtoneActivity.class));
                break;
            case R.id.ll_timer:
                break;
            default:
                break;

        }
    }



}
