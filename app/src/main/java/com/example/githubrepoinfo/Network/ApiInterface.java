package com.example.githubrepoinfo.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("users/{userName}/repos")
    Call<List<GetRepo>> getRepos(@Path(value = "userName", encoded = true) String userName);


    @GET("/repos/{owner}/{repo}/issues")
    Call<List<GetIssues>> getIssues(@Path(value = "owner", encoded = true) String userID, @Path(value = "repo", encoded = true) String repoName
);

}
