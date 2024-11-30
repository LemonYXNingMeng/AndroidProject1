package com.example.myapp.ui.people;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapp.R;
import com.example.myapp.database.repository.MessageRepository;
import com.example.myapp.databinding.ActivityPeopleChatBinding;
import com.example.myapp.ui.base.TitleView;
import com.example.myapp.ui.base.ChatContentItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeopleChatActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;
    static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 102;


    private ActivityPeopleChatBinding binding;
    private PeopleChatAdapter peopleChatAdapter;
    private List<ChatContentItem> messages;
    private MessageRepository messageRepository;
    //private Uri selectedImageUri;
    private String userID;
    private String friendID;
    private String friendAvatarPath;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_people_chat);
        binding = ActivityPeopleChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messageRepository = new MessageRepository(getApplicationContext());

        messages = new ArrayList<>();
        peopleChatAdapter = new PeopleChatAdapter(this, messages);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(peopleChatAdapter);

        binding.sendButton.setOnClickListener(v -> sendMessage());
        //binding.imagePickerButton.setOnClickListener(v -> pickImage());
        binding.imagePickerButton.setOnClickListener(v -> pickImage());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        // 顶部
        Intent intent =getIntent();
        String title = "This is Title";
        if (intent!=null) {
            title = intent.getStringExtra("friend_name");
            userID = intent.getStringExtra("user_ID");
            friendID = intent.getStringExtra("friend_ID");
            friendAvatarPath = intent.getStringExtra("friend_avatar_path");
            // 加载历史聊天记录
            loadChatMessages();
            /*System.out.println(userID);
            System.out.println(friendID);*/
        }

        //intent.putExtra("message_preview", chatItem.getMessagePreview());
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitleText(title);


        // Simulate receiving messages
        //simulateReceivingMessages();
    }
    private void sendMessage() {
        String messageText = binding.messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatContentItem chatItem = new ChatContentItem(
                    messageText,
                    null,
                    null,
                    null,
                    true
                    ); // true for sent by user
            messages.add(chatItem);
            peopleChatAdapter.notifyDataSetChanged();
            binding.recyclerView.scrollToPosition(messages.size() - 1);
            binding.messageInput.setText("");
            // 记录到数据库内
            messageRepository.insertMessage(userID,friendID,messageText,null,false);
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
                ChatContentItem chatItem = new ChatContentItem(
                        null,
                        bitmap,
                        selectedImageUri,
                        null,
                        true);
                messages.add(chatItem);
                peopleChatAdapter.notifyDataSetChanged();
                binding.recyclerView.scrollToPosition(messages.size() - 1);
                // 记录到数据库内
                messageRepository.insertMessage(userID,friendID,null,selectedImageUri.toString(),false);
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
                // Permission granted, you can now proceed with picking an image
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Handle saving image here if needed
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void simulateReceivingMessages() {
        //ChatContentItem receivedMessage = new ChatContentItem("Hello! How are you?", null, Uri.parse("https://img.alicdn.com/tfs/TB1GjUJrVzqK1RjSZPcXXbOqpXa-112-112.png"), false); // false for received from other
        ChatContentItem receivedMessage =
                new ChatContentItem("Hello! How are you?", null,null,
                Uri.parse(friendAvatarPath),
                false);
        messages.add(receivedMessage);

        //Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("file:///android_asset/OIP.jpg"));
        /*try {
            //uri->bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("OIP.jpg"));
            //bitmap->uri
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));

            ChatContentItem receivedImage = new ChatContentItem(null,bitmap,
                    Uri.parse("file:///android_asset/OIP.jpg"),
                    false, uri);
            messages.add(receivedImage);
        }catch (IOException e){
            e.printStackTrace();
            //System.out.println("失败");
        }*/

        messages.add(new ChatContentItem("Hello!", null,null, Uri.parse(friendAvatarPath), false));
        messages.add(new ChatContentItem("Hi there!", null,null, null, true));
        messages.add(new ChatContentItem("How are you?", null,null, Uri.parse(friendAvatarPath), false));
        peopleChatAdapter.notifyDataSetChanged();
    }

    private void loadChatMessages() {
        messages.addAll(messageRepository.getChatContentItemsByFriendID(userID,friendAvatarPath,friendID));
        peopleChatAdapter.notifyDataSetChanged();
    }




}



