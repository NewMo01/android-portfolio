package com.example.rxjava;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.databinding.ActivityMainBinding;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                binding.myText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(charSequence.length() != 0)
                            emitter.onNext(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        }).doOnNext(x->Log.e("A","Up Stream: "+x))
            .map(new Function<Object, Object>() {
                @Override
                public Object apply(Object o) throws Throwable {
                    return Integer.parseInt(o.toString())+2;
                }
            })
            .subscribe(y->Log.e("A" , "Down Stream: "+y));
    }


    private void sleepThread(long x){
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}