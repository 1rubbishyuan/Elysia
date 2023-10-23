package com.java.yuanjiarui.adapters;

public class CategoryInfo {
    public String category;
    public boolean isClicked;
    public boolean shaking;
    public CategoryInfo(String category,boolean isClicked)
    {
        this.category=category;
        this.isClicked=isClicked;
        this.shaking=false;
    }
    public void setIsClicked(boolean b){
        this.isClicked=b;
    }
}
