package com.example.imotionchatbot;

public class DataItem {

    private String content;
    private String name;
    private int viewType;

    public DataItem(String content, String name, int viewType){
        this.content = content;
        this.name = name;
        this.viewType = viewType;
    }

    public String getContent() { return this.content; }
    public String getName(){ return this.name; }
    public int getViewType() { return this.viewType; }
}
