package com.ban.Utils;

import android.util.Log;

import com.ban.Bean.MyNews;
import com.ban.Bean.NewsList;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Administrator on 2016/6/2.
 */
public class InternetUtil {


    private static final String TAG = "InternetUtil";

    public static boolean downloadHeadPortrait(String portraitName){
        boolean isSuccess = false;
        String address = " http://114.215.128.92:9090/greenlife/"+portraitName;
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if (conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                isSuccess = true;

            }
            else Log.e(TAG,"链接失败");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSuccess;

    }
    /*public static boolean upLoadHeadPortait(File file){
            boolean isSuccess = false;
            StringBuilder json = new StringBuilder();
            String address = "http://114.215.128.92:9090/greenlife/file/upload.do";
            String newName = UUID.randomUUID().toString();
            String BOUNDARY = "*****";
            String PREFIX = "--",LINE_END = "\r\n";
            String CONTENT_TYPE = "multipart/form-data";

            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(50000);
                connection.setUseCaches(false);
                connection.setRequestProperty("Charset","utf-8");
                connection.setRequestProperty("connection","keep-alive");
                connection.setRequestProperty("Content-Type",CONTENT_TYPE+";boundary="+BOUNDARY);
              //  connection.connect();
                Log.e("bbbbbb","bbbbbbb");
                if (file!=null){
                    Log.e("cccccc","ccccccc");
                    OutputStream outputStream = connection.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(PREFIX);
                    stringBuffer.append(BOUNDARY);
                    stringBuffer.append(LINE_END);
                    stringBuffer.append("Content-Disposition: form-data; " +
                            "name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);
                 //   stringBuffer.append("Content-Type: application/octet-stream; charset=utf-8"+LINE_END);
                    stringBuffer.append(LINE_END);
                    dataOutputStream.write(stringBuffer.toString().getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes))!= -1){
                        dataOutputStream.write(bytes,0,len);

                    }
                    is.close();
                    dataOutputStream.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                    dataOutputStream.write(end_data);
                    dataOutputStream.flush();
                    Log.e("ddddddd","dddddddd");
                    if (200 == connection.getResponseCode()){
                        Log.e("eeeeeeeeeeee","eeeee");
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine = null;
                        while ((inputLine = in.readLine())!= null){
                            json.append(inputLine);
                        }
                        in.close();
                        Log.e("上传测试：",json.toString());
                        return true;
                    }
                    else Log.e("xxxxxxx","xxxxxxxxx");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isSuccess;
        }*/
    public static String upLoadHeadPortait( File uploadFile) {
        String result = null;
        String actionUrl = "http://114.215.128.92/GreenLife/file/upload.do";
        String newName = UUID.randomUUID().toString() + ".png";
        String end = "\r\n";
        String Hyphens = "--";
        String boundary = "*****";
        StringBuffer b = new StringBuffer();
        URL url = null;
        try {
            url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
        /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
         /* 设定传送的method=POST */
            con.setRequestMethod("POST");
        /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
        /* 设定DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
        /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
        /* 设定每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
        /* 从文件读取数据到缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
            /* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();
        /* 取得Response内容 */
            if(con.getResponseCode() == 200){

                InputStream is = con.getInputStream();
                int ch;
                while ((ch = is.read()) != -1) {
                    b.append((char) ch);
                }
                Log.e(TAG, b.toString());

                Log.e(TAG,"qqqqqqqqqqq");
                JSONObject jsonObject = new JSONObject(b.toString());
                if (jsonObject.getInt("resultCode") == 200)
                    result = jsonObject.getString("data");
                else Log.e(TAG,"上传失败");
            }
            else Log.e(TAG,"sssssssss");

            ds.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean getNewsList(){
        boolean isSuccess = false;
        String address = "http://121.42.165.98:8080/News/listNews";
        String responeData = sendGet(address);

        Gson gson = new Gson();

        NewsList newsList =gson.fromJson(responeData,NewsList.class);
        NewsList.getNewsList().setData(newsList.getData());
        Log.e("新闻列表获取：",NewsList.getNewsList().getData().toString());
        return true;
    }
    public static boolean getNewsLists(){
        boolean isSuccess = false;
        String address = "http://api.tianapi.com/tiyu/?key=e7d071997ecb5a30ab5807ddd1f8c1d1&num=20";
        String responeData = sendGet(address);

        Gson gson = new Gson();
        MyNews myNews = gson.fromJson(responeData,MyNews.class);
        if (myNews.getCode() == 200){
            Log.w("新闻列表获取：","成功");
            MyNews temp = MyNews.getMyNews();
            temp.setCode(myNews.getCode());
            temp.setMsg(myNews.getMsg());
            temp.setNewslist(myNews.getNewslist());
          //  Log.e("新闻列表获取：",MyNews.getMyNews().getNewslist().toString());
            return true;
        }
/*        NewsList newsList =gson.fromJson(responeData,NewsList.class);
        NewsList.getNewsList().setData(newsList.getData());

        Log.e("新闻列表获取：",NewsList.getNewsList().getData().toString());*/
        return false;
    }




    public static boolean attempLogin( String account,  String password) {
        boolean isSuccess =false;
        String addrress = "http://121.42.165.98:8080/News/userLogin?phone="+account+"&password="+password;
        String responeData = sendGet(addrress);
        isSuccess = DataDealUtil.getUserFromJson(responeData);
        return isSuccess;
    }

    public static boolean attempRegister(String account,String password,String sno){

        boolean isSuccess = false;
        String address = "http://121.42.165.98:8080/News/addUser?phone="+account+"&password="+password+"&sno="+sno;
        String responeData = sendGet(address);
        isSuccess = DataDealUtil.getUserFromJson(responeData);
        return isSuccess;
    }

    private static String sendGet(String urlString){
        StringBuilder json = new StringBuilder();
        try {
            Log.e("aaaaaaa","aaa");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
            connection.connect();
            if (200 == connection.getResponseCode()){
                Log.e("wqeqw:","qweqweqwe");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine())!= null){
                    json.append(inputLine);
                }
                in.close();
            }
            else Log.e("链接失败:","返回码不是200");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Log.e("get获取JSON数据",json.toString());
            return json.toString();
        }

    }




}
