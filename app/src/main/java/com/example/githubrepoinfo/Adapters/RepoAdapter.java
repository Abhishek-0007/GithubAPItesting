package com.example.githubrepoinfo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubrepoinfo.Network.GetRepo;
import com.example.githubrepoinfo.Network.Owner;
import com.example.githubrepoinfo.R;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoAdapterViewHolder>{
    private Context context;
    private List<GetRepo> repoAdapters;
    public RepoAdapter(Context context, List<GetRepo> repoAdapters) {
        this.context = context;
        this.repoAdapters = repoAdapters;
    }
    @NonNull
    @Override
    public RepoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.repo_layout, parent,false);
        return new RepoAdapter.RepoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapterViewHolder holder, int position) {
        GetRepo repoAdapter = repoAdapters.get(position);
        holder.modelName.setText(repoAdapter.getName());

    }

    @Override
    public int getItemCount() {
        try
        {
            return repoAdapters.size();
        }
        catch (NullPointerException e)
        {
            return 0;
        }

    }

    public class RepoAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView modelImg;
        TextView modelName;
        public RepoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            modelImg = itemView.findViewById(R.id.modelImg);
            modelName = itemView.findViewById(R.id.modelName);
        }
    }
}
