package com.ban.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.ban.maopu.R;

import java.io.File;

/**
 * Created by Administrator on 2016/4/11.
 */
public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        File maopu = new File(Environment.getExternalStorageDirectory()+"/猫扑篮球/uploadCache");

        if(!maopu.exists()) {
            maopu.mkdirs();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);

    }
}
