package com.java.yuanjiarui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.java.yuanjiarui.adapters.Bean;
import com.java.yuanjiarui.adapters.Myadapter;
import com.java.yuanjiarui.tools.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LocalPageActivity extends AppCompatActivity {
    private List<Bean> beans = new ArrayList<>();
    private RecyclerView recyclerView;
    private Myadapter myadapter;
    private ImageView imageButton;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                List<Bean> tmp = (List<Bean>) msg.obj;
                myadapter.setArraylist(tmp);
                myadapter.notifyDataSetChanged();
                myadapter.setOnRecycleItemCLickedListener(new Myadapter.OnRecycleItemClickedListener() {
                    @Override
                    public void onRecycleItemClick(int position) {
                        Intent intent = new Intent(LocalPageActivity.this, ReadActivity.class);
                        intent.putExtra("title", tmp.get(position).titleString);
                        intent.putExtra("content", tmp.get(position).contentString);
                        String[] tmp2 = tmp.get(position).publishData.split("      ");
//                        Log.d("look1",tmp2[0]);
//                        Log.d("look2",tmp2[1]);
                        intent.putExtra("date", tmp2[0].trim());
                        intent.putExtra("publisher", tmp2[1].trim());
                        intent.putExtra("newsId", tmp.get(position).newsidString);
                        intent.putExtra("images", new String[]{""});
                        intent.putExtra("video", "");
                        startActivity(intent);
                    }
                });


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //传进来一个是否是收藏页来的，加一个判断决定是否放入beans中去
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        boolean fromStar = (from.equals("star"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_page);
        recyclerView = findViewById(R.id.local_recyclerView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(LocalPageActivity.this, "Mydatabase.db", null, 1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String sortOrder = "readTime DESC";
                Cursor cursor = db.query("my_table_version2", new String[]{"newsId", "title", "publishData", "content", "stared", "images", "publisher", "publishDate", "category","readTime"}, null, null, null, null, sortOrder);
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getColumnIndex("newsId") >= 0) {
                            @SuppressLint("Range") String newsId = cursor.getString(cursor.getColumnIndex("newsId"));
                            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                            @SuppressLint("Range") String publishData = cursor.getString(cursor.getColumnIndex("publishData"));
                            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                            //Log.d("local_info",newsId+' '+title+" "+publishData);
                            @SuppressLint("Range") String star = cursor.getString(cursor.getColumnIndex("stared"));
                            @SuppressLint("Range") String images = cursor.getString(cursor.getColumnIndex("images"));
                            @SuppressLint("Range") String publisher = cursor.getString(cursor.getColumnIndex("publisher"));
                            @SuppressLint("Range") String publishTime = cursor.getString(cursor.getColumnIndex("publishDate"));
                            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                            @SuppressLint("Range") long readTime=cursor.getLong(cursor.getColumnIndex("readTime"));
                           // Log.d("tioashi", publisher);
                            if ((fromStar &&star!=null&&star.equals("yes")) || !fromStar) {
                                Bean bean = new Bean(newsId, title, content, publishData, images, publisher, publishTime, category);
                                bean.fromLocal = true;
                                beans.add(bean);
                            }
                        }
                    }
                    while (cursor.moveToNext());
                }
                Message message = new Message();
                message.what = 1;
                message.obj = beans;
                handler.sendMessage(message);
            }
        });
        thread.start();
        myadapter = new Myadapter(LocalPageActivity.this, this.beans);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LocalPageActivity.this));
        recyclerView.addItemDecoration(new LocalItemDecoration());

        imageButton=findViewById(R.id.back2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    class LocalItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 15;
            outRect.right=90;
            outRect.left=90;
        }
    }
}