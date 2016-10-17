package com.ban.Bean;

import java.util.List;

/**
 *
 *
 * http://api.tianapi.com/wxnew/?key=e7d071997ecb5a30ab5807ddd1f8c1d1&num=10
 * Created by Administrator on 2016/6/6.
 */
public class NewsList {
    public int errNum;
    public String errMsg;
    public List<News> data;

    private static class NewsListHolder{
        private static final NewsList NEWS_LIST = new NewsList();
    }
    private NewsList(){

    }
    public static final NewsList getNewsList(){
        return NewsListHolder.NEWS_LIST;
    }

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "errNum=" + errNum +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
