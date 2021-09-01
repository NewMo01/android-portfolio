package com.example.retrofit_mvvm.data;

import com.example.retrofit_mvvm.pojo.PostsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {

    @GET("posts")

    Call<List<PostsModel>> getCall();
}
