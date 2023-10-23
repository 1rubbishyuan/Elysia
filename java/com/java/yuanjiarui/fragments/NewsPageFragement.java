package com.java.yuanjiarui.fragments;

import static com.java.yuanjiarui.tools.UrlDealer.create_URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.java.yuanjiarui.MainActivity;
import com.java.yuanjiarui.adapters.Bean;
import com.java.yuanjiarui.adapters.CategoryAdapter;
import com.java.yuanjiarui.adapters.CategoryInfo;
import com.java.yuanjiarui.adapters.Myadapter;
import  com.java.yuanjiarui.R;
import com.java.yuanjiarui.ReadActivity;
import com.java.yuanjiarui.tools.DataBaseOperation;
import com.java.yuanjiarui.tools.MyHeader;
import com.java.yuanjiarui.tools.MyURL;
import com.java.yuanjiarui.tools.UrlDealer;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.FalsifyHeader;
import com.scwang.smart.refresh.header.TwoLevelHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

public class NewsPageFragement extends Fragment {
    private List<Bean> beans=new ArrayList<>();
    private RecyclerView recyclerView;
    private Myadapter myadapter;
    public MyURL  initURL;
    private TextView textView;
    private ImageView imageView;
    private int totalSize;
    private  int recordSize=8;
    private int const_size=8;
   // private BottomSheetDialog bottomSheetDialog;
   // private Button unfoldCategory;
    //private Toolbar toolbar;
    //private int now_page=1;
    private SmartRefreshLayout smartRefreshLayout;
    private Handler handler =new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<Bean> tmp=(List<Bean>)msg.obj;
            myadapter.setArraylist(tmp);
            myadapter.notifyDataSetChanged();
            if (myadapter.beans.size()==0){
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
            myadapter.setOnRecycleItemCLickedListener(new Myadapter.OnRecycleItemClickedListener() {
                @Override
                public void onRecycleItemClick(int position) {
                    //TODO 实现对应位置的跳转。
                    Intent intent= new Intent(getActivity(),ReadActivity.class);
                    intent.putExtra("content",tmp.get(position).contentString);
                    intent.putExtra("title",tmp.get(position).titleString);
                    intent.putExtra("date",tmp.get(position).publishTimeString);
                    intent.putExtra("publisher",tmp.get(position).publisherString);
                    intent.putExtra("newsId",tmp.get(position).newsidString);
//                    StringBuilder stringBuilder=new StringBuilder();
//                    for(int i=0;i<tmp.get(position).images.length;i++){
//                        stringBuilder.append(tmp.get(position).images[i]+"+");
//                    }
//                    String images=stringBuilder.toString();
                    intent.putExtra("images",tmp.get(position).images);
                    intent.putExtra("video",tmp.get(position).videoString);
                    if(DataBaseOperation.query(getActivity(),tmp.get(position).newsidString,"title")==null) {
                        DataBaseOperation.insert(getActivity()
                                , tmp.get(position).newsidString
                                , tmp.get(position).titleString
                                , tmp.get(position).contentString
                                , tmp.get(position).publishData
                                ,""
                                ,tmp.get(position).publisherString
                                ,tmp.get(position).publishTimeString
                                ,tmp.get(position).categoryString);
                    }
                    else {
                        DataBaseOperation.updateReadTime(getActivity(),tmp.get(position).newsidString);
                    }
                    myadapter.notifyDataSetChanged();
                    startActivity(intent);
                }
            });
        }
    };
    public NewsPageFragement(MyURL URL){
       this.initURL=URL;
       Log.d("url",initURL.Url);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //线程
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonContent=UrlDealer.getJsonContent(initURL.Url);
                totalSize= UrlDealer.getTotalSize(jsonContent);
                beans= UrlDealer.parseJson(jsonContent);
                Message message=new Message();
                message.what=0;
                message.obj=beans;
                handler.sendMessage(message);
            }
        });
        thread.start();

       //视图加载
        View view = inflater.inflate(R.layout.fragment_news_page, container, false);
        textView=view.findViewById(R.id.no_result1);
        textView.setVisibility(View.GONE);
        imageView=view.findViewById(R.id.no_result_image);
        imageView.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        myadapter = new Myadapter(getActivity(),this.beans);
        recyclerView.addItemDecoration(new MyRecycleItemDecoration());
        recyclerView.setAdapter(myadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        MyReceiver myReceiver =new MyReceiver();
        IntentFilter filter = new IntentFilter("notify");
        getActivity().registerReceiver(myReceiver, filter);
        return view;
    }
    //fragment向activity传递信息的推荐方式：首先在fragment中定义一个接口，然后activity会继承这个接口并实现其成员函数，在fragment中实例化一个接口，在on_attach时
    //将activity中实现的接口赋值给fragment中实例化的接口，可以直接使用(cast)context的形式完成，然后在fragment中需要向activity中传递这个信息时，调用实例化的接口
    //中对应的成员函数即可。优势在于解耦合
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //刷新的实现
        smartRefreshLayout=(SmartRefreshLayout) getView().findViewById(R.id.smart_refresh);
        //smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        smartRefreshLayout.setRefreshHeader(new MyHeader(getActivity()));
        BallPulseFooter ballPulseFooter= new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale);
        ballPulseFooter.setNormalColor(getActivity().getColor(R.color.footer_color));
        ballPulseFooter.setAnimatingColor(getActivity().getColor(R.color.footer_color));
        smartRefreshLayout.setRefreshFooter(ballPulseFooter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishRefresh(1000/*,false*/);
                //now_page++;

                if(recordSize<totalSize){
                int record=beans.size();
                Log.d("record",""+record);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //int new_size=record+20;
                        recordSize+=8;
                        initURL=UrlDealer.getMore(initURL);
                        beans=UrlDealer.initBeanArray(initURL.Url);
                      //  Log.d("new_size",""+new_size );
                       // beans=beans.subList(record-1,beans.size());
                       // Log.d("beans",beans.toString());
                        Message message=new Message();
                        message.obj=beans;
                        handler.sendMessage(message);
                    }
                }).start();
              }
                else{
                    Log.d("noMore","noMore");
                    Toast toast=Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMore(1000/*,false*/);
               // int record=beans.size();
                if(recordSize<totalSize){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recordSize+=8;
                        //int new_size=record+15;
                        //beans=UrlDealer.initBeanArray(create_URL(""+const_size,"","",""+now_page));
                        //now_page++;
                        initURL=UrlDealer.getMore(initURL);
                        beans.addAll(UrlDealer.initBeanArray(initURL.Url));
                        Message message=new Message();
                        message.obj=beans;
                        handler.sendMessage(message);
                    }
                }).start();
                }
                else{
                    Toast toast=Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class MyRecycleItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 20;
            outRect.left=90;
            outRect.right=90;
        }
    }
//    public String create_URL(String size,String words,String categories,String page ){
//        Calendar calendar=Calendar.getInstance();
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time=format.format(calendar.getTime());
//        time=time.substring(0,10);
//        String s="https://api2.newsminer.net/svc/news/queryNewsList?size="+size+"&startDate=2023-08-20&endDate="+time+"&words="+words+"&categories="+categories+"&page="+page;
//        return  s;
//    }
//    class CategoryItemDecoration extends RecyclerView.ItemDecoration{
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.bottom=100;
//            outRect.left=100;
//        }
//    }
    public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
          myadapter.notifyDataSetChanged();
        }
    }
}
