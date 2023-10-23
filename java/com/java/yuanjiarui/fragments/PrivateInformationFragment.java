package com.java.yuanjiarui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.yuanjiarui.LocalPageActivity;
import com.java.yuanjiarui.PrivateInfoChangeActivity;
import com.java.yuanjiarui.R;
import com.java.yuanjiarui.StartActivity;
import com.java.yuanjiarui.tools.MyDatabaseHelper;

import java.util.Arrays;
import java.util.List;

public class PrivateInformationFragment extends Fragment {
    private TextView history;
    private TextView star;
    private TextView InfoChange;
    public ImageView imageView;
    public TextView sloganView;
    private  TextView setting;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.private_information,container,false);
        imageView=view.findViewById(R.id.avatar);
        sloganView=view.findViewById(R.id.show_slogan);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        history=view.findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), LocalPageActivity.class);
                intent.putExtra("from","history");
                startActivity(intent);
//                Toast toast =new Toast(getActivity());
//                toast.setText("sadsa");
//                toast.show();
            }
        });
        star =view.findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),LocalPageActivity.class);
                intent.putExtra("from","star");
                startActivity(intent);
            }
        });

        InfoChange=view.findViewById(R.id.private_info);
        InfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), PrivateInfoChangeActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("private_information", Context.MODE_PRIVATE);
        List<Drawable> drawables1= Arrays.asList(new Drawable[]{getActivity().getDrawable(R.drawable.chigua), getActivity().getDrawable(R.drawable.fafa), getActivity().getDrawable(R.drawable.heihei),
                getActivity().getDrawable(R.drawable.keyin), getActivity().getDrawable(R.drawable.laba), getActivity().getDrawable(R.drawable.lagong), getActivity().getDrawable(R.drawable.maomao),
                getActivity().getDrawable(R.drawable.xinxin), getActivity().getDrawable(R.drawable.yaoyu)});

        sloganView.setText(sharedPreferences.getString("slogan",""));
        imageView.setImageDrawable(drawables1.get(sharedPreferences.getInt("which_alysia",3)));
        setting=view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDatabaseHelper= new MyDatabaseHelper(getActivity(),"Mydatabase.db",null,1);
                myDatabaseHelper.clearTable("my_table_version2");
                Toast toast=Toast.makeText(getActivity(),"缓存清理完成",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent=new Intent("notify");
                getActivity().sendBroadcast(intent);
            }
        });
    }

}
/*
public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
 */