package com.alexbernat.newsapp;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class News {
    private String title, section, date;

    public News(String section, String title, String date){
        this.title = title;
        this.section = section;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }
}
