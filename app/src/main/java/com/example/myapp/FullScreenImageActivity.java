package com.example.myapp;

import android.view.ViewGroup;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.net.Uri;
import android.graphics.drawable.BitmapDrawable;

public class FullScreenImageActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private PhotoView photoView;
    private Button saveButton;
    private Uri imageUri;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        photoView = findViewById(R.id.photoView);
        saveButton = findViewById(R.id.saveButton);

        // Get image data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("image_uri")) {
                imageUri = Uri.parse(extras.getString("image_uri"));
                loadImageFromUri(imageUri);
            } else if (extras.containsKey("image_path")) {
                imagePath = extras.getString("image_path");
                loadImageFromAssets(imagePath);
            }
        }

        saveButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(FullScreenImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FullScreenImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {
                saveImage();
            }
        });

        photoView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showPopup();
            }
            return true;
        });
    }

    private void loadImageFromUri(Uri uri) {
        if (uri.toString().startsWith("file:///android_asset/")) {
            String assetPath = uri.toString().substring("file:///android_asset/".length());
            loadImageFromAssets(assetPath);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                            photoView.setImageBitmap(resource);
                        }
                    });
        }
    }

    private void loadImageFromAssets(String path) {
        Glide.with(this)
                .asBitmap()
                .load(Uri.parse("file:///android_asset/" + path))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        photoView.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage() {
        Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File file = new File(storageDir, System.currentTimeMillis() + ".jpg");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            Toast.makeText(FullScreenImageActivity.this, "图片已保存", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FullScreenImageActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_save_image, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView saveText = popupView.findViewById(R.id.save_text);
        saveText.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(FullScreenImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FullScreenImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {
                saveImage();
            }
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(photoView, -popupView.getWidth()/2, 50);
    }
}



