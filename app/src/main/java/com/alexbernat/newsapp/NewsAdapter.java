package com.alexbernat.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, List<News> newsList){
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        if (rootView == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.news_item, parent, false);
        }
        News currentNews = getItem(position);
        TextView sectionTextView = (TextView)rootView.findViewById(R.id.news_section);
        TextView titleTextView = (TextView)rootView.findViewById(R.id.news_title);
        TextView dateTextView = (TextView)rootView.findViewById(R.id.news_date);

        String formattedDate = formatDate(currentNews.getDate());

        sectionTextView.setText(currentNews.getSection());
        titleTextView.setText(currentNews.getTitle());
        dateTextView.setText(formattedDate);

        return rootView;
    }

    private String formatDate(String initialDate){
        String[] bufer = initialDate.split("T");
        SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = currentFormat.parse(bufer[0]);
        } catch (ParseException e){
            return bufer[0];
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        return newFormat.format(date);
    }
}
