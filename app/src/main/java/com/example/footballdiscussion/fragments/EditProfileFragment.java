package com.example.footballdiscussion.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentEditProfileBinding;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.view_modals.ProfileViewModel;
import com.squareup.picasso.Picasso;


public class EditProfileFragment extends Fragment {
    private ProfileViewModel viewModel;
    private FragmentEditProfileBinding binding;

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    private Boolean isImageSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editProfileImg.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editProfileImg.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        loadProfileData();

        binding.editProfileSaveButton.setOnClickListener(view -> {
            String username = binding.usernameEditProfileEt.getText().toString();
            String phone = binding.phoneEditProfileEt.getText().toString();
            String email = binding.emailEditProfileEt.getText().toString();
            Bitmap bitmap = null;
            if (isImageSelected) {
                binding.editProfileImg.setDrawingCacheEnabled(true);
                binding.editProfileImg.buildDrawingCache();
                bitmap = ((BitmapDrawable) binding.editProfileImg.getDrawable()).getBitmap();
            }
            viewModel.updateUserProfile(username, email, phone, bitmap, (unused) -> {

            }, (error) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Error at updating")
                        .setMessage(error)
                        .setPositiveButton("OK", null)
                        .show();
            });
        });
        binding.editProfileCameraButton.setOnClickListener(view -> {
            cameraLauncher.launch(null);
        });

        binding.editProfileGalleryButton.setOnClickListener(view -> {
            galleryLauncher.launch("image/*");
        });


        return binding.getRoot();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

    }

    private void loadProfileData() {
        User currentUser = viewModel.getCurrentUser();
        if (currentUser.getImageUrl()  != null && currentUser.getImageUrl().length() > 5) {
            Picasso.get().load(currentUser.getImageUrl())
                    .placeholder(R.drawable.empty_profile).into(binding.editProfileImg);
        } else{
            binding.editProfileImg.setImageResource(R.drawable.empty_profile);
        }

        binding.emailEditProfileEt.setText(currentUser.getEmail());
        binding.phoneEditProfileEt.setText(currentUser.getPhone());
        binding.usernameEditProfileEt.setText(currentUser.getUsername());
    }
}