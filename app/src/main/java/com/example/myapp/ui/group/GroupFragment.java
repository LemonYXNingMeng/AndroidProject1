package com.example.myapp.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentGroupBinding;
import com.example.myapp.vo.GroupItem;

public class GroupFragment extends Fragment {

    private FragmentGroupBinding binding;
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private List<GroupItem> groupItems;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        groupItems = new ArrayList<>();
        groupItems.add(new GroupItem("Alice", "Member of Team A"));
        groupItems.add(new GroupItem("Bob1", "Leader of Team B"));
        groupItems.add(new GroupItem("Charlie", "Member of Team C"));

        adapter = new GroupAdapter(groupItems);
        recyclerView.setAdapter(adapter);

        return view;
        //return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}