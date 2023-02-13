package com.example.footballdiscussion.fragments.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentCommentRowBinding;
import com.example.footballdiscussion.models.entities.UserPostComment;

import java.util.List;

class UserCommentsViewHolder extends RecyclerView.ViewHolder {
    List<UserPostComment> data;
    private FragmentCommentRowBinding binding;

    public UserCommentsViewHolder(@NonNull FragmentCommentRowBinding binding, List<UserPostComment> data) {
        super(binding.getRoot());
        this.binding = binding;
        this.data = data;
    }

    public void bind(UserPostComment userPostComment) {
        binding.userPostCommentUserIcon.setImageResource(R.drawable.baseline_person_24);
        binding.userPostCommentUsername.setText(userPostComment.getUsername());
        binding.userPostCommentTextView.setText(userPostComment.getComment());
    }
}

public class UserCommentsRecyclerAdapter extends RecyclerView.Adapter<UserCommentsViewHolder> {
    List<UserPostComment> data;
    String currentUserId;
    LayoutInflater inflater;

    public UserCommentsRecyclerAdapter(LayoutInflater inflater, List<UserPostComment> data, String currentUserId) {
        this.inflater = inflater;
        this.data = data;
        this.currentUserId = currentUserId;
    }

    public void setData(List<UserPostComment> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull UserCommentsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    @NonNull
    @Override
    public UserCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCommentRowBinding binding = FragmentCommentRowBinding.inflate(this.inflater, parent, false);
        return new UserCommentsViewHolder(binding, data);
    }
}
