package com.example.retrofit_mvvm.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_mvvm.R;
import com.example.retrofit_mvvm.pojo.PostsModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MyAdapter adapter = new MyAdapter();
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MViewModel viewModel = ViewModelProviders.of(this).get(MViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getPosts();

        viewModel.mutableLiveData.observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                adapter.setList(postsModels);
            }
        });



    }
}