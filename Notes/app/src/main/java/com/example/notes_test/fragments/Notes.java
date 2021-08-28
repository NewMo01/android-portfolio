package com.example.notes_test.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_test.NoteData;
import com.example.notes_test.R;
import com.example.notes_test.adapters.MyRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notes extends Fragment implements MyRecyclerAdapter.ClickItemInterface {


    Context context ;
    FloatingActionButton add_float_btn ;
    @SuppressLint("StaticFieldLeak")
    ArrayList<NoteData> arrayList ;
    @SuppressLint("StaticFieldLeak")
    public static MyRecyclerAdapter recyclerAdapter ;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context ;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes , container , false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ConnectivityManager cManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        // no connection
        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Toast.makeText(getActivity() , "Please make sure of wifi connection" , Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView = requireView().findViewById(R.id.RecyclerView);
        arrayList = new ArrayList<>();
        add_float_btn = requireView().findViewById(R.id.add_float_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerAdapter = new MyRecyclerAdapter(getContext() , arrayList , this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);


        // float button (add new note)
        add_float_btn.setOnClickListener(view -> startActivity(new Intent(context , NewNote.class)) );

    }




    @Override
    public void onStart() {
        super.onStart();

        // get data from firebase into arrayList
        NewNote.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot child : snapshot.child("notes").getChildren()){
                    arrayList.add(0 , child.getValue(NoteData.class));
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int pos) {

        if(arrayList.get(pos).getPass() != null){

            View v = getLayoutInflater().inflate(R.layout.dialog_pass_lay,null);
            EditText et_pass = v.findViewById(R.id.title_dialog_pass);

            AlertDialog dialog = new AlertDialog.Builder(this.getContext())
                    .setTitle("Enter the password : ")
                    .setView(v)
                    .setNegativeButton(R.string.Cancelation, null)
                    .setPositiveButton(R.string.Accept, null).create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if(et_pass.getText().toString().isEmpty() || !et_pass.getText().toString().trim().equals(arrayList.get(pos).getPass()))
                {
                    Toast.makeText(this.getContext() , "Please enter a valid password",Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    goEditNote(pos);
                }
            });

        }else{
            goEditNote(pos);
        }
    }

    @SuppressLint("ResourceType")
    private void goEditNote(int pos){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                ((Activity) context).findViewById(R.id.tv_title_ls), "titleTransition");
        ActivityCompat.startActivity(context ,
                new Intent(context , NewNote.class)
                        .putExtra("data_title",arrayList.get(pos).getTitle())
                        .putExtra("data_detail" , arrayList.get(pos).getDetail())
                        .putExtra("data_id" ,arrayList.get(pos).getId() )
                        .putExtra("data_pass" , arrayList.get(pos).getPass())
                        .putExtra("data_color", arrayList.get(pos).getColor())
                        .putExtra("check",false)
                , options.toBundle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getActivity().getWindow().setExitTransition(null);
        }

    }


}

