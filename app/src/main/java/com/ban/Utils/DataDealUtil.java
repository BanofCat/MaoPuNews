package com.ban.Utils;

import android.util.Log;

import com.ban.Bean.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DataDealUtil {

        public static boolean getUserFromJson(String jsonString){
            boolean isLogin = false;
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if(jsonObject.getInt("errNum") == 200) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    User.getUser().setId(data.getInt("id"));
                    User.getUser().setPhone(data.getString("phone"));
                    User.getUser().setPassword(data.getString("password"));
                    User.getUser().setSex(data.getString("sex"));
                    User.getUser().setHead(data.getString("head"));
                    User.getUser().setSno(data.getString("sno"));
                    isLogin = true;
                    Log.e("用户数据：", User.getUser().toString());
                }
                else {
                    isLogin = false;
                    Log.e("登录失败：", "dsadawe");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                return isLogin;
            }
        }


}
