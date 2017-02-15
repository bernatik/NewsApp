package com.alexbernat.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class NewsParser {


    public NewsParser() {

    }

    public List<News> readNewsFromInternet(String link) throws IOException {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<News> newsList;
        URL url = createUrl(link);
        String json = recieveJson(url);
        newsList = parseJson(json);
        return newsList;
    }

    public List<News> parseJson(String stringJson) {
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject jsonString = new JSONObject(stringJson);
            JSONObject response = jsonString.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject current = results.getJSONObject(i);
                String section = current.getString("sectionName");
                String title = current.getString("webTitle");
                String date = current.getString("webPublicationDate");
                String link = current.getString("webUrl");
                News news = new News(section, title, date, link);
                newsList.add(news);
            }
        } catch (JSONException e) {
            return null;
        }
        return newsList;
    }

    public URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }

    public String recieveJson(URL url) throws IOException {
        String recievedJson = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(2000);
            urlConnection.setReadTimeout(2000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            recievedJson = convertStreamIntoString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }

        return recievedJson;
    }

    public String convertStreamIntoString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder out = new StringBuilder();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            out.append(inputLine);
        }
        bufferedReader.close();
        return out.toString();
    }
}
