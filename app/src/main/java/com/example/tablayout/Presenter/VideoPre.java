package com.example.tablayout.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.tablayout.Model.DataVideo;
import com.example.tablayout.Model.getToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class VideoPre {
    private static final String TAG = "ketqua";
    Context context;

    public VideoPre(Context context) {
        this.context = context;
    }

    public ArrayList<DataVideo> getArrDataVideo(){
        ArrayList<DataVideo> arrDataVideo = new ArrayList<>();
        String data = null;
        try {
            data = new getToken().execute().get();
            Log.d(TAG, "getArrDataVideo: "+data);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArrayItem = jsonObject.getJSONArray("items");
            for(int i=0; i<jsonArrayItem.length();i++){
                JSONObject jsonObjectItem =jsonArrayItem.getJSONObject(i);
                DataVideo dataVideo = getDataVideo(jsonObjectItem);
                arrDataVideo.add(dataVideo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrDataVideo;
    }

    public DataVideo getDataVideo (JSONObject jsonObject){
        DataVideo dataVideo = new DataVideo();
        try {
            dataVideo.setTieuDe(jsonObject.getString("title"));
            dataVideo.setLinkAvatar(jsonObject.getString("avatar"));
            dataVideo.setUrlVideo(jsonObject.getString("file_mp4"));
            dataVideo.setDate(jsonObject.getString("date_created"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataVideo;
    }
}
