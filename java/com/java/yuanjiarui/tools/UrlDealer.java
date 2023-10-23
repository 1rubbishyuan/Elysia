package com.java.yuanjiarui.tools;

import android.util.Log;

import com.google.gson.Gson;
import com.java.yuanjiarui.adapters.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UrlDealer {
    public static  MyURL create_URL(String size,String words,String categories,int page,String startDate,String endDate ){
        //String s="https://api2.newsminer.net/svc/news/queryNewsList?size="+size+"&startDate=2021-08-20&endDate="+endDate+"&words="+words+"&categories="+categories+"&page="+page;
        return  new MyURL(size,words,categories,endDate,page,startDate);
    }
    public static  String getJsonContent(String URL){
        String ans="";
        try {
            //建立网络连接
            HttpURLConnection httpURLConnection=null;
            java.net.URL url=new URL(URL);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            //进行流的读取

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String line=new String();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            String jsonContent=stringBuilder.toString();
            bufferedReader.close();
            // Log.d("jsoncontent",jsonContent);
            ans=jsonContent;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return ans;
    }
    public static List<Bean> initBeanArray(String URL){
        List<Bean> ans=new ArrayList<>();
//        try {
//            //建立网络连接
//            HttpURLConnection httpURLConnection=null;
//            java.net.URL url=new URL(URL);
//            httpURLConnection=(HttpURLConnection)url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setConnectTimeout(5000);
//            httpURLConnection.connect();
//
//            //进行流的读取
//
//            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//            StringBuilder stringBuilder=new StringBuilder();
//            String line=new String();
//            while ((line=bufferedReader.readLine())!=null){
//                stringBuilder.append(line);
//            }
//            String jsonContent=stringBuilder.toString();
//            bufferedReader.close();
//            // Log.d("jsoncontent",jsonContent);
//            ans=parseJson(jsonContent);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            Log.d("dsadsadsad","jile");
//        }
        ans=parseJson(getJsonContent(URL));
        return ans;
    }
    public static List<Bean> parseJson(String jsonContent){

        List<Bean> beans=new ArrayList<>();
        Gson gson=new Gson();
        Map map=gson.fromJson(jsonContent,Map.class);
        if(map!=null){
        int totalSize=((Double) map.get("total")).intValue();
        //Log.d("look",String.valueOf(totalSize));
        List<Map> datas= (List<Map>) map.get("data");
        for(Map data :datas){
            //  Log.d("category", (String) data.get("category"));
            String rr_images=(String) data.get("image");
            String r_publishTime=(String)data.get("publishTime");
            String r_video=(String) data.get("video");
            String r_title=(String) data.get("title");
            String r_content=(String) data.get("content");
            String r_url=(String) data.get("url");
            String r_newsID=(String) data.get("newsID");
            String r_publisher=(String) data.get("publisher");
            String r_category=(String) data.get("category");
            String[] images;
            if (rr_images.length()>3){
            String r_images=rr_images.substring(1,rr_images.length()-1);
            images= r_images.split(",");
            }
            else{
             images=new String[]{""};
            }
            beans.add(new Bean(images,r_publishTime,r_video,r_title,r_content,r_url,r_newsID,r_publisher,r_category));
        }
        }
        return beans;
    }
    public static int getTotalSize(String jsonCont){
        Gson gson=new Gson();
        Map map=gson.fromJson(jsonCont,Map.class);
        if(map!=null){
        int totalSize=((Double) map.get("total")).intValue();
        //Log.d("look",String.valueOf(totalSize));
        return totalSize;
        }
        else  return  0;
    }
    public static MyURL getMore(MyURL nowURL){
        int more=nowURL.page+1;
        MyURL ans=UrlDealer.create_URL(nowURL.size,nowURL.keyword,nowURL.category,more,nowURL.startDate,nowURL.endDate);
        return ans;
    }

}
