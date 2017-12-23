package com.example.anchitchawla.news;

/**
 * Created by Anchit Chawla on 14-12-2017.
 */

public class News  {
    private String section;
    private String title;
    private String type;
    private String Url;
    News(String sec,String tit,String typ,String url)
    {
        this.section=sec;
        this.title=tit;
        this.type=typ;
        this.Url=url;
    }
    public String getSection(){
        return section;
    }
    public String getTitle(){
        return title;
    }
    public String getType(){
        return type;
    }
    public String getAnInt(){
        return Url;
    }
}
