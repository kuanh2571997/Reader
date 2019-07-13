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
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.tablayout.Model.Data;
import com.example.tablayout.Model.Database;
import com.example.tablayout.Model.LoadRSS;
import com.example.tablayout.Model.XMLDOMParser;
import com.example.tablayout.Presenter.RecyclerViewAdapter2;
import com.example.tablayout.R;
import com.example.tablayout.Presenter.RecyclerItemClickListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class OneFragment extends Fragment implements LoadRSS.pass {
    final String TAG = "ketqua";
    RecyclerView recyclerView;
    RecyclerViewAdapter2 recyclerViewAdapter2;
    ProgressBar progressBar;
    String s = "";
    ArrayList<Data> arr = new ArrayList<>();
    int nodeListCounter = 0;
    LoadRSS loadRSS;
    RelativeLayout screen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("arr",arr);
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState!=null){
//            arr = (ArrayList<Data>) savedInstanceState.getSerializable("arr");
//            recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), arr);
//            recyclerView.setAdapter(recyclerViewAdapter2);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        Bundle bundle = getArguments();
        s = bundle.getString("link");
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), arr);
        recyclerView.setAdapter(recyclerViewAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);
        screen = view.findViewById(R.id.screen);
        String content = "";
        loadRSS = new LoadRSS(this);
        loadRSS.getData(s);
//        try {
//            content = new LoadRSS().execute(s).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        if(content!=""){
//            XMLDOMParser parser = new XMLDOMParser();
//            Document document = parser.getDocument(content);
//
//            NodeList nodeList = document.getElementsByTagName("item");
//            NodeList nodeListDescription = document.getElementsByTagName("description");
//
//            String description="";
//            nodeListCounter = nodeList.getLength();
//            //Log.d(TAG, "onCreateView: "+nodeList.getLength());
//
//            for(int i=0; i<nodeListCounter; i++) {
//                Element e = (Element) nodeList.item(i);
//                Data data = new Data();
//                data.setTieude(parser.getValue(e, "title"));
//                //Log.d(TAG, "onCreateView: "+parser.getValue(e, "title"));
//
//                data.setThoigian(parser.getValue(e, "pubDate"));
//                //Log.d(TAG, "onCreateView: "+parser.getValue(e, "pubDate"));
//
//                data.setLink(parser.getValue(e,"link"));
//                //Log.d(TAG, "onCreateView: "+parser.getValue(e,"link"));
//
//                description = nodeListDescription.item(i+1).getTextContent() + "";
//                data.setLinkImg(getImageLinkFromCDATA(description));
//                //Log.d(TAG, "onCreateView: "+getImageLinkFromCDATA(description));
//
//                data.setDes(getDescriptionFromCDATA(description));
//                //Log.d(TAG, "onCreateView: "+getDescriptionFromCDATA(description));
//                arr.add(data);
//            }
//
//            recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), arr);
//            recyclerView.setAdapter(recyclerViewAdapter2);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    Data data = arr.get(position);
//                    if(data.getLink().contains("video.vnexpress.net")){
//                        Toast.makeText(getContext(), "Đây là link video,chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(data.getLink().contains("ttps://e.vnexpress.net")){
//                        Toast.makeText(getContext(), "bài viết tiếng anh có định dạng khác, chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Intent intent = new Intent(getContext(),ChiTiet.class);
//                        String link = data.getLink();
//                        String linkimg = data.getLinkImg();
//                        String date = data.getThoigian();
//                        Database database = new Database(getContext(), "Title.sqlite", null, 1);
//                        database.QueryData("CREATE TABLE IF NOT EXISTS tblHistory(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(1000), Link VARCHAR(1000), LinkImg VARCHAR(1000), Date VARCHAR(200))");
//                        database.InsertHis(data.getTieude(),link, linkimg, date);
//                        intent.putExtra("link",data.getLink());
//                        //database.QueryData("drop table tblHistory");
//                        startActivity(intent);
//                    }
//                }
//
//                @Override
//                public void onLongItemClick(View view, int position) {
//
//                }
//            }));
//        }
        return view;
    }

    private String getImageLinkFromCDATA(String description){
        int a = 0;
        if(description.contains("data-original")){
            a=description.indexOf("data-original")+15;
        }
        else a = description.indexOf("src")+5;

        int b = 0;
        if(description.contains(".png")){
            b = description.indexOf(".png");
        }
        else if(description.contains(".jpg")){
            b = description.indexOf(".jpg");
        }
        else if(description.contains(".gif")){
            b = description.indexOf(".gif");
        }
        else if(description.contains(".jpeg")){
            b = description.indexOf(".jpeg")+1;
        }

        return description.substring(a,b+4);
    }

    private String getDescriptionFromCDATA(String description){
        int a = description.indexOf("</a></br>");
        return description.substring(a+9);
    }

    @Override
    public void setData(String s) {

        XMLDOMParser parser = new XMLDOMParser();
        Document document = parser.getDocument(s);

        NodeList nodeList = document.getElementsByTagName("item");
        NodeList nodeListDescription = document.getElementsByTagName("description");

        String description="";
        nodeListCounter = nodeList.getLength();
        //Log.d(TAG, "onCreateView: "+nodeList.getLength());

        for(int i=0; i<nodeListCounter; i++) {
            Element e = (Element) nodeList.item(i);
            Data data = new Data();
            data.setTieude(parser.getValue(e, "title"));
            //Log.d(TAG, "onCreateView: "+parser.getValue(e, "title"));

            data.setThoigian(parser.getValue(e, "pubDate"));
            //Log.d(TAG, "onCreateView: "+parser.getValue(e, "pubDate"));

            data.setLink(parser.getValue(e,"link"));
            //Log.d(TAG, "onCreateView: "+parser.getValue(e,"link"));

            description = nodeListDescription.item(i+1).getTextContent() + "";
            data.setLinkImg(getImageLinkFromCDATA(description));
            //Log.d(TAG, "onCreateView: "+getImageLinkFromCDATA(description));

            data.setDes(getDescriptionFromCDATA(description));
            //Log.d(TAG, "onCreateView: "+getDescriptionFromCDATA(description));
            arr.add(data);
        }

        //recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), arr);
        recyclerView.setAdapter(recyclerViewAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter2.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Data data = arr.get(position);
                if(data.getLink().contains("video.vnexpress.net")){
                    Toast.makeText(getContext(), "Đây là link video,chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
                }
                else if(data.getLink().contains("ttps://e.vnexpress.net")){
                    Toast.makeText(getContext(), "bài viết tiếng anh có định dạng khác, chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getContext(),ChiTiet.class);
                    String link = data.getLink();
                    String linkimg = data.getLinkImg();
                    String date = data.getThoigian();
                    Database database = new Database(getContext(), "Title.sqlite", null, 1);
                    database.QueryData("CREATE TABLE IF NOT EXISTS tblHistory(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(1000), Link VARCHAR(1000), LinkImg VARCHAR(1000), Date VARCHAR(200))");
                    database.InsertHis(data.getTieude(),link, linkimg, date);
                    intent.putExtra("link",data.getLink());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", arr);
                    intent.putExtra("bundle",bundle);
                    intent.putExtra("titleImg",data.getLinkImg());
                    intent.putExtra("possition", position);
                    intent.putExtra("first","first");
                    //database.QueryData("drop table tblHistory");
                    startActivity(intent);
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}