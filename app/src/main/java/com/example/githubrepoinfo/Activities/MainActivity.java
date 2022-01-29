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
    ImageView imageView;
    ShimmerFrameLayout mFrameLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRepoList();

        imageView = findViewById(R.id.profile);
        mFrameLayout4 = findViewById(R.id.shimmerLayout4);


        repoRecycler = findViewById(R.id.reposRecycler);
        repoRecycler.setHasFixedSize(true);
        repoRecycler.addItemDecoration(new MiddleDividerItemDecoration(MainActivity.this, MiddleDividerItemDecoration.ALL));
        repoRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));


    }

    @Override
    protected void onStart() {
        super.onStart();
        getRepoList();
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

    public void getRepoList() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<GetRepo>> call = apiInterface.getRepos();
        call.enqueue(new Callback<List<GetRepo>>() {
            @Override
            public void onResponse(Call<List<GetRepo>> call, Response<List<GetRepo>> response) {
                mFrameLayout4.startShimmer();
                Log.d("@@REsponsee: No res", response.body().toString());
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
//                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
//                        alertDialog.setTitle(list.get(position).getName());
//                        alertDialog.setMessage("Number of issues: "+list.get(position).getOpenIssuesCount()+ "\nSize: "+list.get(position).getSize()+" KB" + "\nForks: "+list.get(position).getForks()+ "\nVisibility: "+list.get(position).getVisibility());
//
//                        alertDialog.setButton("View More Issues..", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                 getIssues(list.get(position).getOwner().getLogin(), list.get(position).getName());
//
//                            }
//                        });
//
//                        alertDialog.show();  //<-- See This!
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

    public void getIssues(String userID, String repoName)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<GetIssues>> call = apiInterface.getIssues(userID, repoName);
        call.enqueue(new Callback<List<GetIssues>>() {
            @Override
            public void onResponse(Call<List<GetIssues>> call, Response<List<GetIssues>> response) {
                issuesList = response.body();
                Log.d("@@REsponsee: No res",issuesList.toArray().toString());

            }

            @Override
            public void onFailure(Call<List<GetIssues>> call, Throwable t) {
                Log.d("@@REsponsee: No res", t.getLocalizedMessage());

            }
        });

    }
}