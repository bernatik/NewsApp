package com.alexbernat.newsapp;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class News {
    private String mTitle, mSection, mDate;

    public News(String section, String title, String date){
        mTitle = title;
        mSection = section;
        mDate = date;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmTitle() {
        return mTitle;
    }
}
