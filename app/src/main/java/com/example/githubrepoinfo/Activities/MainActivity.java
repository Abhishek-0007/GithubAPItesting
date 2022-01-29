package com.example.githubrepoinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubrepoinfo.Adapters.RepoAdapter;
import com.example.githubrepoinfo.DetailActivity;
import com.example.githubrepoinfo.Network.ApiClient;
import com.example.githubrepoinfo.Network.ApiInterface;
import com.example.githubrepoinfo.Network.GetIssues;
import com.example.githubrepoinfo.Network.GetRepo;
import com.example.githubrepoinfo.Network.Owner;
import com.example.githubrepoinfo.R;
import com.example.githubrepoinfo.RecyclerViewExtensions.MiddleDividerItemDecoration;
import com.example.githubrepoinfo.RecyclerViewExtensions.RecyclerItemClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<GetRepo> list;
    private List<GetIssues> issuesList;
    ApiInterface apiInterface;
    RecyclerView repoRecycler;
    ImageView imageView, back;
    ShimmerFrameLayout mFrameLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String user = intent.getStringExtra("userName");
        getRepoList(user);

        imageView = findViewById(R.id.profile);
        mFrameLayout4 = findViewById(R.id.shimmerLayout4);

        back = findViewById(R.id.backq);
        repoRecycler = findViewById(R.id.reposRecycler);
        repoRecycler.setHasFixedSize(true);
        repoRecycler.addItemDecoration(new MiddleDividerItemDecoration(MainActivity.this, MiddleDividerItemDecoration.ALL));
        repoRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFrameLayout4.startShimmer();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mFrameLayout4.stopShimmer();
    }

    public void getRepoList(String userName) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<GetRepo>> call = apiInterface.getRepos(userName);
        call.enqueue(new Callback<List<GetRepo>>() {
            @Override
            public void onResponse(Call<List<GetRepo>> call, Response<List<GetRepo>> response) {
                mFrameLayout4.startShimmer();
                Log.d("@@REsponsee: No res", response.body().toString());
                Log.d("@@REsponsee: No res", response.message().toString());
                list = response.body();

                Glide.with(MainActivity.this).load(list.get(0).getOwner().getAvatarUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                imageView.setVisibility(View.VISIBLE);
                repoRecycler.setAdapter(new RepoAdapter(MainActivity.this, list));
                mFrameLayout4.setVisibility(View.VISIBLE);
                mFrameLayout4.setVisibility(View.GONE);
                mFrameLayout4.stopShimmer();

                repoRecycler.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, repoRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("UserID", list.get(position).getOwner().getLogin());
                        intent.putExtra("repoName",list.get(position).getName());
                        intent.putExtra("repoURL",list.get(position).getHtmlUrl());
                        intent.putExtra("issue",list.get(position).getOpenIssuesCount());
                        intent.putExtra("size",list.get(position).getSize());
                        intent.putExtra("fork",list.get(position).getForks());
                        intent.putExtra("visibility",list.get(position).getVisibility());
                        Log.d("@@REsponsee: ",list.get(position).getOwner().getLogin() );
                        Log.d("@@REsponsee: ",list.get(position).getName() );
                        Log.d("@@REsponsee: ",list.get(position).getHtmlUrl() );

             startActivity(intent);
                     }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
                Log.d("@@REsponsee: No res", list.get(0).getOwner().getAvatarUrl());

            }
            @Override
            public void onFailure(Call<List<GetRepo>> call, Throwable t) {
                Log.d("@@REsponsee: No res", t.getLocalizedMessage());
            }
        });
    }
}