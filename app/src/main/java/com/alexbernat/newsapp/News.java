package com.alexbernat.newsapp;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class News {
    private String title, section, date, link;

    public News(String section, String title, String date, String link){
        this.title = title;
        this.section = section;
        this.date = date;
        this.link = link;
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

    public String getLink() {
        return link;
    }
}
