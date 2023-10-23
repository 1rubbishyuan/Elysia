package com.java.yuanjiarui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.yuanjiarui.fragments.NewsPageFragement;
import com.java.yuanjiarui.tools.MyURL;
import com.java.yuanjiarui.tools.UrlDealer;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ImageView imageButton;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        linearLayout=findViewById(R.id.search_result_linearLayout);
        //textView=findViewById(R.id.no_result);
        Intent intent=getIntent();
        String keyword=intent.getStringExtra("keyword");
        String category=intent.getStringExtra("category");
        String startDate=intent.getStringExtra("startDate");
        String endDate=intent.getStringExtra("endDate");
        MyURL url= UrlDealer.create_URL("8",keyword,category,1,startDate,endDate);
        getSupportFragmentManager().beginTransaction().add(R.id.search_result_linearLayout, new NewsPageFragement(url)).commitAllowingStateLoss();
        imageButton=findViewById(R.id.back1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }
}