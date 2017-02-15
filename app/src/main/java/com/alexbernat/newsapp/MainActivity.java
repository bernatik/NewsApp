package com.alexbernat.newsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    final static String LINK = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    final static String LOG_TAG = "NewsApp";
    final static int LOADER_NEWS_ID = 1;
    public NewsAdapter newsAdapter;
    public TextView emptyListTextView;
    public ListView mainListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        mainListView = (ListView) findViewById(R.id.news_list);
        emptyListTextView = (TextView) findViewById(R.id.news_list_empty_text_view);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_NEWS_ID, null, this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Loader<List<News>> loader = null;
        if (id == LOADER_NEWS_ID){
            loader = new NewsLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, final List<News> data) {
        if (data == null) {
            emptyListTextView.setText(R.string.text_when_list_is_empty);
        }
        newsAdapter = new NewsAdapter(this, data);
        mainListView.setAdapter(newsAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = data.get(position);
                String link = currentNews.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

    public static class NewsLoader extends AsyncTaskLoader<List<News>>{

        public NewsLoader(Context context){
            super(context);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<News> loadInBackground() {
            NewsParser parser = new NewsParser();
            List<News> newsList;
            try {
                newsList = parser.readNewsFromInternet(LINK);
            } catch (IOException e) {
                newsList = null;
            }
            return newsList;
        }
    }
}
