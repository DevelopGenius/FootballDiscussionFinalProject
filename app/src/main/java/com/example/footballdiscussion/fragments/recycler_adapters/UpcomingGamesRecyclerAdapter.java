package com.example.footballdiscussion.fragments.recycler_adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballdiscussion.databinding.FragmentUpcomingGameRowBinding;
import com.example.footballdiscussion.models.entities.UpcomingGame;

import java.util.List;


class UpcomingGamesViewHolder extends RecyclerView.ViewHolder {
    private FragmentUpcomingGameRowBinding binding;

    public UpcomingGamesViewHolder(@NonNull FragmentUpcomingGameRowBinding binding, UpcomingGamesRecyclerAdapter.OnItemClickListener listener, List<UpcomingGame> data) {
        super(binding.getRoot());
        this.binding = binding;
        itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
    }

    public void bind(UpcomingGame upcomingGame) {
        binding.upcomingGameRowTitle.setText(upcomingGame.getGameTitle());
        binding.upcomingGameRowDescription.setText(upcomingGame.getGameDescription());
    }
}


public class UpcomingGamesRecyclerAdapter extends RecyclerView.Adapter<UpcomingGamesViewHolder> {
    List<UpcomingGame> data;
    LayoutInflater inflater;
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public UpcomingGamesRecyclerAdapter(LayoutInflater inflater, List<UpcomingGame> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<UpcomingGame> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingGamesViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }


    @NonNull
    @Override
    public UpcomingGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentUpcomingGameRowBinding binding = FragmentUpcomingGameRowBinding.inflate(this.inflater, parent, false);
        return new UpcomingGamesViewHolder(binding, listener, data);
    }
}
