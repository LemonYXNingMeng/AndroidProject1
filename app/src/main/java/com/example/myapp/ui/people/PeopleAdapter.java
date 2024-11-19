package com.example.myapp.ui.people;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.vo.ChatItem;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ChatViewHolder> {

    private List<ChatItem> chatItems;
    private PeopleOnItemClickListener listener;

    public PeopleAdapter(List<ChatItem> chatItems, PeopleOnItemClickListener listener) {
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

        ChatViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            messagePreview = itemView.findViewById(R.id.message_preview);
        }
    }
}



