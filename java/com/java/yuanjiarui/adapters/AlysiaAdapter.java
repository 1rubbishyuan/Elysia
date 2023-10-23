package com.java.yuanjiarui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yuanjiarui.R;

import java.util.List;

public class AlysiaAdapter extends RecyclerView.Adapter<AlysiaAdapter.MyViewHolder> {
    private  Context context;
    private  OnItemClickedListener onItemClickedListener;
    private  List<Drawable> drawables;
    public int selected_position=3;
    public AlysiaAdapter(Context context, List<Drawable> drawables){
        this.context=context;
        this.drawables=drawables;
        SharedPreferences sharedPreferences= context.getSharedPreferences("private_information",Context.MODE_PRIVATE);
        selected_position=sharedPreferences.getInt("which_alysia",3);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.alysia_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageDrawable(drawables.get(position));
        if (position==selected_position){
            holder.linearLayout.setBackgroundColor(context.getColor(R.color.round_button_for_category));
        }
        else{
            holder.linearLayout.setBackgroundColor(context.getColor(R.color.white));
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
        return drawables.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.alysia);
            linearLayout=itemView.findViewById(R.id.select_background);
        }
    }
    public  void  setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener=onItemClickedListener;
    }
    public interface OnItemClickedListener{
        public  void  onItemClicked(int position);
    }
}
