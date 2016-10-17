package com.ban.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ban.Fragment.NewsFragemnt;
import com.ban.Fragment.PersonalFragment;
import com.ban.Fragment.StadiumFragment;
import com.ban.maopu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NoScrollViewPager viewPager;
    private MainActivityAdapter mainActivityAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private TextView toolBarTitle;
    private TextView toolBarRightTitle;
    private LinearLayout newsButton;
    private ImageView newsImg;
    private TextView newsText;
    private LinearLayout stadiumButton;
    private ImageView stadiumImg;
    private TextView stadiumText;
    private LinearLayout personalButton;
    private ImageView personalImg;
    private TextView personalText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.color.toolbar_color);
        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolBarTitle.setText("猫扑篮球");
        toolBarRightTitle = (TextView) findViewById(R.id.toolbar_right_title);
        toolBarRightTitle.setText("");

        init();

        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(2);
        }
        fragmentList.add(new NewsFragemnt());
        fragmentList.add(new StadiumFragment());
        fragmentList.add(new PersonalFragment());

        viewPager.setAdapter(mainActivityAdapter);
    }

    private void init() {
        newsButton = (LinearLayout) findViewById(R.id.fragemnt1);
        newsImg = (ImageView) findViewById(R.id.news_image);
        newsText = (TextView) findViewById(R.id.news_text);
        stadiumButton = (LinearLayout) findViewById(R.id.fragemnt2);
        stadiumImg = (ImageView) findViewById(R.id.stadium_image);
        stadiumText = (TextView) findViewById(R.id.stadium_text);
        personalButton = (LinearLayout) findViewById(R.id.fragemnt3);
        personalImg = (ImageView) findViewById(R.id.personal_image);
        personalText = (TextView) findViewById(R.id.personal_text);
        newsButton.setOnClickListener(this);
        stadiumButton.setOnClickListener(this);
        personalButton.setOnClickListener(this);

        mainActivityAdapter = new MainActivityAdapter(getSupportFragmentManager());
        viewPager = (NoScrollViewPager) findViewById(R.id.view_page);
        viewPager.setNoScroll(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragemnt1:
                viewPager.setCurrentItem(0);
                newsImg.setImageResource(R.drawable.news_chosed);
                stadiumImg.setImageResource(R.drawable.stadium);
                personalImg.setImageResource(R.drawable.personal);
                newsText.setTextColor(getResources().getColor(R.color.toolbar_color));
                stadiumText.setTextColor(getResources().getColor(R.color.text_default));
                personalText.setTextColor(getResources().getColor(R.color.text_default));

                break;
            case R.id.fragemnt2:
                viewPager.setCurrentItem(1);
                newsImg.setImageResource(R.drawable.news);
                stadiumImg.setImageResource(R.drawable.stadium_chosed);
                personalImg.setImageResource(R.drawable.personal);
                newsText.setTextColor(getResources().getColor(R.color.text_default));
                stadiumText.setTextColor(getResources().getColor(R.color.toolbar_color));
                personalText.setTextColor(getResources().getColor(R.color.text_default));
                break;
            case R.id.fragemnt3:
                viewPager.setCurrentItem(2);
                newsImg.setImageResource(R.drawable.news);
                stadiumImg.setImageResource(R.drawable.stadium);
                personalImg.setImageResource(R.drawable.personal_chosed);
                newsText.setTextColor(getResources().getColor(R.color.text_default));
                stadiumText.setTextColor(getResources().getColor(R.color.text_default));
                personalText.setTextColor(getResources().getColor(R.color.toolbar_color));
                break;
        }
    }

    private class MainActivityAdapter extends FragmentStatePagerAdapter{
        public MainActivityAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);

        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }
    }


}
