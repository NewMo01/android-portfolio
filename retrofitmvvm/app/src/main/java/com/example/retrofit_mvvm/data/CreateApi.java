package com.example.retrofit_mvvm.data;

import com.example.retrofit_mvvm.pojo.PostsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateApi {

    public Call<List<PostsModel>> call;
    public CreateApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostInterface postInterface = retrofit.create(PostInterface.class);
        call = postInterface.getCall();
    }


}
