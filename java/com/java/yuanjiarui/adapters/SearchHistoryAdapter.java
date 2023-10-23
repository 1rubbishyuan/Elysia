package com.java.yuanjiarui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yuanjiarui.R;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder> {
    private Context context;
    private SearchHistoryAdapter.OnItemClickedListener onItemClickedListener;
    public List<String> histories=new ArrayList<>();
    public SearchHistoryAdapter(Context context, List<String> histories){
        this.context=context;

        this.histories=histories;
    }
    @NonNull
    @Override
    public SearchHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.history_item,null);
        return new SearchHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(histories.get(histories.size()-1-position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.show_history);
        }
    }
    public  void  setOnItemClickedListener(SearchHistoryAdapter.OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener=onItemClickedListener;
    }
    public interface OnItemClickedListener{
        public  void  onItemClicked(int position);
    }
}
