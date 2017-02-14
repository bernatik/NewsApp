package com.alexbernat.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Bernat on 14.02.2017.
 */

public class NewsParser {
    final public static String FAKE_JSON = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":23155,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2316,\"orderBy\":\"relevance\",\"results\":" +
            "[{\"id\":\"us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-09-26T15:57:34Z\",\"webTitle\":\"The Nixon-Kennedy presidential debates: from the archive, 1960\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"isHosted\":false}," +
            "{\"id\":\"culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2017-02-06T07:00:20Z\",\"webTitle\":\"The next Doctor Who, a black Bond … the pop culture debates that never end\",\"webUrl\":\"https://www.theguardian.com/culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"apiUrl\":\"https://content.guardianapis.com/culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"isHosted\":false}," +
            "{\"id\":\"commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2016-09-23T21:43:23Z\",\"webTitle\":\"Get real-time reactions during the presidential debates | Guardian Mobile Innovation Lab\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"isHosted\":false}," +
            "{\"id\":\"us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-09-25T10:00:31Z\",\"webTitle\":\"Make or break: the defining moments of presidential debates\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"isHosted\":false}," +
            "{\"id\":\"australia-news/2017/jan/01/cabinet-papers-1992-93-victory-for-true-believers-kicks-off-lasting-debates\",\"type\":\"article\",\"sectionId\":\"australia-news\",\"sectionName\":\"Australia news\",\"webPublicationDate\":\"2016-12-31T13:01:28Z\",\"webTitle\":\"Cabinet papers 1992-93: victory for 'True Believers' kicks off lasting debates\",\"webUrl\":\"https://www.theguardian.com/australia-news/2017/jan/01/cabinet-papers-1992-93-victory-for-true-believers-kicks-off-lasting-debates\",\"apiUrl\":\"https://content.guardianapis.com/australia-news/2017/jan/01/cabinet-papers-1992-93-victory-for-true-believers-kicks-off-lasting-debates\",\"isHosted\":false}," +
            "{\"id\":\"us-news/2016/sep/02/presidential-debate-moderators-martha-raddatz-anderson-cooper\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-09-02T16:53:02Z\",\"webTitle\":\"The Clinton-Trump presidential debates: who are the moderators?\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/sep/02/presidential-debate-moderators-martha-raddatz-anderson-cooper\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/sep/02/presidential-debate-moderators-martha-raddatz-anderson-cooper\",\"isHosted\":false}," +
            "{\"id\":\"us-news/2016/oct/19/where-is-climate-change-in-the-trump-v-clinton-presidential-debates\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-10-19T12:00:07Z\",\"webTitle\":\"Why has climate change been ignored in the US election debates?\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/oct/19/where-is-climate-change-in-the-trump-v-clinton-presidential-debates\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/oct/19/where-is-climate-change-in-the-trump-v-clinton-presidential-debates\",\"isHosted\":false}," +
            "{\"id\":\"science/political-science/2016/oct/24/hinkley-c-shows-the-value-of-social-science-in-the-most-toxic-public-debates\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2016-10-24T06:30:07Z\",\"webTitle\":\"Hinkley C shows the value of social science in the most toxic public debates\",\"webUrl\":\"https://www.theguardian.com/science/political-science/2016/oct/24/hinkley-c-shows-the-value-of-social-science-in-the-most-toxic-public-debates\",\"apiUrl\":\"https://content.guardianapis.com/science/political-science/2016/oct/24/hinkley-c-shows-the-value-of-social-science-in-the-most-toxic-public-debates\",\"isHosted\":false}," +
            "{\"id\":\"us-news/2016/oct/22/clinton-trump-debate-analysis-foreign-policy-economy-scandals\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-10-22T11:00:15Z\",\"webTitle\":\"What did Clinton and Trump talk about in the debates – and for how long?\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/oct/22/clinton-trump-debate-analysis-foreign-policy-economy-scandals\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/oct/22/clinton-trump-debate-analysis-foreign-policy-economy-scandals\",\"isHosted\":false}," +
            "{\"id\":\"politics/2016/jun/02/eu-referendum-tv-debates-when-where-watch-them\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2016-06-09T12:53:46Z\",\"webTitle\":\"EU referendum debates: when and where to watch them\",\"webUrl\":\"https://www.theguardian.com/politics/2016/jun/02/eu-referendum-tv-debates-when-where-watch-them\",\"apiUrl\":\"https://content.guardianapis.com/politics/2016/jun/02/eu-referendum-tv-debates-when-where-watch-them\",\"isHosted\":false}]}}";

    public NewsParser(){

    }

    public List<News> fetchNewsDataFromJson(){
        List<News> newsList = new ArrayList<>();
        try{
            JSONObject jsonString = new JSONObject(FAKE_JSON);
            JSONObject response = jsonString.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i<results.length(); i++){
                JSONObject current = results.getJSONObject(i);
                String section = current.getString("sectionName");
                String title = current.getString("webTitle");
                String date = current.getString("webPublicationDate");
                News news = new News(section, title,date);
                newsList.add(news);
            }
        } catch (JSONException e){
            return null;
        }
        return newsList;
    }
}
