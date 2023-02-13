package com.example.footballdiscussion.fragments.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentUpcomingGameRowBinding;
import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.utils.ImageUtils;
import java.util.List;

class UpcomingGamesViewHolder extends RecyclerView.ViewHolder {
    private FragmentUpcomingGameRowBinding binding;

    public UpcomingGamesViewHolder(@NonNull FragmentUpcomingGameRowBinding binding, List<UpcomingGame> data) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(UpcomingGame upcomingGame) {
        binding.upcomingGameRowTitle.setText(upcomingGame.getGameTitle());
        binding.upcomingGameRowDescription.setText(upcomingGame.getGameDescription());
        ImageUtils.loadImage(upcomingGame.getImageUrl(), binding.upcomingGameRowImage, R.drawable.football_stadium);
    }
}


public class UpcomingGamesRecyclerAdapter extends RecyclerView.Adapter<UpcomingGamesViewHolder> {
    List<UpcomingGame> data;
    LayoutInflater inflater;

    public UpcomingGamesRecyclerAdapter(LayoutInflater inflater, List<UpcomingGame> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<UpcomingGame> data) {
        this.data = data;
        notifyDataSetChanged();
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
        return new UpcomingGamesViewHolder(binding, data);
    }
}
