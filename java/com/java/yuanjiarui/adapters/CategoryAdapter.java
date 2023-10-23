package com.java.yuanjiarui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yuanjiarui.R;
import com.java.yuanjiarui.tools.MyAnimation;
import com.java.yuanjiarui.tools.UrlDealer;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private OnItemClickedListener onItemClickedListener;
    public List<CategoryInfo> categoryInfoList;

    private Context context;
    private Drawable[] drawables;
    public CategoryAdapter(Context context,List<CategoryInfo> categoryInfoList){
        this.context=context;
        this.categoryInfoList=categoryInfoList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.category_item,null);
        drawables= new Drawable[]{context.getDrawable(R.drawable.entertainment),context.getDrawable(R.drawable.military),context.getDrawable(R.drawable.education)
                ,context.getDrawable(R.drawable.culture),context.getDrawable(R.drawable.health),context.getDrawable(R.drawable.finance),context.getDrawable(R.drawable.sports),
                 context.getDrawable(R.drawable.car),context.getDrawable(R.drawable.technology),context.getDrawable(R.drawable.social)};


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // Log.d("theurl", UrlDealer.create_URL("15","",categoryInfoList/,1).Url);
        if (categoryInfoList.get(position).shaking){
            MyAnimation.viewShake(holder.textView);
        }
        else {
            holder.itemView.clearAnimation();
        }
        holder.textView.setText(categoryInfoList.get(position).category);
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(drawables[position],null,null,null);
        if(categoryInfoList.get(position).isClicked){
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.textView.setBackgroundColor(ContextCompat.getColor(context,R.color.round_button_for_category));
        }
        else{
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.round_button_for_category));
            holder.textView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.onItemClicked(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryInfoList.size();
    }
    public  class  MyViewHolder extends RecyclerView.ViewHolder{
       private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.category_textview);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
    public void  setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener=onItemClickedListener;
    }
    public void setCategoryInfoList(List<CategoryInfo> categoryInfoList){
        this.categoryInfoList=categoryInfoList;
    }
}
