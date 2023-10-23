package com.java.yuanjiarui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.java.yuanjiarui.adapters.AlysiaAdapter;

import java.util.Arrays;
import java.util.List;

public class PrivateInfoChangeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_info_change);
        List<Drawable> drawables= Arrays.asList(new Drawable[]{getDrawable(R.drawable.chigua), getDrawable(R.drawable.fafa), getDrawable(R.drawable.heihei),
                getDrawable(R.drawable.keyin), getDrawable(R.drawable.laba), getDrawable(R.drawable.lagong), getDrawable(R.drawable.maomao),
                getDrawable(R.drawable.xinxin), getDrawable(R.drawable.yaoyu)});

        SharedPreferences sharedPreferences=getSharedPreferences("private_information", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        AlysiaAdapter alysiaAdapter=new AlysiaAdapter(PrivateInfoChangeActivity.this,drawables);
        alysiaAdapter.setOnItemClickedListener(new AlysiaAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                alysiaAdapter.selected_position=position;
                editor.putInt("which_alysia",position);
                editor.apply();
                alysiaAdapter.notifyDataSetChanged();
            }
        });

        recyclerView=findViewById(R.id.Alysias);
        recyclerView.setAdapter(alysiaAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(PrivateInfoChangeActivity.this,3));

        editText=findViewById(R.id.slogan);
        //editText.setText(sharedPreferences.getString("slogan",""));

        button=findViewById(R.id.sure_for_modify);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("change");
               // Log.d("dsa",editText.getText());
                if (editText.getText().toString()!=null&&!editText.getText().toString().equals("")){
                    editor.putString("slogan",editText.getText().toString());
                    editor.apply();
                intent.putExtra("slogan",editText.getText().toString());}
                else {
                    intent.putExtra("slogan","未进行原嘉锐预期中的修改");
                }
                intent.putExtra("which_alysia",sharedPreferences.getInt("which_alysia",3));
                sendBroadcast(intent);
                onBackPressed();
            }
        });

    }
}