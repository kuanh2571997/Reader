package com.example.tablayout.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tablayout.Model.ConnectionReceiver;
import com.example.tablayout.Presenter.PagerAdapter;
import com.example.tablayout.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tabLayout;
    PagerAdapter pagerAdapter;
    //ActionBar actionBar;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    ArrayList<String> arrHis = new ArrayList<>();
    //LinearLayout imgThoiSu, imgVideo, imgTrangChu, imgPhapLuat, imgGiaiTri;
    private static final String TAG = "MainActivityLog";
    BottomNavigationView navigation;
    Menu menu;
    MenuItem menuItem;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu = navigation.getMenu();
//        imgTrangChu = findViewById(R.id.imgTrangChu);
//        imgTrangChu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(0);
//                //toolbar.setTitle("Trang chủ");
//            }
//        });
//        imgThoiSu = findViewById(R.id.imgThoiSu);
//        imgThoiSu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(1);
//                //toolbar.setTitle("Thời sự");
//            }
//        });
//        imgVideo = findViewById(R.id.imgVideo);
//        imgVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(18);
//                //toolbar.setTitle("Video");
//            }
//        });
//        imgPhapLuat = findViewById(R.id.imgPhapLuat);
//        imgPhapLuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(7);
//                //toolbar.setTitle("Pháp luật");
//            }
//        });
//        imgGiaiTri = findViewById(R.id.imgGiaiTri);
//        imgGiaiTri.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(5);
//                toolbar.setTitle("Giải trí");
//            }
//        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                SetMenu(menuItem);
                return true;
            }
        });
        boolean check = ConnectionReceiver.isConnected();
        if (check) {
            pagerAdapter = new PagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            //tabLayout.setupWithViewPager(pager);
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Trang chủ");
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.icon_menu);
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    String title = getTitle(i);
                    toolbar.setTitle(title);
                    if(i==0||i==1||i==18||i==7||i==5){
                        menu.getItem(convert(i)).setChecked(true);
                    }
                    else{
                        menu.setGroupCheckable(0,true,false);
                        for(int j=0;j<menu.size();j++){
                            menu.getItem(j).setChecked(false);
                        }
                        menu.setGroupCheckable(0,true,true);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    pager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            Toast.makeText(this, "Thiết bị chưa kết nối Internet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Off.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetMenu(MenuItem menuItem) {
        drawerLayout.closeDrawers();
        String title = "";
        switch (menuItem.getItemId()) {
            case R.id.nav_saved:
                Intent intent = new Intent(MainActivity.this, Off.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                Intent intent1 = new Intent(MainActivity.this, HisAndSave.class);
                startActivity(intent1);
                break;
            case R.id.nav_Video:
                pager.setCurrentItem(18);
                Menu menu = navigation.getMenu();
                break;
            case R.id.nav_TrangChu:
                pager.setCurrentItem(0);
                break;
            case R.id.nav_ThoiSu:
                pager.setCurrentItem(1);
                break;
            case R.id.nav_TheGioi:
                pager.setCurrentItem(2);
                break;
            case R.id.nav_KinhDoanh:
                pager.setCurrentItem(3);
                break;
            case R.id.nav_StarUp:
                pager.setCurrentItem(4);
                break;
            case R.id.nav_GiaiTri:
                pager.setCurrentItem(5);
                break;
            case R.id.nav_TheThao:
                pager.setCurrentItem(6);
                break;
            case R.id.nav_PhapLuat:
                pager.setCurrentItem(7);
                break;
            case R.id.nav_GiaoDuc:
                pager.setCurrentItem(8);
                break;
            case R.id.nav_SucKhoe:
                pager.setCurrentItem(9);
                break;
            case R.id.nav_DoiSong:
                pager.setCurrentItem(10);
                break;
            case R.id.nav_DuLich:
                pager.setCurrentItem(11);
                break;
            case R.id.nav_KhoaHoc:
                pager.setCurrentItem(12);
                break;
            case R.id.nav_SoHoa:
                pager.setCurrentItem(13);
                break;
            case R.id.nav_Xe:
                pager.setCurrentItem(14);
                break;
            case R.id.nav_YKien:
                pager.setCurrentItem(15);
                break;
            case R.id.nav_TamSu:
                pager.setCurrentItem(16);
                break;
            case R.id.nav_Cuoi:
                pager.setCurrentItem(17);
                break;
            case R.id.item_kinhte:
                Toast.makeText(this, "kinh tế", Toast.LENGTH_SHORT).show();
        }
    }

    public String getTitle(int possition) {
        String title = "";
        switch (possition) {
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_TrangChu:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.navigation_ThoiSu:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.navigation_Video:
                    pager.setCurrentItem(18);
                    return true;
                case R.id.navigation_PhapLuat:
                    pager.setCurrentItem(7);
                    return true;
                case R.id.navigation_GiaiTri:
                    pager.setCurrentItem(5);
                    return true;
            }
            return false;
        }
    };

    public int convert(int i){
        if(i==18){
            return 2;
        }
        else if(i==7){
            return 3;
        }
        else if(i==5){
            return 4;
        }
        else return i;
    }

}
