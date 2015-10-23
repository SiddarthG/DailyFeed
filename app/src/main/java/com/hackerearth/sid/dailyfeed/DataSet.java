package com.hackerearth.sid.dailyfeed;

import java.io.Serializable;

/**
 * Created by sid on 10/18/2015.
 */
public class DataSet implements Serializable{

    String data;
    String thumbnail;
    String category;
    String link;
    String content;
    String source;

    public DataSet(){
        //data="N/A";
    }
    public DataSet(String title,String url,String category,String link,String content,String source){
        data=title;
        thumbnail=url;
        this.category=category;
        this.link=link;
        this.content=content;
        this.source=source;
        
    }
    public String getCategory(){
        return category;
    }
}
