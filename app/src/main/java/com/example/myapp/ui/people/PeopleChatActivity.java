package com.example.myapp.ui.people;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.databinding.ActivityPeopleChatBinding;
import com.example.myapp.ui.base.TitleView;
import com.example.myapp.vo.ChatContentItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeopleChatActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;

    private ActivityPeopleChatBinding binding;
    private PeopleChatAdapter peopleChatAdapter;
    private List<ChatContentItem> messages;
    TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_people_chat);
        binding = ActivityPeopleChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize TitleView
        Intent intent =getIntent();
        String title = "This is Title";
        if (intent!=null) {
            title = intent.getStringExtra("friend_name");
        }
        //intent.putExtra("message_preview", chatItem.getMessagePreview());
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitleText(title);

        messages = new ArrayList<>();
        peopleChatAdapter = new PeopleChatAdapter(this, messages);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(peopleChatAdapter);

        binding.sendButton.setOnClickListener(v -> sendMessage());
        binding.imagePickerButton.setOnClickListener(v -> pickImage());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        // Simulate receiving messages
        simulateReceivingMessages();
    }
    private void sendMessage() {
        String messageText = binding.messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatContentItem chatItem = new ChatContentItem(messageText, null,
                    null, true,
                    null); // true for sent by user
            messages.add(chatItem);
            peopleChatAdapter.notifyDataSetChanged();
            binding.recyclerView.scrollToPosition(messages.size() - 1);
            binding.messageInput.setText("");
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                //ChatContentItem chatItem = new ChatContentItem(null, bitmap, Uri.parse("https://img.alicdn.com/tfs/TB1GjUJrVzqK1RjSZPcXXbOqpXa-112-112.png"), true); // true for sent by user
                ChatContentItem chatItem = new ChatContentItem(null, bitmap,
                        null,
                        true,selectedImageUri);
                messages.add(chatItem);
                peopleChatAdapter.notifyDataSetChanged();
                binding.recyclerView.scrollToPosition(messages.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    private void simulateReceivingMessages() {
        // Simulate receiving text message with avatar
        //ChatContentItem receivedMessage = new ChatContentItem("Hello! How are you?", null, Uri.parse("https://img.alicdn.com/tfs/TB1GjUJrVzqK1RjSZPcXXbOqpXa-112-112.png"), false); // false for received from other
        ChatContentItem receivedMessage = new ChatContentItem("Hello! How are you?", null,
                Uri.parse("file:///android_asset/OIP.jpg"),
                false,null);
        messages.add(receivedMessage);

        //Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("file:///android_asset/OIP.jpg"));
        ChatContentItem receivedImage = new ChatContentItem(null, getPlaceholderBitmap(),
                Uri.parse("file:///android_asset/OIP.jpg"),
                false,Uri.parse("file:///android_asset/OIP.jpg"));
        messages.add(receivedImage);
        peopleChatAdapter.notifyDataSetChanged();
    }

    private Bitmap getPlaceholderBitmap() {
        // Create a simple placeholder bitmap
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    }




}



