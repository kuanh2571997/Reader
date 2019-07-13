package com.example.tablayout.Model;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PareURL extends AsyncTask<String, Void, ArrayList> {
    private static final String TAG = "PareURL";

//    final String TAG = "ketqua";

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> arr = new ArrayList<>();
        String title = "";
        String htmlString="";
        try {
            Document doc = Jsoup.connect(strings[0]).get();
            htmlString = doc.toString();
            Element eee = doc.select("li.active").first();
            Element chuyenmuc = eee.getElementsByTag("a").first();
            //Log.d(TAG, "doInBackground: link:" + chuyenmuc.text());

            try {
                if(htmlString.contains("class=\"title_news_detail")) {
                    Element tit = doc.getElementsByClass("title_news_detail").first();
                    //Log.d(TAG, "doInBackground: " + tit.text());
                    arr.add(tit.text());
                }
                else if(htmlString.contains("class=\"title_detail")) {
                    Element tit = doc.getElementsByClass("title_detail").first();
                    //Log.d(TAG, "doInBackground: " + tit.text());
                    arr.add(tit.text());
                }
                else arr.add("Tiêu đề sai định dạng html");
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: không tìm thấy tiêu đề");
                //Log.d(TAG, e.getMessage());
            }

            try {
                if(htmlString.contains("class=\"description\"")) {
                    Element des = doc.getElementsByClass("description").first();
                    arr.add(des.text());
                }
                else if(htmlString.contains("class=\"lead_detail\"")) {
                    Element des = doc.getElementsByClass("lead_detail").first();
                    arr.add(des.text());
                    Log.d(TAG, "doInBackground: tìm thấy mô tả");
                }
                else arr.add("Mô tả sai định dạng html");
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: không tìm thấy mô tả");
            }

            try {
                if(htmlString.contains("class=\"time left")) {
                    Element time = doc.getElementsByClass("time left").first();
                    arr.add(chuyenmuc.text() + " || " + time.text());
                }
                else if(htmlString.contains("class=\"right timestamp")) {
                    Element time = doc.getElementsByClass("right timestamp").first();
                    arr.add(chuyenmuc.text() + " || " + time.text());
                }
                else arr.add("Thời gian sai định dạng html");
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: không tìm thời gian");
            }

            try {
                if(htmlString.contains("fck_detail")) {
                    Element topicList = doc.getElementsByClass("fck_detail").first();
                    Elements elements = topicList.children();
                    if(!elements.hasClass("Normal")){
                        arr.add("Nội dung không theo định dạng");
                    }
                    for (Element element : elements) {
                        if (element.is("p")) {
                            arr.add(element.text());
                        } else if (element.is("table")) {
                            Element element1 = element.getElementsByTag("img").first();
                            Element element2 = element.getElementsByTag("p").first();
                            //Log.d(TAG, "doInBackground: " + element2.text());
                            String s = element1.attr("src") + "img_hoanganhktd" + element2.text();
                            arr.add(s);
                            //int a = s.indexOf("img_hoanganhktd");
                            //Log.d(TAG, "doInBackground: "+a);
                            //String ss = s.substring(0,a);
                            //String sss = s.substring(a+15);
//                        Log.d(TAG, "doInBackground: "+ss);
//                        Log.d(TAG, "doInBackground: "+sss);
//                        Log.d(TAG, "doInBackground: "+s);
                        }
                    }
                }
                else{
                    arr.add("Nội dụng sai định dạng html");
                }
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: không tìm thấy nội dung");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "doInBackground: lỗi123");
        }
        return arr;
    }

}
