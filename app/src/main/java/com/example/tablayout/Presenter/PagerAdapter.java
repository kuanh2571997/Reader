package com.example.tablayout.Presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tablayout.View.OneFragment;
import com.example.tablayout.View.VideoFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        String url;
        Bundle bundle;
        switch (position) {
            case 1:
                url = "https://vnexpress.net/rss/thoi-su.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 2:
                url = "https://vnexpress.net/rss/the-gioi.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 3:
                url = "https://vnexpress.net/rss/kinh-doanh.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 4:
                url = "https://vnexpress.net/rss/startup.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 5:
                url = "https://vnexpress.net/rss/giaitri.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 6:
                url = "https://vnexpress.net/rss/the-thao.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 7:
                url = "https://vnexpress.net/rss/phap-luat.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 8:
                url = "https://vnexpress.net/rss/giao-duc.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 9:
                url = "https://vnexpress.net/rss/suc-khoe.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 10:
                url = "https://vnexpress.net/rss/doi-song.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 11:
                url = "https://vnexpress.net/rss/du-lich.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 12:
                url = "https://vnexpress.net/rss/khoa-hoc.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 13:
                url = "https://vnexpress.net/rss/so-hoa.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 14:
                url = "https://vnexpress.net/rss/oto-xe-may.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 15:
                url = "https://vnexpress.net/rss/y-kien.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 16:
                url = "https://vnexpress.net/rss/tam-su.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 17:
                url = "https://vnexpress.net/rss/cuoi.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 0:
                url = "https://vnexpress.net/rss/tin-moi-nhat.rss";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new OneFragment();
                frag.setArguments(bundle);
                break;
            case 18:
                url = "http://ocp-api-v2.gdcvn.com/v1/publishers/get-items?id=120&limit=0&offset=20&publisher_key=zw5yfhcygiruH81M";
                bundle = new Bundle();
                bundle.putString("link", url);
                frag = new VideoFragment();
                frag.setArguments(bundle);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 19;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 1:
                title = "Thời sự";
                break;
            case 2:
                title = "Thế giới";
                break;
            case 3:
                title = "Kinh doanh";
                break;
            case 4:
                title = "Startup";
                break;
            case 5:
                title = "Giải trí";
                break;
            case 6:
                title = "Thể thao";
                break;
            case 7:
                title = "Pháp luật";
                break;
            case 8:
                title = "Giáo dục";
                break;
            case 9:
                title = "Sức khỏe";
                break;
            case 10:
                title = "Đời sống";
                break;
            case 11:
                title = "Du lịch";
                break;
            case 12:
                title = "Khoa học";
                break;
            case 13:
                title = "Số hóa";
                break;
            case 14:
                title = "Xe";
                break;
            case 15:
                title = "Ý kiến";
                break;
            case 16:
                title = "Tâm sự";
                break;
            case 17:
                title = "Cười";
                break;
            case 0:
                title = "Trang chủ";
                break;
            case 18:
                title = "Video";
                break;
        }
        return title;
    }

}
