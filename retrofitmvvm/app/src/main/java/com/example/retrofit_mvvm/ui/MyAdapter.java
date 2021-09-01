package com.example.retrofit_mvvm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_mvvm.R;
import com.example.retrofit_mvvm.pojo.PostsModel;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<PostsModel> list = new ArrayList<>();

    public void setList(List<PostsModel> l){
        list = l ;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.item  , parent,false);
        return new MyViewHolder(v);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId , tvTitle , tvBody;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvBody = itemView.findViewById(R.id.tv_body);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(list.get(position).getId() ) );
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvBody.setText(list.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
