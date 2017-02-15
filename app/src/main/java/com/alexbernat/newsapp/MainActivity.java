package com.alexbernat.newsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    final static String LINK = "http://content.guardianapis.com/search";
    final static int LOADER_NEWS_ID = 1;
    public NewsAdapter newsAdapter;
    public TextView emptyListTextView;
    public ListView mainListView;
    public ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        mainListView = (ListView) findViewById(R.id.news_list);
        emptyListTextView = (TextView) findViewById(R.id.news_list_empty_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_NEWS_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String keyWord = sharedPreferences.getString(
                getString(R.string.pref_query_key),
                getString(R.string.pref_query_default));
        Uri baseUri = Uri.parse(LINK);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("q", keyWord);
        uriBuilder.appendQueryParameter("api-key", "test");

        Loader<List<News>> loader = null;
        if (id == LOADER_NEWS_ID) {
            loader = new NewsLoader(this, uriBuilder.toString());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, final List<News> data) {
        progressBar.setVisibility(View.GONE);
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

    public static class NewsLoader extends AsyncTaskLoader<List<News>> {

        private String link;

        public NewsLoader(Context context, String link) {
            super(context);
            this.link = link;
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
                newsList = parser.readNewsFromInternet(link);
            } catch (IOException e) {
                newsList = null;
            }
            return newsList;
        }
    }
}
