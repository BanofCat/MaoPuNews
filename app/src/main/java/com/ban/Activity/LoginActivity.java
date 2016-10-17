package com.ban.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ban.Utils.InternetUtil;
import com.ban.maopu.R;


public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwdEdit;
    private TextView regitsiterSkip;
    private Button loginSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        accountEdit = (EditText) findViewById(R.id.account);
        Drawable accountDrawable = getResources().getDrawable(R.drawable.account_icon);
        accountDrawable.setBounds(0, 0, 60, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        accountEdit.setCompoundDrawables(accountDrawable, null, null, null);

        passwdEdit = (EditText) findViewById(R.id.password);
        Drawable passwdDrawable = getResources().getDrawable(R.drawable.password_icon);
        passwdDrawable.setBounds(0, 0, 60, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        passwdEdit.setCompoundDrawables(passwdDrawable, null, null, null);

        regitsiterSkip = (TextView) findViewById(R.id.toolbar_right_title);
        regitsiterSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisiterActivity.class);
                startActivity(intent);
            }
        });

        loginSubmit = (Button) findViewById(R.id.login_submit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void loginCheck()  {
        final String account = accountEdit.getText().toString();
        final String password = passwdEdit.getText().toString();
        View focusView = null;
        boolean cancel = false;


        if(TextUtils.isEmpty(account)){
            accountEdit.setError("账号不能为空！");
            focusView = accountEdit;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            passwdEdit.setError("密码不能为空！");
            focusView = passwdEdit;
            cancel = true;
        }
        if (cancel){

            focusView.requestFocus();
        }
        else {
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    boolean isSuccess = InternetUtil.attempLogin(account,password);

                    if (isSuccess){
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"账号密码错误！",Toast.LENGTH_SHORT).show();

                    }
                    Looper.loop();
                }
            }.start();

       /*     try {
                loginThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
