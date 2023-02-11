package com.example.footballdiscussion.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.footballdiscussion.databinding.ActivityMainBinding;
import com.example.footballdiscussion.view_modals.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.startApplicationProgressIndicator.show();
        mainViewModel.userLoggedInHandler((unused) -> startLoggedInActivity(),
                (unused) -> startLoggedOutActivity());
    }


    public void startLoggedOutActivity() {
        Intent intent = new Intent(this, LoggedOutActivity.class);
        startActivity(intent);
        finish();
    }

    public void startLoggedInActivity() {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
        finish();
    }
}