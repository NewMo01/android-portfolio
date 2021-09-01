package com.example.retrofit_mvvm.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofit_mvvm.data.CreateApi;
import com.example.retrofit_mvvm.pojo.PostsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MViewModel extends ViewModel {

    MutableLiveData<List<PostsModel>> mutableLiveData = new MutableLiveData<>() ;
    CreateApi api = new CreateApi();

    public void getPosts(){
        api.call.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                t.getMessage();
            }
        });

    }
}
