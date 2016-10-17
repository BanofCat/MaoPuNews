package com.ban.Bean;

/**
 * Created by Administrator on 2016/6/1.
 */
public class User {
    public int id;
    public String phone;
    public String password;
    public String sex;
    public String head;
    public String sno;

    private static class UserHolder{
        private static final User MYUSER = new User();
    }
    private User(){

    }
    public static final User getUser(){
        return UserHolder.MYUSER;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", head='" + head + '\'' +
                ", sno='" + sno + '\'' +
                '}';
    }
}
