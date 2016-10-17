package com.ban.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MyNews {
    private int code ;
    private String msg ;
    private List<newlists> newslist;

    private MyNews(){
    }
    private static class MyNewsHolder{
        private static final MyNews MY_NEWS = new MyNews();
    }
    public static final MyNews getMyNews(){
        return MyNewsHolder.MY_NEWS;
    }


    @Override
    public String toString() {
        return "MyNews{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newlist=" + newslist +
                '}';
    }

    public List<newlists> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<newlists> newslist) {
        this.newslist = newslist;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public class newlists {
        //boolean isRead = false;


        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;


        @Override
        public String toString() {
            return "newlists{" +
                    "ctime=" + ctime +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

/*        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }*/
        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
