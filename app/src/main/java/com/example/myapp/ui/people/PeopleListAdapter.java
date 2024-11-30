package com.example.myapp.ui.people;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.ui.base.ChatItem;

import java.util.List;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ChatViewHolder> {
    private Context context;
    private List<ChatItem> chatItems;
    private PeopleOnItemClickListener listener;

    public PeopleListAdapter(Context context,List<ChatItem> chatItems, PeopleOnItemClickListener listener) {
        this.context = context;
        this.chatItems = chatItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem chatItem = chatItems.get(position);
        holder.userName.setText(chatItem.getUserName());
        holder.messagePreview.setText(chatItem.getMessagePreview());
        if(!chatItem.getAvatarPath().isEmpty()) {
            Uri uri = Uri.parse(chatItem.getAvatarPath());
            System.out.println(uri.toString());
            Glide.with(context)
                    .load(uri)
                    .into(holder.avatarImageView);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(chatItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView messagePreview;
        ImageView avatarImageView;

        ChatViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            messagePreview = itemView.findViewById(R.id.message_preview);
            avatarImageView = itemView.findViewById(R.id.user_avatar);
        }
    }
}



