package com.lijiadayuan.lishijituan;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;



public class HomeActivity extends TabActivity {
    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_FIND = "SEARCH_ACTIVITY";
    public static final String TAB_NEWS = "CATEGORY_ACTIVITY";
    public static final String TAB_MINE = "CART_ACTIVITY";

    private RadioButton radioMain, radioFind, radioNews, radioMine;
    private Drawable drawMain, drawFind, drawNews, drawMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById();
        initView();
    }
    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioFind = (RadioButton) findViewById(R.id.home_tabe_find);
        radioMain = (RadioButton) findViewById(R.id.home_tabe_main);
        radioNews = (RadioButton) findViewById(R.id.home_tabe_news);
        radioMine = (RadioButton) findViewById(R.id.home_tabe_mine);

        drawMain = getResources().getDrawable(R.drawable.main_home);
        drawFind = getResources().getDrawable(R.drawable.main_search);
        drawNews = getResources().getDrawable(R.drawable.main_house);
        drawMine = getResources().getDrawable(R.drawable.main_more);
        drawMain.setBounds(0, 0, 60, 60);
        drawFind.setBounds(0, 0, 60, 60);
        drawNews.setBounds(0, 0, 60, 60);
        drawMine.setBounds(0, 0, 60, 60);
        radioMain.setCompoundDrawables(null, drawMain, null, null);
        radioFind.setCompoundDrawables(null, drawFind, null, null);
        radioNews.setCompoundDrawables(null, drawNews, null, null);
        radioMine.setCompoundDrawables(null, drawMine, null, null);
    }
    private void initView() {

        mTabHost = getTabHost();

        Intent i_main = new Intent(this, MainActivity1.class);
        Intent i_find = new Intent(this, FindActivity.class);
        Intent i_news = new Intent(this, NewsActivity.class);
        Intent i_mine = new Intent(this, MineActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_FIND)
                .setIndicator(TAB_FIND).setContent(i_find));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_NEWS)
                .setIndicator(TAB_NEWS).setContent(i_news));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE)
                .setContent(i_mine));

        mTabHost.setCurrentTabByTag(TAB_MAIN);
        mTabButtonGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tabe_main:
                                mTabHost.setCurrentTabByTag(TAB_MAIN);
                                break;

                            case R.id.home_tabe_find:
                                mTabHost.setCurrentTabByTag(TAB_FIND);
                                break;

                            case R.id.home_tabe_news:
                                mTabHost.setCurrentTabByTag(TAB_NEWS);
                                break;

                            case R.id.home_tabe_mine:
                                mTabHost.setCurrentTabByTag(TAB_MINE);
                                break;


                            default:
                                break;
                        }
                    }
                });
    }
}
