package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentUserPostsBinding;
import com.example.footballdiscussion.databinding.FragmentViewProfileBinding;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.utils.ImageUtils;
import com.example.footballdiscussion.view_modals.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class ViewProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private FragmentViewProfileBinding binding;

    public static ViewProfileFragment newInstance() {
        return new ViewProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentViewProfileBinding.inflate(inflater, container, false);
        loadProfileData();
        setListeners();
        return binding.getRoot();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

    }

    private void loadProfileData() {
        User currentUser = viewModel.getCurrentUser();
        ImageUtils.loadImage(currentUser.getImageUrl(), binding.viewProfileImg, R.drawable.empty_profile);

        binding.viewProfileEmail.setText(currentUser.getEmail());
        binding.viewProfilePhone.setText(currentUser.getPhone());
        binding.viewProfileUsername.setText(currentUser.getUsername());
    }

    private void setListeners() {
        binding.viewProfileEditButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(ViewProfileFragmentDirections.actionViewProfileFragmentToEditProfileFragment());
        });
    }
}