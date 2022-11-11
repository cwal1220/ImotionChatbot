package com.example.imotionchatbot;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataItem {

    private String content;
    private String name;
    private int viewType;
    private String currentTime;

    public DataItem(String content, String name, int viewType){
        this.content = content;
        this.name = name;
        this.viewType = viewType;

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        this.currentTime = formatter.format(date);
    }

    public String getContent() { return this.content; }
    public String getName(){ return this.name; }
    public int getViewType() { return this.viewType; }
    public String getCurrentTime() { return this.currentTime;}
}
