package com.example.myapp.ui.people;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.ui.base.ChatItem;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements PeopleOnItemClickListener {

    //private FragmentPeopleBinding binding;
    private RecyclerView recyclerView;
    private PeopleListAdapter adapter;
    private List<ChatItem> chatItems;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatItems = new ArrayList<>();
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));

        adapter = new PeopleListAdapter(chatItems, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(ChatItem chatItem) {
        Intent intent = new Intent(requireContext(), PeopleChatActivity.class);
        intent.putExtra("friend_name", chatItem.getUserName());
        intent.putExtra("friend_message_preview", chatItem.getMessagePreview());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}