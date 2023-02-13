package com.example.footballdiscussion.fragments.recycler_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentUserPostRowBinding;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.utils.ImageUtils;

import java.util.List;

class UserPostsViewHolder extends RecyclerView.ViewHolder {
    private FragmentUserPostRowBinding binding;

    public UserPostsViewHolder(@NonNull FragmentUserPostRowBinding binding, OnItemClickListener listener,
                               OnIconClickListener onIconClickListener,
                               OnIconClickListener onDeleteClickListener,
                               List<UserPost> data) {
        super(binding.getRoot());
        this.binding = binding;
        itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        binding.userPostRowEditLikeIcon.setOnClickListener(view -> onIconClickListener.onIconClick(data.stream()
                .filter(userPost -> userPost.getId().equals(binding
                        .userPostRowEditLikeIcon.getTag())).findFirst().get()));
        binding.userPostRowDeletePost.setOnClickListener(view -> onDeleteClickListener.onIconClick(data.stream()
                .filter(userPost -> userPost.getId().equals(binding
                        .userPostRowEditLikeIcon.getTag())).findFirst().get()));

    }

    private boolean isOwnPost(UserPost userPost, String currentUserId) {
        return userPost.getUserId().equals(currentUserId);
    }

    private boolean isLikedPost(UserPost userPost, String currentUserId) {
        return userPost.getUserLikes().contains(currentUserId);
    }

    public void bind(UserPost userPost, String currentUserId) {
        if (isOwnPost(userPost, currentUserId)) {
            binding.userPostRowEditLikeIcon.setImageResource(R.drawable.baseline_edit_24);
            binding.userPostRowDeletePost.setVisibility(View.VISIBLE);
            binding.userPostRowDeletePost.setTag(userPost.getId());
        } else {
            binding.userPostRowDeletePost.setVisibility(View.GONE);
            binding.userPostRowEditLikeIcon.setImageResource(isLikedPost(userPost, currentUserId) ?
                    R.drawable.baseline_thumb_up_alt_24 : R.drawable.baseline_thumb_up_off_alt_24);
        }
        binding.userPostRowEditLikeIcon.setTag(userPost.getId());
        binding.userPostRowTitle.setText(userPost.getPostTitle());
        binding.userPostRowUsername.setText(userPost.getUsername());
        binding.userPostRowLikesNumber.setText(Integer.toString(userPost.getUserLikes().size()));
        ImageUtils.loadImage(userPost.getImageUrl(), binding.userPostRowImage, R.drawable.football_stadium);
    }
}

public class UserPostsRecyclerAdapter extends RecyclerView.Adapter<UserPostsViewHolder> {
    List<UserPost> data;

    String currentUserId;
    LayoutInflater inflater;
    OnItemClickListener listener;
    OnIconClickListener onIconClickListener;
    OnIconClickListener onDeleteClickListener;

    public UserPostsRecyclerAdapter(LayoutInflater inflater, List<UserPost> data, String currentUserId) {
        this.inflater = inflater;
        this.data = data;
        this.currentUserId = currentUserId;
    }

    public void setData(List<UserPost> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.onIconClickListener = onIconClickListener;
    }

    public void setOnDeleteClickListener(OnIconClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostsViewHolder holder, int position) {
        holder.bind(data.get(position), currentUserId);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }


    @NonNull
    @Override
    public UserPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentUserPostRowBinding binding = FragmentUserPostRowBinding.inflate(this.inflater, parent, false);
        return new UserPostsViewHolder(binding, listener, onIconClickListener, onDeleteClickListener, data);
    }
}
