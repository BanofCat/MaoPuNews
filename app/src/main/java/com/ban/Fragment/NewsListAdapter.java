package com.ban.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ban.Bean.MyNews;
import com.ban.Utils.ImageUtil;
import com.ban.maopu.R;

import java.util.List;

public class NewsListAdapter  extends  BaseAdapter implements AbsListView.OnScrollListener{
    private LayoutInflater mInflater;
    //private List<News> myNews;
    private List<MyNews.newlists> myNews;
    private ViewHolder viewHolder = null;
    private ImageUtil mImageUtil;
    private int firstVisable,lastVisable;
    public   static String[] urlArray;
    private boolean isFirstCreate;

    /*    public NewsListAdapter(Context context, List<News> myNews){
            this.context = context;
            this.myNews = myNews;
        }*/
    public NewsListAdapter(Context context, List<MyNews.newlists> myNews, ListView listView){
        this.mInflater = LayoutInflater.from(context);
        this.myNews = myNews;
        this.mImageUtil = new ImageUtil(listView);
        urlArray = new String[myNews.size()];
        for (int i = 0; i < myNews.size(); i++) {
            urlArray[i] = myNews.get(i).getPicUrl();
        }
        listView.setOnScrollListener(this);
        isFirstCreate = true;
    }




    @Override
    public int getCount() {
        return myNews.size();
    }

    @Override
    public MyNews.newlists getItem(int position) {
        return myNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_news_item_layout,null);
            viewHolder.newIcon = (ImageView) convertView.findViewById(R.id.news_image);
            viewHolder.newsAuthor = (TextView) convertView.findViewById(R.id.news_author);
        //    viewHolder.newsAuthor.setText(myNews.get(position).getAuthor());
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.newsTime = (TextView) convertView.findViewById(R.id.news_time);


/*            if (myNews.get(position).isRead()){
                viewHolder.newsTitle.setTextColor(convertView.getResources().getColor(R.color.gray));
            }*/
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.newsTime.setText(myNews.get(position).getCtime());
        viewHolder.newIcon.setImageResource(R.drawable.default_portrait);
        viewHolder.newIcon.setTag(myNews.get(position).getPicUrl());
       // ImageUtil.showInternetPic(viewHolder.newIcon,myNews.get(position).getPicUrl());
       // mImageUtil.showInternetPic(viewHolder.newIcon,myNews.get(position).getPicUrl());
        mImageUtil.showInternetPicByAsyncTask(viewHolder.newIcon,myNews.get(position).getPicUrl());
        Log.w("----------title:---",myNews.get(position).getTitle() + "----position:"+position);
        viewHolder.newsTitle.setText(myNews.get(position).getTitle());
        return convertView;
    }


    //检测滑动状态，若正在滑动应停止加载图片，否则易引起滑动过程中的卡顿现象
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){//加载可见项
            mImageUtil.loadImageArray(firstVisable,lastVisable);
        }else {//停止加载任务
            mImageUtil.cancleAllTasks();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstVisable = firstVisibleItem;
        lastVisable  = firstVisibleItem + visibleItemCount;
        if (isFirstCreate && visibleItemCount > 0){
            mImageUtil.loadImageArray(firstVisable,lastVisable);
            isFirstCreate = false;
        }
    }

    public static class ViewHolder{
        ImageView newIcon ;
        TextView newsTitle;
        TextView newsAuthor;
        TextView newsTime;
    }
}