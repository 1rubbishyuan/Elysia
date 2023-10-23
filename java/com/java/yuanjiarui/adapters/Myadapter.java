package com.java.yuanjiarui.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.java.yuanjiarui.R;
import com.java.yuanjiarui.tools.DataBaseOperation;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyviewHolder> {
    private Context context;
    //还差一个自定义的信息类组成的Array
    public List<Bean> beans = new ArrayList<>();
    private OnRecycleItemClickedListener onRecycleItemClickedListener;

    public Myadapter(Context context, List<Bean> beans) {
        this.context = context;
        this.beans = beans;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.myadapter_item, null);
        //加载视图,传入一个item的视图
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        //给控件定义逻辑,并设置每一个item中填充提供的data_list中的哪一个位置的内容

        holder.dateView.setText(beans.get(position).publishTimeString);
        holder.publisherView.setText(beans.get(position).publisherString);

        holder.textView.setText(beans.get(position).titleString);
        holder.textView.setTextColor(context.getColor(R.color.black));
        holder.dateView.setTextColor(context.getColor(R.color.gray));
        holder.publisherView.setTextColor(context.getColor(R.color.gray));

        if(DataBaseOperation.query(context,beans.get(position).newsidString,"title")!=null&&!beans.get(position).fromLocal){
            holder.textView.setTextColor(context.getColor(R.color.read_gray));//换个颜色啊啊啊啊
            holder.dateView.setTextColor(context.getColor(R.color.read_gray));
            holder.publisherView.setTextColor(context.getColor(R.color.read_gray));
        }

        //holder.textView.setTextColor(Color.BLACK);
//        if (beans.get(position).images[0]!=""&&beans.get(position).images[0]!=" "){
//            Glide.with(holder.itemView).load(beans.get(position).images[0]).into(holder.imageView);
//
//        }//为什么会有位置错误
        // 设置默认占位图或清空之前的图片
        // 使用占位图，或者用 null 清空图片
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        Log.d("mstate",String.valueOf(isConnected));
        holder.imageView.setVisibility(View.GONE);
        holder.imageView.setImageDrawable(null);
        if ((beans.get(position).images.length > 0)&&isConnected) {
            if (!TextUtils.isEmpty(beans.get(position).images[0])&&beans.get(position).images[0].length()>10) {
                Log.d("lll",beans.get(position).titleString+" "+beans.get(position).images[0]);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.placeholder);
                Glide
                        .with(holder.itemView)
                        .load(beans.get(position)
                                .images[0])
                        .into(holder.imageView);
            } else {
                // 如果图片链接为空，可以设置一个默认的占位图或清空之前的图片
                holder.imageView.setVisibility(View.GONE);
                holder.imageView.setImageDrawable(null); // 使用占位图，或者用 null 清空图片
            }
        }
        Context context1= context.getApplicationContext();
        File root=context1.getFilesDir();
        File place =new File(root,beans.get(position).newsidString+"0.jpg");
        Bitmap bitmap= BitmapFactory.decodeFile(place.getAbsolutePath());
        if(bitmap!=null){
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(bitmap);
        }

        holder.videoView.setVisibility(View.GONE);
        if(beans.get(position).videoString!=null){
            if (beans.get(position).videoString.isEmpty()) {
                holder.videoView.setVisibility(View.GONE);
            } else {
                Uri uri = Uri.parse(beans.get(position).videoString);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
                holder.imageView.setImageURI(uri);
                holder.videoView.setVisibility(View.VISIBLE);
                holder.videoView.setVideoURI(uri);
            //holder.videoView.start();
            //可能接下来还要处理一下视频播放的逻辑
         }
        }


        Log.d("compare", beans.get(position).titleString + " " + beans.get(position).images[0]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //用于实现一个recycle_view的点击事件
            @Override
            public void onClick(View view) {
                if (onRecycleItemClickedListener != null) {
                    onRecycleItemClickedListener.onRecycleItemClick(holder.getAdapterPosition());
                }
            }
        });
//        holder.mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast= Toast.makeText(context, "123", Toast.LENGTH_LONG);
//                toast.show();
//            }
//        });
    }

    public void setArraylist(List<Bean> l) {
        this.beans = l;
    }

    @Override
    public int getItemCount() {
        //view中出现多少个控件
        Log.d("size", "" + beans.size());
        return beans.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        //捕获控件
        private TextView textView;
        private VideoView videoView;
        private ImageView imageView;
        private TextView publisherView;
        private TextView dateView;
        //private Button mButton;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_image);
            videoView = itemView.findViewById(R.id.item_video);
            dateView=itemView.findViewById(R.id.item_date);
            publisherView=itemView.findViewById(R.id.item_publisher);
            // mButton=itemView.findViewById(R.id.button);
        }
    }

    public interface OnRecycleItemClickedListener {
        void onRecycleItemClick(int position);
    }

    public void setOnRecycleItemCLickedListener(OnRecycleItemClickedListener onRecycleItemClickedListener) {
        this.onRecycleItemClickedListener = onRecycleItemClickedListener;
    }
}
