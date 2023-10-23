package com.java.yuanjiarui.adapters;

import java.util.List;

public class Bean {
    public int record;
    public String[] images;
    public String publishTimeString;
    public String videoString;
    public String titleString;
    public String contentString;
    public String urlString;
    public String newsidString;
    public String publisherString;
    public String categoryString;
    public String publishData;
    public boolean fromLocal;
    public Bean(String [] images,String publishTimeString,String videoString,String titleString,String contentString
    ,String urlString,String  newsidString,String publisherString,String categoryString){
        fromLocal=false;
        this.record=0;
        this.images=images;
        this.publishTimeString=publishTimeString;
        this.videoString=videoString;
        this.titleString=titleString;
        this.contentString=contentString;
        this.urlString=urlString;
        this.newsidString=newsidString;
        this.publisherString=publisherString;
        this.categoryString=categoryString;
        this.publishData=publishTimeString+"      "+publisherString;
    }
    public Bean(String newsId,String titleString,String contentString ,String publishData,String images,String publisherString,String publishTimeString,String categoryString){
        //对路径进行反序列化，并赋值给images的数组
        this.publisherString=publisherString;
        this.publishTimeString=publishTimeString;
        this.categoryString=categoryString;
        this.images=new String[]{""};
        this.titleString=titleString;
        this.contentString=contentString;
        this.publishData=publishData;
        this.newsidString=newsId;
        fromLocal=false;
    }

}
