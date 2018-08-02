package com.playgirl.hieunt.liecall;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgAvatar, imgRemoveAvatar;
    private EditText etName;
    private EditText etPhone;
    private Dialog mDialogInsertPicture;
    private Button btnDone;
    private String path = null;
    String img = null;
    private SharePre sharePre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        sharePre = new SharePre(this);

        imgAvatar = findViewById(R.id.img_avatar);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);

        String name = sharePre.getString("name");
        String phone = sharePre.getString("phone");
        path = sharePre.getString("img");
        if (path != null) {
            loadAvatar(path);
            img = path;
            imgRemoveAvatar.setVisibility(View.VISIBLE);
        }
        etName.setText(name);
        etPhone.setText(phone);
        imgRemoveAvatar = findViewById(R.id.img_delete_avatar);
        imgRemoveAvatar.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);

        mDialogInsertPicture = new Dialog(AddContactActivity.this);
        mDialogInsertPicture.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogInsertPicture.setContentView(R.layout.dialog_pick_picture);
    }

    private void loadAvatar(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgAvatar.setImageBitmap(myBitmap);

        }
    }

    public void choosePhotoFromGallary() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, 1);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                setSelectedPicture();
                break;
            case R.id.ll_choose_camera:
                takePhotoFromCamera();
                mDialogInsertPicture.dismiss();
                break;
            case R.id.ll_choose_galery:
                choosePhotoFromGallary();
                break;
            case R.id.ll_img_favorite:
                break;
            case R.id.img_delete_avatar:
                imgAvatar.setImageResource(R.drawable.avatar);
                imgRemoveAvatar.setVisibility(View.GONE);
                img = null;
            case R.id.btn_done:
                sharePre.saveString("name", etName.getText().toString().trim());
                sharePre.saveString("phone", etPhone.getText().toString().trim());
                sharePre.saveString("img", img);
                finish();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(data.getData());
                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        String path = getRealPathFromURI(tempUri);
                        img = path;
                        imgAvatar.setImageURI(tempUri);
                        imgRemoveAvatar.setVisibility(View.VISIBLE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                mDialogInsertPicture.dismiss();
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data.getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        String path = getRealPathFromURI(tempUri);
                        img = path;
                        imgAvatar.setImageURI(tempUri);
                        imgRemoveAvatar.setVisibility(View.VISIBLE);
                    }
                }
                mDialogInsertPicture.dismiss();
                break;
            default:
                mDialogInsertPicture.dismiss();
                break;

        }
    }

    private void setSelectedPicture() {
        mDialogInsertPicture.show();
        LinearLayout llChooseCamera, llChooseGalery, llImgfavorite;
        llChooseCamera = mDialogInsertPicture.findViewById(R.id.ll_choose_camera);
        llChooseGalery = mDialogInsertPicture.findViewById(R.id.ll_choose_galery);
        llImgfavorite = mDialogInsertPicture.findViewById(R.id.ll_img_favorite);
        llChooseCamera.setOnClickListener(this);
        llChooseGalery.setOnClickListener(this);
        llImgfavorite.setOnClickListener(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
