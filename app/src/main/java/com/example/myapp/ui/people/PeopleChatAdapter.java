package com.example.myapp.ui.people;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapp.FullScreenImageActivity;
import com.example.myapp.R;
import com.example.myapp.vo.ChatContentItem;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

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

            imageView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ChatContentItem chatItem = messages.get(position);
                    Uri imageUri = chatItem.getImageUri();
                    if (imageUri != null) {
                        Intent intent = new Intent(context, FullScreenImageActivity.class);
                        intent.putExtra("image_uri", imageUri.toString());
                        context.startActivity(intent);
                    }
                }
            });
        }

        void bind(ChatContentItem chatItem) {
            if (chatItem.getImage() != null) {
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(chatItem.getImage());
                Glide.with(context)
                        .load(chatItem.getImageUri())
                        .into(imageView);
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
    }
}



