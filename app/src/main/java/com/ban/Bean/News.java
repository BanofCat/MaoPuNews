package com.ban.Bean;

/**
 * Created by Administrator on 2016/6/1.
 */
public class News {
    public int id;
    public int userId;
    public String newsTitle;
    public String author;
    public String newsContent;
    public String time;
    public String state;
    public String picture;

    public boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", userId=" + userId +
                ", newsTitle='" + newsTitle + '\'' +
                ", author='" + author + '\'' +
                ", newsContent='" + newsContent + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
