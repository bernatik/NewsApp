package com.alexbernat.newsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        Log.v(LOG_TAG, "inside onCreate. let's see if i am here");


    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Loader<List<News>> loader = null;
        if (id == LOADER_NEWS_ID){
            loader = new NewsLoader(this);
        }
        Log.v(LOG_TAG, "inside onCreateLoader. let's see id: " + id);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if (data == null) {
            emptyListTextView.setText(R.string.text_when_list_is_empty);
        }
        Log.v(LOG_TAG, "inside onLoadFinished. let's see data: " + data);
        newsAdapter = new NewsAdapter(this, data);
        mainListView.setAdapter(newsAdapter);

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
            Log.v(LOG_TAG, "inside loadInBackground. let's see if i am here");
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
