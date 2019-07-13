package com.example.tablayout.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadRSS  {
    pass p;

    public LoadRSS(pass p) {
        this.p = p;
    }
    //    @Override
//    protected void doInBackground(String... strings) {
//        StringBuilder content = new StringBuilder();
//        try {
//            URL url = new URL(strings[0]);
//
//            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line = "";
//            while((line = bufferedReader.readLine())!=null){
//                content.append(line+"\n");
//            }
//
//            bufferedReader.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content.toString();

    public void getData(String link){

        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.LOW)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("hoanganhktd", "onResponse: chạy vào đây:  " +response);

                p.setData(response);
            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }

    public interface pass{
        public void setData(String s);
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
}
