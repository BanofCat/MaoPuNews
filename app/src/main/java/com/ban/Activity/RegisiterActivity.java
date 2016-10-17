package com.ban.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ban.Utils.ImageUtil;
import com.ban.Utils.InternetUtil;
import com.ban.maopu.R;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/19.
 */
public class RegisiterActivity extends AppCompatActivity {
    TextView toolBarRightTitle;
    TextView toolBarTitle;

    ImageView headPortrait;
    EditText accountET;
    EditText passwordET;
    EditText repeatET;
    EditText studentNO_ET;
    Bitmap mBitmap;
    static final int IMAGE_SELECT = 1;
    static final int IMAGE_CUT = 2;
    Button submit;
    private File imageFile;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regisiter_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        connectComponmentID();
    }

    private void connectComponmentID() {


        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCheck();
            }
        });

        headPortrait = (ImageView) findViewById(R.id.head_portrait);

        headPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent,IMAGE_SELECT);
            }

        });
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_portrait);
        mBitmap = ImageUtil.fixToCircle(mBitmap);
        headPortrait.setImageBitmap(mBitmap);



        accountET = (EditText) findViewById(R.id.account);
        setLogoSize(accountET,R.drawable.account_icon);

        passwordET = (EditText) findViewById(R.id.password);
        setLogoSize(passwordET,R.drawable.password_icon);

        repeatET = (EditText) findViewById(R.id.repeat_password);
        setLogoSize(repeatET,R.drawable.password_icon);

        studentNO_ET = (EditText) findViewById(R.id.student_no);
        setLogoSize(studentNO_ET,R.drawable.student_no_logo);

        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolBarTitle.setText("注册");
        toolBarRightTitle = (TextView) findViewById(R.id.toolbar_right_title);
        toolBarRightTitle.setText("");

    }

    private void registerCheck() {
        final String phone = accountET.getText().toString();
        final String password = passwordET.getText().toString();
        final String repeat = repeatET.getText().toString();
        final String studentNO = studentNO_ET.getText().toString();
        boolean cancel = false;

        boolean isPhone = false;
        final Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        isPhone = p.matcher(phone).matches();
        View focusView = null;
        if(TextUtils.isEmpty(phone)){
            accountET.setError("手机号不能为空！");
            focusView = accountET;
            cancel = true;
        }
        else if(phone.length() != 11 || !isPhone){
            accountET.setError("请输入正确的手机号码！");
            focusView = accountET;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            passwordET.setError("密码不能为空！");
            focusView = passwordET;
            cancel = true;
        }
        else if(password.length()<6){
            passwordET.setError("密码太短！密码必须为6位以上！");
            focusView = passwordET;
            cancel = true;
        }

        if (!password.equals(repeat)){
            repeatET.setError("两次输入密码不一致！");
            focusView = repeatET;
            cancel = true;
        }

        if (TextUtils.isEmpty(studentNO)){
            studentNO_ET.setError("学号不能为空！");
            focusView = studentNO_ET;
            cancel = true;
        }
        else if (studentNO.length() != 10){
            studentNO_ET.setError("输入学号不正确！");
            focusView = studentNO_ET;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        }
        else{
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    boolean isSuccess = InternetUtil.attempRegister(phone,password,studentNO);

                    if (isSuccess){
                        Thread uploadThread = new Thread(){
                            @Override
                            public void run() {
                                Log.e("aaaaaa","aaaaaaaa");
                               String finishUpload =  InternetUtil.upLoadHeadPortait(imageFile);

                                if (finishUpload != null){
                                    InternetUtil.attempLogin(phone,password);
                                    Intent intent = new Intent();
                                    intent.setClass(RegisiterActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"头像上传失败！请检查网络设置！",Toast.LENGTH_SHORT).show();
                                    Log.e("上传失败","上传失败");
                                }
                            }
                        };

                        try {
                            uploadThread.start();
                            uploadThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"注册失败！账号已存在！",Toast.LENGTH_SHORT).show();

                    }
                    Looper.loop();
                }
            }.start();

        }

    }

    public void setLogoSize(TextView textView,int logoPosition){
        Drawable drawable  = getResources().getDrawable(logoPosition);
        drawable.setBounds(0, 0, 60, 60);
        textView.setCompoundDrawables(drawable,null,null,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == IMAGE_SELECT) {
                Uri myuri = data.getData();
                Intent imageIntent = ImageUtil.scropPictureSet(myuri);
                startActivityForResult(imageIntent, IMAGE_CUT);
                }
            else if (requestCode == IMAGE_CUT){
                Bundle extras = data.getExtras();
                if (extras != null) {
                    mBitmap = extras.getParcelable("data");
                    imageFile = ImageUtil.savePicture(mBitmap);//成功注册后再保存信息---------------------------------------------
                    mBitmap = ImageUtil.fixToCircle(mBitmap);
                    headPortrait.setImageBitmap(mBitmap);

                }
            }

        }


    }

}
