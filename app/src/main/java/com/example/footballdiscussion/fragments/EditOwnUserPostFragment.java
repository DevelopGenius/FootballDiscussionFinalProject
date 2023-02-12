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
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentEditOwnUserPostBinding;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.utils.ImageUtils;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;


public class EditOwnUserPostFragment extends Fragment {

    FragmentEditOwnUserPostBinding binding;
    UserPostsViewModel viewModel;
    private UserPost userPost;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    boolean isImageSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editPostImg.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editPostImg.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditOwnUserPostBinding.inflate(inflater, container, false);
        String userPostId = UserPostDetailsFragmentArgs.fromBundle(getArguments()).getUserPostId();

        viewModel.getUserPostById(userPostId).observe(getViewLifecycleOwner(), userPost -> {
            setUserPostData(userPost);
        });

        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);
    }

    private void setUserPostData(UserPost userPost) {
        if (userPost != null) {
            this.userPost = userPost;
            ImageUtils.loadImage(userPost.getImageUrl(), binding.editPostImg, R.drawable.football_stadium);
            binding.editPostTitleEt.setText(userPost.getPostTitle());
        }
    }

    private void updateUserPost(View view) {
        viewModel.updateUserPost(userPost, (unused) -> {
            binding.editPostProgressIndicator.setVisibility(View.GONE);
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }, (e) -> {
            binding.editPostProgressIndicator.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Error")
                    .setMessage(e)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

    private void setListeners() {
        binding.savePostButton.setOnClickListener(view -> {
            String newTitle = binding.editPostTitleEt.getText().toString();

            if (newTitle.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Empty title")
                        .setMessage("The title must not be empty")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                binding.editPostProgressIndicator.setVisibility(View.VISIBLE);
                userPost.setPostTitle(newTitle);
                if (isImageSelected) {
                    binding.editPostImg.setDrawingCacheEnabled(true);
                    binding.editPostImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.editPostImg.getDrawable()).getBitmap();
                    viewModel.uploadImage(userPost.getId(), bitmap, url -> {
                        if (url != null) {
                            userPost.setImageUrl(url);
                            updateUserPost(view);
                        }
                    });
                } else {
                    updateUserPost(view);
                }

            }
        });

        binding.editPostCameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.editPostGalleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });
    }

}