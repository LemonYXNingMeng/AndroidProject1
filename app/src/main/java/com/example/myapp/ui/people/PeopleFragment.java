package com.example.myapp.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentPeopleBinding;
import com.example.myapp.ui.group.GroupAdapter;
import com.example.myapp.vo.ChatItem;
import com.example.myapp.vo.GroupItem;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {

    private FragmentPeopleBinding binding;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatItem> chatItems;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatItems = new ArrayList<>();
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));

        adapter = new ChatAdapter(chatItems);
        recyclerView.setAdapter(adapter);

        return view;
        //return inflater.inflate(R.layout.fragment_people, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}