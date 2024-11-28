package com.example.myapp.ui.people;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapp.R;
import com.example.myapp.ui.base.ChatContentItem;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PeopleChatAdapter extends RecyclerView.Adapter<PeopleChatAdapter.MessageViewHolder> {

    private List<ChatContentItem> messages;
    private Context context;

    public PeopleChatAdapter(Context context, List<ChatContentItem> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatContentItem chatItem = messages.get(position);
        holder.bind(chatItem);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView userAvatar;
        private TextView textView;
        private ImageView imageView;
        private ConstraintLayout container;

        MessageViewHolder(View itemView) {
            super(itemView);
            this.userAvatar = itemView.findViewById(R.id.user_avatar);
            this.textView = itemView.findViewById(R.id.textView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.container = itemView.findViewById(R.id.container);
        }


        void bind(ChatContentItem chatItem) {
            if (chatItem.getImage() != null) {
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(chatItem.getImage());
                Glide.with(context)
                        .load(chatItem.getImageUri())
                        .into(imageView);
                // 保存弹框
                imageView.setOnClickListener(v -> showPopup(chatItem.getImageUri()));
            } else {
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                textView.setText(chatItem.getText());
            }

            // Bind avatar
            Glide.with(itemView.getContext())
                    .load(chatItem.getAvatarUri())
                    .into(userAvatar);

            // Align based on whether the message is sent by user or not
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(container);

            if (chatItem.isSentByUser()) {
                userAvatar.setVisibility(View.GONE);
                constraintSet.clear(textView.getId(), ConstraintSet.START);
                constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.setHorizontalBias(textView.getId(), 1f);
                constraintSet.clear(imageView.getId(), ConstraintSet.START);
                constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.setHorizontalBias(imageView.getId(), 1f);
                textView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bubble_sent));
            } else {
                userAvatar.setVisibility(View.VISIBLE);
                constraintSet.clear(userAvatar.getId(), ConstraintSet.END);
                constraintSet.connect(userAvatar.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.setHorizontalBias(userAvatar.getId(), 0f);
                constraintSet.clear(textView.getId(), ConstraintSet.END);
                constraintSet.connect(textView.getId(), ConstraintSet.START, userAvatar.getId(), ConstraintSet.END);
                constraintSet.setHorizontalBias(textView.getId(), 0f);
                constraintSet.clear(imageView.getId(), ConstraintSet.END);
                constraintSet.connect(imageView.getId(), ConstraintSet.START, userAvatar.getId(), ConstraintSet.END);
                constraintSet.setHorizontalBias(imageView.getId(), 0f);
                textView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bubble_received));
            }
            constraintSet.applyTo(container);
        }

        private void showPopup(Uri imageUri) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_image_view, null);

            ImageView popupImageView = popupView.findViewById(R.id.popupImageView);
            Button saveButton = popupView.findViewById(R.id.saveButton);

            // Load the image into the popup's ImageView
            Glide.with(context)
                    .load(imageUri)
                    .apply(RequestOptions.centerCropTransform())
                    .into(popupImageView);

            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            // Set a touch listener to dismiss the popup when clicking outside
            popupView.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            });

            saveButton.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PeopleChatActivity.REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else {
                    saveImage(imageUri);
                    popupWindow.dismiss();
                }
            });

            // Show the popup window centered in the parent view
            popupWindow.showAtLocation(((AppCompatActivity) context).findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        }

        private void saveImage(Uri imageUri) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                File file = new File(storageDir, System.currentTimeMillis() + ".jpg");
                try (FileOutputStream out = new FileOutputStream(file)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                    Toast.makeText(context, "图片已保存", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "无法读取图片", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



