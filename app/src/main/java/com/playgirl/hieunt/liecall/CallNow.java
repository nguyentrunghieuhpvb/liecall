package com.playgirl.hieunt.liecall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CallNow extends AppCompatActivity {
    private ImageView img;
    private TextView tvName, tvphone;
    private ImageView btnAccept, btnDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_call_noew);
        getSupportActionBar().hide();
        SharePre sharePre = new SharePre(this);


        String name = sharePre.getString("name");
        String phone = sharePre.getString("phone");
        String path = sharePre.getString("img");

        img = findViewById(R.id.call_now_img);
        tvName = findViewById(R.id.call_noew_name);
        tvphone = findViewById(R.id.call_now_phone);
        btnAccept = findViewById(R.id.img_accept);
        btnDecline = findViewById(R.id.img_decline);
        tvName.setText(name);
        tvphone.setText(phone);
        if (path != null) {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);
            }
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CallNow.this, AcceptCall.class));
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
