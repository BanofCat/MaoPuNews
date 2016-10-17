package com.ban.Utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.ban.Fragment.NewsListAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/24.
 */
public class ImageUtil {
    private  ImageView targetView ;
    private String mUrl;
    private LruCache<String,Bitmap> mLruCache;
    private ListView mListView;
    private Set<NewsAsyncTak> mTasks;


    public ImageUtil(ListView listview){
        mListView = listview;
        mTasks = new HashSet<>();
        int maxSize = (int) Runtime.getRuntime().maxMemory();   //获取最大内存
        int cacheSize = maxSize / 4;
        mLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


    private  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (targetView.getTag().equals(mUrl)) targetView.setImageBitmap((Bitmap) msg.obj);
        }
    };


    public static Bitmap fixToCircle(Bitmap mBitmap){
        Canvas mCanvas ;
        BitmapShader mBitmapShader ;
        Paint mPaint;
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();

        int r =  height>width?width:height;

        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(),Bitmap.Config.ARGB_8888);
        RectF rect = new RectF(0,0,r,r);
        mCanvas = new Canvas(bitmap);
     //   mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
     //   mPaint.setShader(mBitmapShader);
        mCanvas.drawRoundRect(rect,r/2,r/2,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(mBitmap,null,rect,mPaint);

    return bitmap;
    }



    public static  File savePicture(Bitmap mBitmap){
        String name = "default";
       File imageFile = new File(Environment.getExternalStorageDirectory()+"/猫扑篮球/uploadCache/"+name+".jpg");
        try {
            FileOutputStream fo = new FileOutputStream(imageFile);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public static Intent scropPictureSet(Uri myuri){
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(myuri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        return intent;
    }


    private Bitmap getBitmapFromURL(String urlString){
        Bitmap bitmap = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public  void showInternetPic(ImageView imageView, final String urlString){
        targetView = imageView;
        mUrl = urlString;
        new Thread(){
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getBitmapFromURL(urlString);;
                mHandler.sendMessage(msg);
            }

        }.start();
    }



    public void showInternetPicByAsyncTask(ImageView imageView, final String urlString){

        Bitmap bitmap = getBitmapFromCache(urlString);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);



    }
    private void addBitmapToCache(String url , Bitmap bitmap){
        if (getBitmapFromCache(url) == null){
            mLruCache.put(url,bitmap);
        }
    }
    private Bitmap getBitmapFromCache(String url){
         return mLruCache.get(url);

    }

    public void loadImageArray(int start, int end) {
        for(int i = start; i < end ; i++){
            String url = NewsListAdapter.urlArray[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null){
                NewsAsyncTak task = new NewsAsyncTak(url);
                task.execute(url);
                mTasks.add(task);
            }else{
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void cancleAllTasks() {
        if (mTasks != null)
        for (NewsAsyncTak task : mTasks){
            task.cancel(false);
        }
    }

    private class NewsAsyncTak extends AsyncTask<String,Void,Bitmap>{
        private String mmUrl;


        public NewsAsyncTak( String url){
            mmUrl = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if (bitmap != null) addBitmapToCache(url,bitmap);
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mmUrl);
            if (imageView != null && bitmap != null) imageView.setImageBitmap(bitmap);
            mTasks.remove(this);
        }
    }
}
