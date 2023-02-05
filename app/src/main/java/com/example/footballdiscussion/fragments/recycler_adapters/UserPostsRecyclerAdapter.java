package com.example.footballdiscussion.fragments.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentUserPostRowBinding;
import com.example.footballdiscussion.models.entities.UserPost;

import java.util.List;

class UserPostsViewHolder extends RecyclerView.ViewHolder {
    private FragmentUserPostRowBinding binding;

    public UserPostsViewHolder(@NonNull FragmentUserPostRowBinding binding, OnItemClickListener listener, List<UserPost> data) {
        super(binding.getRoot());
        this.binding = binding;
        itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
    }

    public void bind(UserPost userPost) {
        if (userPost.isOwnPost()) {
            binding.userPostRowEditLikeIcon.setImageResource(R.drawable.baseline_edit_24);
        } else {
            binding.userPostRowEditLikeIcon.setImageResource(userPost.isLiked() ?
                    R.drawable.baseline_thumb_up_alt_24 : R.drawable.baseline_thumb_up_off_alt_24);
        }
        binding.userPostRowTitle.setText(userPost.getPostTitle());
        binding.userPostRowUsername.setText(userPost.getUsername());
    }
}

public class UserPostsRecyclerAdapter extends RecyclerView.Adapter<UserPostsViewHolder> {
    List<UserPost> data;
    LayoutInflater inflater;
    OnItemClickListener listener;


    public UserPostsRecyclerAdapter(LayoutInflater inflater, List<UserPost> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<UserPost> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostsViewHolder holder, int position) {
        holder.bind(data.get(position));
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
        return new UserPostsViewHolder(binding, listener, data);
    }
}
