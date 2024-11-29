package com.example.myapp;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.myapp.databinding.ActivityMenuBinding;

import com.example.myapp.viewmodel.UserViewModel;


public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Retrieve user data from the Intent
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        String userToken = intent.getStringExtra("userToken");
        String userAvatarPath = intent.getStringExtra("userAvatarPath");

        // Set user data in the ViewModel
        userViewModel.setUserID(userID);
        userViewModel.setUserName(userName);
        userViewModel.setUserToken(userToken);
        userViewModel.setUserAvatarPath(userAvatarPath);


        // Find the NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_menu);
        NavController navController = navHostFragment.getNavController();

        // Setup the ActionBar with NavController
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_people, R.id.navigation_group, R.id.navigation_moments)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Update the user avatar in the Toolbar
        System.out.println(userAvatarPath);
        ImageView userAvatar = findViewById(R.id.user_avatar);
        if (userAvatarPath != null && !userAvatarPath.isEmpty()) {
            Uri uri = Uri.parse(userAvatarPath);
            Glide.with(this).load(uri).into(userAvatar);
        }
    }

}
