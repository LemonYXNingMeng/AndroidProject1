package com.example.myapp.ui.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.ui.base.GroupItem;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<GroupItem> groupItems;

    public GroupAdapter(List<GroupItem> groupItems) {
        this.groupItems = groupItems;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chat_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupItem groupItem = groupItems.get(position);
        holder.bind(groupItem);
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;

        GroupViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.group_name);
            textViewDescription = itemView.findViewById(R.id.group_message_preview);
        }

        void bind(GroupItem groupItem) {
            textViewName.setText(groupItem.getName());
            textViewDescription.setText(groupItem.getDescription());
        }
    }
}



