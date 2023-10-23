package com.java.yuanjiarui.tools;

import android.util.Log;

public class MyURL {
    public String size;
    public String keyword;
    public String category;
    public String endDate;
    public String startDate;
    public int page;
    public String Url;
    public MyURL(String size,String keyword,String  category,String  endDate,int page,String startDate){
        this.size=size;
        this.startDate=startDate;
        this.keyword=keyword;
        this.page=page;
        this.category=category;
        this.endDate=endDate;
        this.Url="https://api2.newsminer.net/svc/news/queryNewsList?size="+size+"&startDate="+startDate+"&endDate="+endDate+"&words="+keyword+"&categories="+category+"&page="+page;
    }
    //public getURL
}
