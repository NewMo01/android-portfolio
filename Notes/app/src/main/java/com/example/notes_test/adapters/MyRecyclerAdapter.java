 package com.example.notes_test.adapters;

 import android.content.Context;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.cardview.widget.CardView;
 import androidx.recyclerview.widget.RecyclerView;

 import com.example.notes_test.NoteData;
 import com.example.notes_test.R;
 import com.example.notes_test.RewardAd;
 import com.example.notes_test.fragments.NewNote;

 import java.util.ArrayList;
 import java.util.List;

 public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MViewHolder> {

    List<NoteData> list ;
    Context context ;
    ClickItemInterface listener ;

    public MyRecyclerAdapter(Context context , ArrayList<NoteData> list  , ClickItemInterface listener){
        this.context = context ;
        this.list = list ;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_view , parent , false);
        return new MViewHolder(v);

    }


    public static class MViewHolder extends RecyclerView.ViewHolder {

        TextView title , time  ;  ImageView icon ;
        CardView cardView;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.tv_title_ls);
            time = itemView.findViewById(R.id.lv_time);
            icon = itemView.findViewById(R.id.deleteIcon);

        }
    }
    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime());
        if(list.get(position).getPass() != null) {
            holder.icon.setClickable(false);
            holder.icon.setImageResource(R.drawable.ic_lock);
        }
        else{
            holder.icon.setImageResource(R.drawable.ic_delete);
            holder.icon.setOnClickListener(view -> {
                RewardAd.counter++;
                NewNote.myRef.child("notes").child(list.get(position).getId()).removeValue();
            });
        }

        holder.cardView.setBackgroundColor(list.get(position).getColor());
        holder.cardView.setOnClickListener(view -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickItemInterface{
        void onItemClick(int pos);
    }
}
