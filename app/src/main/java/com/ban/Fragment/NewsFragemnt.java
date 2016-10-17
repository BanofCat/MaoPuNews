package com.ban.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ban.Activity.NewsContentActivity;
import com.ban.Bean.MyNews;
import com.ban.Utils.InternetUtil;
import com.ban.maopu.R;

/**
 * Created by Administrator on 2016/5/30.
 */
public class NewsFragemnt extends android.support.v4.app.Fragment {

    private ViewPager viewPager;
    private TabLayout tab;
    private NewsAdapter newsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_layout, container, false);
        newsAdapter = new NewsAdapter(getChildFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_page);
        tab = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager.setAdapter(newsAdapter);
        tab.setupWithViewPager(viewPager);

        return rootView;
    }

    public static class NewsChildFragment extends Fragment {

        private NewsListAdapter newsListAdapter;
        private SwipeRefreshLayout swipeRefreshLayout;
        private ListView listView;

        private Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0001:
                        newsListAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                }
            }
        };

        public NewsChildFragment() {
        }

        public static NewsChildFragment newInstance() {
            NewsChildFragment fragment = new NewsChildFragment();
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            updateData();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
/*            View rootView = inflater.inflate(R.layout.fragment_news_list_layout, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.news_list);
            String[] test = {"这是第一项","dierxiang","第三项"};
            listView.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.fragment_news_item_layout,test));*/


            //  Log.e("新闻myNewsList：", NewsList.getNewsList().getData().toString());
            // Log.e("新闻myNewsList：", MyNews.getMyNews().getNewslist().toString());

            //     newsListAdapter = new NewsListAdapter(getContext(), NewsList.getNewsList().getData());

            View rootView = inflater.inflate(R.layout.fragment_news_list_layout, container, false);

            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.news_swip);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateData();
                    Message msg = new Message();
                    msg.what = 0001;
                    myHandler.sendMessageDelayed(msg, 2000);
                    Looper.loop();

                }
            });
            listView = (ListView) rootView.findViewById(R.id.news_list);
            newsListAdapter = new NewsListAdapter(getContext(), MyNews.getMyNews().getNewslist(),listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.news_title);
                    textView.setTextColor(getResources().getColor(R.color.gray));
                    Intent intent = new Intent();
                    intent.putExtra("url", MyNews.getMyNews().getNewslist().get(position).getUrl());
                    Log.e("position:--------id--", position + "---" + id);
                    intent.setClass(getActivity(), NewsContentActivity.class);
                    startActivity(intent);
                    //NewsList.getNewsList().getData().get(position).setRead(true);
                    //  MyNews.getMyNews().getNewslist().get(position).setRead(true);
                }
            });
            listView.setAdapter(newsListAdapter);
            return rootView;
        }

        private void updateData() {
            Thread getNewsThread = new Thread() {
                @Override
                public void run() {
                    InternetUtil.getNewsLists();
                }
            };

            try {
                getNewsThread.start();
                getNewsThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public class NewsAdapter extends FragmentPagerAdapter {


        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsChildFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NBA";
                case 1:
                    return "CBA";
            }
            return null;

        }
    }


}
