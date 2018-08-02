package com.playgirl.hieunt.liecall;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PrivateKey;

public class SharePre {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharePre(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
