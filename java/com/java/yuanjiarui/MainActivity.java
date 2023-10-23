package com.java.yuanjiarui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.java.yuanjiarui.adapters.ViewPageAdapter;
import com.java.yuanjiarui.fragments.MainFragment;
import com.java.yuanjiarui.fragments.NewsPageFragement;
import com.java.yuanjiarui.fragments.PrivateInformationFragment;

public class MainActivity extends AppCompatActivity {
   // private Button button;

    private long backPressedTime=0;
    ViewPager2 viewPager2;
    ViewPageAdapter viewPageAdapter;
    private List<Fragment> fragments =new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState){
//    //button = (Button) findViewById(R.id.b1);
//        SharedPreferences sharedPreferences=getSharedPreferences("like_store", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_fragments();
        viewPager2=findViewById(R.id.news_view_page2);
        viewPageAdapter= new ViewPageAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(viewPageAdapter);
        TabLayout tabLayout=findViewById(R.id.main_tabview);
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.tablayout_selected));
       // tabLayout.setTabTextColors(getColor(R.color.black), getColor(R.color.tablayout_selected));

        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                // 监听页面滚动状态变化事件
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    // 如果检测到滑动操作，强制将页面切换回当前选中的页面
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem(), false);
                }
            }
        };

// 将自定义的OnPageChangeCallback设置给ViewPager2
       viewPager2.registerOnPageChangeCallback(onPageChangeCallback);
        Drawable[] drawables={getDrawable(R.drawable.homed),getDrawable(R.drawable.person) };
        for(int i=0;i<drawables.length;i++){
            TabLayout.Tab tab=tabLayout.newTab();
            tab.setIcon(drawables[i]);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    tab.setIcon(getDrawable(R.drawable.homed));
                }
                else  {
                    tab.setIcon(getDrawable(R.drawable.personed));
                }
                viewPager2.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    tab.setIcon(getDrawable(R.drawable.home));
                }
                else if (tab.getPosition()==3) {
                    tab.setIcon(getDrawable(R.drawable.person));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    //getColor(R.color.round_button_for_category);
                    tab.setIcon(getDrawable(R.drawable.homed));
                }
                else if (tab.getPosition()==3) {
                    tab.setIcon(getDrawable(R.drawable.personed));
                }
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // 当ViewPager2的页面被选中时，更新选项卡的选中状态
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("change");
        registerReceiver(receiver, filter);
       // fragment.imageView.setImageDrawable(drawables1.get(sharedPreferences.getInt("which_alysia",3)));


    }
    private void init_fragments(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        Network network = connectivityManager.getActiveNetwork();
//        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
//        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
//        Log.d("mstate",String.valueOf(isConnected));
      //  if(isConnected){
        fragments.add(new MainFragment());
        //}
//        else {
//        fragments.add(new PrivateInformationFragment());
//        }
        fragments.add(new PrivateInformationFragment());
    }

    @Override
    public void onBackPressed() {
        long nowTime=System.currentTimeMillis();
        if(nowTime-backPressedTime>1500){
            Toast toast =Toast.makeText(MainActivity.this,"再按一次退出应用",Toast.LENGTH_SHORT);
            toast.show();
            backPressedTime=nowTime;
        }
        else{
            super.onBackPressed();
        }
    }
//    public void infoChange(String slogan,Drawable drawable){
//        PrivateInformationFragment fragment = (PrivateInformationFragment) viewPageAdapter.getFragment(1);
//        fragment.imageView.setImageDrawable(drawable);
//        fragment.sloganView.setText(slogan);
//    }
    public class Receiver extends BroadcastReceiver{
    List<Drawable> drawables= Arrays.asList(new Drawable[]{getDrawable(R.drawable.chigua), getDrawable(R.drawable.fafa), getDrawable(R.drawable.heihei),
            getDrawable(R.drawable.keyin), getDrawable(R.drawable.laba), getDrawable(R.drawable.lagong), getDrawable(R.drawable.maomao),
            getDrawable(R.drawable.xinxin), getDrawable(R.drawable.yaoyu)});
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("change")){
           PrivateInformationFragment fragment = (PrivateInformationFragment) viewPageAdapter.getFragment(1);
           if(!intent.getStringExtra("slogan").equals("未进行原嘉锐预期中的修改")){
           fragment.sloganView.setText(intent.getStringExtra("slogan"));}
           fragment.imageView.setImageDrawable(drawables.get(intent.getIntExtra("which_alysia",3)));
          }
        }
    }
}
