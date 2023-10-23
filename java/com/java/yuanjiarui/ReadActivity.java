package com.java.yuanjiarui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.java.yuanjiarui.fragments.ReadContentFragment;
import com.java.yuanjiarui.tools.DataBaseOperation;
import com.java.yuanjiarui.tools.MyAnimation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadActivity extends AppCompatActivity {
    private ImageButton likeButton;
    private ImageButton starButton;
    private ImageView imageView;
    private ImageView leftFly;
    private ImageView rightFly;
    private ImageView midFly;
    //    private Handler handler = new Handler(Looper.myLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            Context context = getApplicationContext();
//            File file = new File(context.getFilesDir(), newsId + ".jpg");
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            ImageView imageView1 = findViewById(R.id.mtest);
//            imageView1.setImageBitmap(bitmap);
//        }
//    };
    String newsId;

    //设置滚动，设置标题，设置时间，设置图片，设置美观，设置评论区、点赞、收藏等等拓展功能。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();

        String content = intent.getStringExtra("content");
        String title = intent.getStringExtra("title");
        String publishData = intent.getStringExtra("date") + "      " + intent.getStringExtra("publisher");
        newsId = intent.getStringExtra("newsId");
        String[] images = intent.getStringArrayExtra("images");
        String video = intent.getStringExtra("video");
        leftFly=findViewById(R.id.left_fly);
        rightFly=findViewById(R.id.right_fly);
        midFly=findViewById(R.id.mid_fly);

        imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        Log.d("mstate",String.valueOf(isConnected));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();
                try {
                    for (int i = 0; i < images.length; i++) {
                       // Log.d("llll",images[i]);
                        URL url = new URL(images[i]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = connection.getInputStream();
                            File rootFile = context.getFilesDir();
                            File place = new File(rootFile, newsId +i+ ".jpg");
                            FileOutputStream outputStream = new FileOutputStream(place);

                            byte[] bytes = new byte[4096];
                            int readCount;
                            while ((readCount = inputStream.read(bytes)) != -1) {
                                outputStream.write(bytes, 0, readCount);
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//               Message message = new Message();
//                message.obj = true;
//                handler.sendMessage(message);
            }
        });
        if ((images.length > 0 && !images[0].isEmpty())&&isConnected&&images[0].length()>10) {
            thread.start();
        }


        //Log.d("imagelll",images[0]);
        //Log.d("images",images);
//        Thread thread= new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("image[0]",images[0]);
//                downLoad.downloadImage("https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0901%2F458a9686j00s0b6n80015c000hs00f7g.jpg&thumbnail=660x2147483647&quality=80&type=jpg","test");
//            }
//        });
//       // if (images.length>0){
//            thread.start();
//      //  }
//        imageView=findViewById(R.id.test);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast =new Toast(ReadActivity.this);
//                toast.setText("lllll");
//                toast.show();
//                String end;
////                if(images[0].endsWith(".jpg")){
////                    end=".jpg";
////                }
////                else {
////                    end=".png";
////                }
//               // String imagePath ="D:" + "/test"+end;
//                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test"+".png";
//                Log.d("download",Environment.DIRECTORY_PICTURES+ "/test"+".jpg");
//                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//               // Log.d("download",bitmap.toString());
//                if (bitmap==null){
//                    Log.d("download","lose");
//                }
//                imageView.setImageBitmap(bitmap);
//            }
//        });
        //如果不在我们就insert
        // 访问了空指针，需要调试

        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("title", title);
        bundle.putString("publishData", publishData);
        bundle.putStringArray("images", images);
        bundle.putString("video", video);
        bundle.putString("newsId",newsId);
        ReadContentFragment readContentFragment = new ReadContentFragment();
        readContentFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.read_content_layout, readContentFragment);
        fragmentTransaction.commitAllowingStateLoss();

        setLikeEvent();
        setStarEvent();
    }

    public void setLikeEvent() {
        Drawable drawable = getDrawable(R.drawable.liked);
        likeButton = findViewById(R.id.like_button);
//        SharedPreferences sharedPreferences=getSharedPreferences("like_store", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
        // boolean liked=sharedPreferences.getBoolean("liked",false);//后者是没有对应Key时返回的默认值
        String liked = "";
        liked = DataBaseOperation.query(ReadActivity.this, newsId, "liked");
        if (liked != null) {
            if (liked.equals("yes")) {
                likeButton.setImageDrawable(drawable);
            }
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // boolean liked=sharedPreferences.getBoolean("liked",false);
                String liked = "";
                liked = DataBaseOperation.query(ReadActivity.this, newsId, "liked");
                if (liked == null || liked.equals("")) {
                    // Toast toast=new Toast(ReadActivity.this);
                    //   toast.setText("点赞成功");
                    // toast.show();
                    MyAnimation.leftWingFlap(leftFly);
                    likeButton.setImageDrawable(drawable);
                    DataBaseOperation.update(ReadActivity.this, newsId, new String[]{"liked"}, new String[]{"yes"});
//                    ContentValues values=new ContentValues();
//                    values.put("newsId",newsId);
//                    values.put("liked",1);
//                    long rowId=db.insert("my_table",null,values);
                    //editor.putBoolean("liked",true);
                    // editor.apply();
                   // MyAnimation.upAndDisappear(likeButton);
                } else {
                    //   Toast toast=new Toast(ReadActivity.this);
                    //   toast.setText("取消点赞");
                    //  toast.show();
//                    ContentValues contentValues=new ContentValues();
//                    contentValues.put("liked",0);
                    DataBaseOperation.update(ReadActivity.this, newsId, new String[]{"liked"}, new String[]{""});
                    likeButton.setImageDrawable(getDrawable(R.drawable.like));
                }
            }
        });
    }

    public void setStarEvent() {
        Drawable drawable = getDrawable(R.drawable.stared);
        starButton = findViewById(R.id.star_button);
        String stared = "";
        stared = DataBaseOperation.query(ReadActivity.this, newsId, "stared");
        if (stared != null) {
            if (stared.equals("yes")) {
                starButton.setImageDrawable(drawable);
            }
        }
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stared = "";
                stared = DataBaseOperation.query(ReadActivity.this, newsId, "stared");
                if (stared == null || stared.equals("")) {
                    MyAnimation.rightWingFlap(rightFly);
                    starButton.setImageDrawable(drawable);
                    DataBaseOperation.update(ReadActivity.this, newsId, new String[]{"stared"}, new String[]{"yes"});
                } else {
                    starButton.setImageDrawable(getDrawable(R.drawable.star));
                    DataBaseOperation.update(ReadActivity.this, newsId, new String[]{"stared"}, new String[]{""});
                }
            }
        });

    }
}