package com.example.myroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView ;
    MyAdapter adapter ;
    List<Post> list ;
    PostsDatabase postsDatabase;
    Button sendBtn , showBtn;
    EditText etTitle , etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.et_title);
        etBody = findViewById(R.id.et_body);
        sendBtn = findViewById(R.id.sendBtn);
        showBtn = findViewById(R.id.showBtn);
        recyclerView = findViewById(R.id.recyclerview);

        list = new ArrayList<>();
        postsDatabase = PostsDatabase.getInstance(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new MyAdapter(list);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendBtn:
                // set data
                postsDatabase.postsDao().insertPost(new Post(new User(3,"Mo"),etTitle.getText().toString(),etBody.getText().toString()))
                        .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
                break;
            case R.id.showBtn:
                // get data
                postsDatabase.postsDao().getPosts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<Post>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<Post> posts) {
                                list.clear();
                                list.addAll(posts) ;
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                break;

        }
    }
}