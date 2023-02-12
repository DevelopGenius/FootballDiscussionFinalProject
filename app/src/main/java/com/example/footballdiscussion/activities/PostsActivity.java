package com.example.footballdiscussion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.ActivityPostsBinding;
import com.example.footballdiscussion.utils.Logout;
import com.example.footballdiscussion.view_modals.PostsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PostsActivity extends AppCompatActivity {
    private ActivityPostsBinding binding;
    NavController navController;
    private PostsViewModel postsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_posts);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view_posts);
        NavigationUI.setupWithNavController(navView, navController);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_icon) {
            Logout.logout((unused) -> logoutActivity());
        }
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}