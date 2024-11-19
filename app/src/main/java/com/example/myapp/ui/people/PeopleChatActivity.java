package com.example.myapp.ui.people;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.ui.base.TitleView;
import com.example.myapp.R;
import com.example.myapp.ui.base.PeopleMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeopleChatActivity extends AppCompatActivity {
    TitleView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_chat);

        Intent intent =getIntent();
        String title = "This is Title";
        if (intent!=null) {
            title = intent.getStringExtra("friend_name");
        }
        //intent.putExtra("message_preview", chatItem.getMessagePreview());

        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitleText(title);
    }




}



