package com.example.myapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.pm.PackageManager;

import android.provider.MediaStore;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.database.helper.UserDatabaseHelper;
import com.example.myapp.database.repository.UserRepository;
import com.example.myapp.vo.User;


public class MainActivity extends AppCompatActivity {
    private Uri selectedAvatarUri;
    private ImageView avatarImageView;
    private EditText passwordTextPassword, userAccountText;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button sign_in =  findViewById(R.id.signin_button);
        userAccountText = findViewById(R.id.user_account_Text);
        passwordTextPassword = findViewById(R.id.password_TextPassword);

        userRepository = new UserRepository(this);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = signIn();
                if(user==null) return;
                Intent intent = new Intent(MainActivity.this,MenuActivity.class);

                intent.putExtra("userID",user.getUserID());
                intent.putExtra("userName",user.getName());
                intent.putExtra("userToken",user.getToken());
                intent.putExtra("userAvatarPath",user.getAvatarPath());

                // 设置启动标志,避免退回登录界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });


        // Request permission for reading external storage
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }*/
    }

    private User signIn(){
        String userID = userAccountText.getText().toString().trim();
        String password = passwordTextPassword.getText().toString().trim();
        if (userID.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请确保账号或密码不为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        User user = userRepository.checkUserToken(userID,password);
        if (user==null){
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            return null;
        }
        return user;
    }

    private void showRegisterDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.register_dialog, null);

        final EditText phoneEditText = dialogView.findViewById(R.id.phone_editText);
        final EditText nameEditText = dialogView.findViewById(R.id.name_editText);
        final EditText passwordEditText = dialogView.findViewById(R.id.password_editText);
        avatarImageView = dialogView.findViewById(R.id.avatar_imageView); // Initialize the avatar ImageView here
        Button selectAvatarButton = dialogView.findViewById(R.id.select_avatar_button);
        Button registerConfirmButton = dialogView.findViewById(R.id.register_confirm_button);

        selectAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForImage();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();

        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();


                if (!name.isEmpty() && !password.isEmpty()) {
                    // 注册服务
                    String userID = userRepository.registerUser(phone,name,password,selectedAvatarUri.toString());
                    Toast.makeText(MainActivity.this, "注册成功,您的账号为" + userID, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "请确保手机号，账号名称，密码不为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();
    }

    private void openGalleryForImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedAvatarUri = data.getData();
            if (avatarImageView != null) { // Check if avatarImageView is not null
                Glide.with(this)
                        .load(selectedAvatarUri)
                        .into(avatarImageView);
            } else {
                Toast.makeText(this, "载入图像失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now proceed with picking an image
            } else {
                Toast.makeText(this, "权限禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 100;
}