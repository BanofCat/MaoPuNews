package com.ban.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ban.maopu.R;

/**
 * Created by Administrator on 2016/6/6.
 */
public class NewsContentActivity extends Activity {
//    private int id;
/*    private TextView newsTitle;
    private TextView newsTime;
    private TextView newsAuthor;
    private ImageView newsPicture;
    private TextView newsContent;
    private TextView toolBarTitle;
    private TextView toolBarRightTitle;*/

    private WebView webView;
    private ImageButton mBack;
    private ImageButton mRefesh;
    private EditText mEdit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.news_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolBarTitle.setText("猫扑新闻");
        toolBarRightTitle = (TextView) findViewById(R.id.toolbar_right_title);
        toolBarRightTitle.setText("");
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        newsTitle = (TextView) findViewById(R.id.title);
        newsTitle.setText(NewsList.getNewsList().getData().get(id).getNewsTitle());

        newsTime = (TextView) findViewById(R.id.time);
        newsTime.setText(NewsList.getNewsList().getData().get(id).getTime());

        newsAuthor = (TextView) findViewById(R.id.author);
        newsAuthor.setText(NewsList.getNewsList().getData().get(id).getAuthor());

        newsPicture = (ImageView) findViewById(R.id.picture);

        newsContent = (TextView) findViewById(R.id.contentPanel);
        newsContent.setText(NewsList.getNewsList().getData().get(id).getNewsContent());*/

        Intent intent = getIntent();
        String mUrl = intent.getStringExtra("url");

        setContentView(R.layout.news_web);
        webView = (WebView) findViewById(R.id.web);
        mEdit = (EditText) findViewById(R.id.address);
        mBack = (ImageButton) findViewById(R.id.back);
        mRefesh = (ImageButton) findViewById(R.id.refresh);

    //    mEdit.setText(mUrl);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) webView.goBack();
            }
        });
        mRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mEdit.setText(title);

            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        if (mUrl != null) webView.loadUrl(mUrl);


    }
}
