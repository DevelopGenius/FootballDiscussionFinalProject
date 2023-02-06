package com.example.footballdiscussion.fragments;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.activities.PostsActivity;
import com.example.footballdiscussion.databinding.FragmentRegisterPageBinding;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;
import com.example.footballdiscussion.view_modals.RegisterPageViewModel;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPageFragment extends Fragment {
    private FragmentRegisterPageBinding binding;
    private RegisterPageViewModel mViewModel;

    public static RegisterPageFragment newInstance() {
        return new RegisterPageFragment();
    }

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
                    binding.avatarImg.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.avatarImg.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterPageBinding.inflate(inflater, container, false);

        binding.registerButton.setOnClickListener(view1 -> {
            String username = binding.usernameRegisterEt.getText().toString();
            String password = binding.passwordRegisterEt.getText().toString();
            String phone = binding.phoneRegisterEt.getText().toString();
            String email = binding.emailRegisterEt.getText().toString();
            if (!isEmailValid(email)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
                builder.setTitle("Invalid Email")
                        .setMessage("The email address " + email + " is not valid.")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                User user = new User(username, phone, email, "");

                if (isImageSelected) {
                    binding.avatarImg.setDrawingCacheEnabled(true);
                    binding.avatarImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                    UserPostModel.instance().uploadImage(user.getUsername(), bitmap, url -> {
                        if (url != null) {
                            user.setImageUrl(url);
                        }
                    });
                }
                binding.registerProgressIndicator.show();
                mViewModel.addUser(user, password, (e) -> openPostsActivity());
            }
        });

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("media/*");
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(RegisterPageViewModel.class);
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void openPostsActivity() {
        Intent postsActivityIntent = new Intent(getActivity(), PostsActivity.class);
        startActivity(postsActivityIntent);
        getActivity().finish();
    }
}