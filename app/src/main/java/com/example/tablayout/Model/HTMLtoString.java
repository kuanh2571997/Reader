package com.example.tablayout.Model;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLtoString extends AsyncTask<String, Void, String> {
    private static final String TAG = "HTMLtoString";
    @Override
    protected String doInBackground(String... strings) {
        String s = "";
        try {
            Document doc = Jsoup.connect(strings[0]).get();
            Element topicList = doc.getElementsByClass("content_detail fck_detail width_common block_ads_connect").first();
            s = topicList.toString();

            Document document = Jsoup.parse(s);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground: lỗi html");
        }
        return s;
    }
}
