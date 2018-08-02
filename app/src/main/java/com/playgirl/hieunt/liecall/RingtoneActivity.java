package com.playgirl.hieunt.liecall;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class RingtoneActivity extends AppCompatActivity {

    private String TAG = "RingtoneActivity";

    private RecyclerView rvRingTone;
    private ImageView imgSave;
    private ArrayList<String> listUri = new ArrayList<>();
    private ArrayList<String> listTitle = new ArrayList<>();
    private RingtoneAdapter adapter;
    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ringtone);
        rvRingTone = findViewById(R.id.rv_list_ringtone);
        imgSave = findViewById(R.id.iv_save_ringtone);
        rvRingTone.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RingtoneAdapter(new ArrayList<String>(), new RingtoneAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int pos) {
//                if (ringtone != null) {
//                    ringtone.stop();
//                }
//                ringtone = RingtoneManager.getRingtone(RingtoneActivity.this, Uri.parse(listUri.get(pos)));
//                Log.d(TAG, "uri  : " + listUri.get(pos));
//                ringtone.play();

                RingtoneManager manager = new RingtoneManager(getApplicationContext());
                manager.setType(RingtoneManager.TYPE_RINGTONE);
                Ringtone ringtone = manager.getRingtone(1);
                ringtone.play();
            }
        });
        getListRingTone();
        rvRingTone.setAdapter(adapter);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getListRingTone() {
        listTitle.add("Default");
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.d(TAG, "uri : " + uri);
            listTitle.add(title);
            listUri.add(uri);
        }
        adapter.setListRingTone(listTitle);

    }
}
