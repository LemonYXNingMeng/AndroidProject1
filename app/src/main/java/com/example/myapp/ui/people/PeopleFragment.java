package com.example.myapp.ui.people;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.database.repository.UserRepository;
import com.example.myapp.ui.base.ChatItem;
import com.example.myapp.viewmodel.UserViewModel;
import java.util.List;

public class PeopleFragment extends Fragment implements PeopleOnItemClickListener {

    //private FragmentPeopleBinding binding;
    private RecyclerView recyclerView;
    private PeopleListAdapter adapter;
    private List<ChatItem> chatItems;
    private UserRepository userRepository;
    private UserViewModel userViewModel;

    private String userID;
    private String userName;
    private String userToken;
    private String userAvatarPath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the ViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        // Observe user data changes
        userViewModel.getUserID().observe(this, id -> {
            userID = id;
            Log.d("PeopleFragment", "Received Data: UserID=" + userID);

            chatItems = userRepository.getAllUsers(userID);
            adapter = new PeopleListAdapter(chatItems, this);
            recyclerView.setAdapter(adapter);
        });

        userViewModel.getUserName().observe(this, name -> {
            userName = name;
            Log.d("PeopleFragment", "Received Data: UserName=" + userName);
        });

        userViewModel.getUserToken().observe(this, token -> {
            userToken = token;
            Log.d("PeopleFragment", "Received Data: UserToken=" + userToken);
        });

        userViewModel.getUserAvatarPath().observe(this, path -> {
            userAvatarPath = path;
            Log.d("PeopleFragment", "Received Data: UserAvatarPath=" + userAvatarPath);
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userRepository = new UserRepository(getContext());
        chatItems = userRepository.getAllUsers(userID);
        /*chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));
        chatItems.add(new ChatItem("Alice", "Member of Team A"));
        chatItems.add(new ChatItem("Bob", "Leader of Team B"));
        chatItems.add(new ChatItem("Charlie", "Member of Team C"));*/
        adapter = new PeopleListAdapter(chatItems, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(ChatItem chatItem) {
        Intent intent = new Intent(requireContext(), PeopleChatActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("friend_ID",chatItem.getUserID());
        intent.putExtra("friend_name", chatItem.getUserName());
        //intent.putExtra("friend_message_preview", chatItem.getMessagePreview());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }


}