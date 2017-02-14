package com.alexbernat.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        ListView mainListView = (ListView)findViewById(R.id.news_list);
        TextView emptyListTextView = (TextView)findViewById(R.id.news_list_empty_text_view);

        NewsParser parser = new NewsParser();

        List<News> newsList = parser.fetchNewsDataFromJson();
        if (newsList == null){
            emptyListTextView.setText(R.string.text_when_list_is_empty);
        } else {
            NewsAdapter adapter = new NewsAdapter(this, newsList);
            mainListView.setAdapter(adapter);
        }
    }
}
