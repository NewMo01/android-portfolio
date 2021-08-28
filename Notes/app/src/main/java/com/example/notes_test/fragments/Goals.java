package com.example.notes_test.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes_test.BannarAd;
import com.example.notes_test.R;
import com.google.android.gms.ads.AdView;

public class Goals extends Fragment {


    Button createBtn ; con listener ;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (con) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listener.config();
        super.onActivityCreated(savedInstanceState);

        createBtn = getView().findViewById(R.id.createButton);

        createBtn.setOnClickListener(this::onClickCreateGoal);

        // banner Ad
        AdView mAdView = getView().findViewById(R.id.adView);
        BannarAd.loadAd(this.getActivity());
        BannarAd.showAd(mAdView);

    }


    public void onClickCreateGoal(View v){
        Intent intent = new Intent(this.getActivity() , NewGoal.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this.getActivity(),createBtn,"go").toBundle();

            startActivity(intent , bundle );
        }else{
            startActivity(intent);
        }


    }

    public interface con{
        void config();
    }




}