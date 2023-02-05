package com.example.footballdiscussion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.ActivityPostsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PostsActivity extends AppCompatActivity {
    private ActivityPostsBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_posts);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view_posts);
        NavigationUI.setupWithNavController(navView,navController);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }
}