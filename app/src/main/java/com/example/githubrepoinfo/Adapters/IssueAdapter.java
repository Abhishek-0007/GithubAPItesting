package com.example.githubrepoinfo.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubrepoinfo.Network.GetIssues;
import com.example.githubrepoinfo.R;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueAdapterViewHolder> {

    private Context context;
    private List<GetIssues> issuesList;

    public IssueAdapter(Context context, List<GetIssues> issuesList) {
        this.context = context;
        this.issuesList = issuesList;
    }

    @NonNull
    @Override
    public IssueAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.issue_layout, parent,false);
        return new IssueAdapter.IssueAdapterViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IssueAdapterViewHolder holder, int position) {
    GetIssues issueAdapter = issuesList.get(position);
    holder.issueTitle.setText(issueAdapter.getTitle());
    holder.issueCount.setText(position+": ");
    }

    @Override
    public int getItemCount() {
        try
        {
            return issuesList.size();
        }
        catch (NullPointerException e)
        {
            return 0;
        }
    }

    public class IssueAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView issueCount;
        TextView issueTitle;

        public IssueAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            issueCount = itemView.findViewById(R.id.issueLevel);
            issueTitle = itemView.findViewById(R.id.issueTitle);
        }
    }
}

