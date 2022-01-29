package com.example.githubrepoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.githubrepoinfo.Adapters.IssueAdapter;
import com.example.githubrepoinfo.Network.ApiClient;
import com.example.githubrepoinfo.Network.ApiInterface;
import com.example.githubrepoinfo.Network.GetIssues;
import com.example.githubrepoinfo.Network.GetRepo;
import com.example.githubrepoinfo.RecyclerViewExtensions.RecyclerItemClickListener;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    RecyclerView issueRecycler;
    List<GetIssues> list;
    RelativeLayout layout1,layout2,layout3,layout4;
    ApiInterface apiInterface;
    TextView text1,text2,text3,text4;
    ImageView back;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");
        String repoName = intent.getStringExtra("repoName");
        String repoUrl = intent.getStringExtra("repoURL");
        int issue = intent.getIntExtra("issue", 0);
        int fork = intent.getIntExtra("fork", 0);
        String visi = intent.getStringExtra("visibility");
        int size = intent.getIntExtra("size",0);
        getIssues(UserID, repoName);
        layout1 = findViewById(R.id.issueLayout);
        layout2 = findViewById(R.id.forkLayout);
        layout3 = findViewById(R.id.sizeLayout);
        layout4 = findViewById(R.id.visibilityLayout);
        text1 = findViewById(R.id.issue);
        text2 = findViewById(R.id.size);
        text3 = findViewById(R.id.fork);
        text4 = findViewById(R.id.visibility);
        back = findViewById(R.id.back);

        issueRecycler = findViewById(R.id.issueRecycler);
        issueRecycler.setHasFixedSize(true);
        issueRecycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this));



        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueRecycler.setVisibility(View.VISIBLE);
                issueRecycler.setAdapter(new IssueAdapter(DetailActivity.this, list));
                issueRecycler.addOnItemTouchListener(new RecyclerItemClickListener(DetailActivity.this, issueRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Uri uri = Uri.parse(list.get(position).getHtmlUrl()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(repoUrl); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(repoUrl); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(repoUrl); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        text1.setText(issue+" ");
        text3.setText(fork+" ");
        text2.setText(size+" KB");
        text4.setText(visi);


    }

    public void getIssues(String userID, String repoName)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<GetIssues>> call = apiInterface.getIssues(userID, repoName);
        call.enqueue(new Callback<List<GetIssues>>() {
            @Override
            public void onResponse(Call<List<GetIssues>> call, Response<List<GetIssues>> response) {
                list = response.body();
                Log.d("@@REsponsee:", Arrays.toString(list.toArray()));

            }

            @Override
            public void onFailure(Call<List<GetIssues>> call, Throwable t) {
                Log.d("@@REsponsee: No res", t.getLocalizedMessage());

            }
        });
    }
}