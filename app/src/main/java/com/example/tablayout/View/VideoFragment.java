package com.example.tablayout.View;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tablayout.Model.DataVideo;
import com.example.tablayout.Model.LoadRSS;
import com.example.tablayout.Model.VideoAdapter;
import com.example.tablayout.Presenter.VideoPre;
import com.example.tablayout.R;
import com.example.tablayout.Presenter.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment implements LoadRSS.pass {
    private RecyclerView recyclerView;
    private String url ="";
    //private VideoPre videoPre;
    private ArrayList<DataVideo> arr = new ArrayList<>();
    private ProgressBar progressBar;
    private LoadRSS loadRSS;
    private VideoAdapter videoAdapter;
    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Bundle bundle = getArguments();
        url = bundle.getString("link");
        progressBar = view.findViewById(R.id.progressBar);
//        videoPre = new VideoPre(getContext());
//        arr = videoPre.getArrDataVideo();
        recyclerView = view.findViewById(R.id.recyclerView);
        videoAdapter = new VideoAdapter(getContext(),arr);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadRSS = new LoadRSS(this);
        loadRSS.getData("http://ocp-api-v2.gdcvn.com/v1/publishers/get-items?id=120&limit=0&offset=20&publisher_key=zw5yfhcygiruH81M");
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(), PlayVideo.class);
//                intent.putExtra("link", arr.get(position).getUrlVideo());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", arr);
//                intent.putExtra("bundle", bundle);
//                intent.putExtra("possition", position);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
//        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void setData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArrayItem = jsonObject.getJSONArray("items");
            for(int i=0; i<jsonArrayItem.length();i++){
                JSONObject jsonObjectItem =jsonArrayItem.getJSONObject(i);
                DataVideo dataVideo = getDataVideo(jsonObjectItem);
                arr.add(dataVideo);
                recyclerView.setAdapter(videoAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                videoAdapter.notifyDataSetChanged();
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), PlayVideo.class);
                        intent.putExtra("link", arr.get(position).getUrlVideo());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("array", arr);
                        intent.putExtra("bundle", bundle);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
                progressBar.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
