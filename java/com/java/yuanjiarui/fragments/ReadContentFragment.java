package com.java.yuanjiarui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.java.yuanjiarui.R;

import java.io.File;
import java.net.URI;

public class ReadContentFragment extends Fragment {
    private TextView textView;
    private TextView titleView;
    private TextView publishDataView;
    private LinearLayout linearLayout;
    private VideoView videoView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_content, container, false);
        String content = getArguments().getString("content");
        textView = view.findViewById(R.id.read_textview);
        textView.setText(content);


        String title = getArguments().getString("title");
        titleView = view.findViewById(R.id.title_textview);
        titleView.setText(title);

        String publishData = getArguments().getString("publishData");
        publishDataView = view.findViewById(R.id.time_textview);
        publishDataView.setText(publishData);

        String[] images=getArguments().getStringArray("images");
        linearLayout=view.findViewById(R.id.layout_for_image);
        for(int i=0;i<images.length;i++){
            if (!images[i].isEmpty()){
                ImageView imageView=new ImageView(getActivity());
                Glide.with(view).load(images[i]).into(imageView);
                linearLayout.addView(imageView);
            }
        }

        String newsId=getArguments().getString("newsId");

        if(images.length==0||images[0].isEmpty()){
            int record=0;
            Context context=getActivity().getApplicationContext();
            File root=context.getFilesDir();
          //  File place =new File(root,)
            while (true){
                File place =new File(root,newsId+record+".jpg");
                Bitmap bitmap= BitmapFactory.decodeFile(place.getAbsolutePath());
                record+=1;
                if(bitmap!=null){
                    ImageView imageView=new ImageView(getActivity());
                    imageView.setImageBitmap(bitmap);
                    linearLayout.addView(imageView);
                }else {
                    break;
                }
            }
        }
        String video=getArguments().getString("video");
        videoView=view.findViewById(R.id.read_video);
        videoView.setVisibility(View.GONE);
        if(video!=null) {
            if (video.isEmpty()) {
                videoView.setVisibility(View.GONE);
            } else {
                videoView.setVisibility(View.VISIBLE);
                Log.d("start", video);
                Uri uri = Uri.parse(video);
                videoView.setVideoURI(uri);
                MediaController mediaController=new MediaController(getActivity());
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.start();
            }
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
