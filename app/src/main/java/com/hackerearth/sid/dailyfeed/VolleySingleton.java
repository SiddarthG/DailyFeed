package com.hackerearth.sid.dailyfeed;


import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(){

        mRequestQueue = Volley.newRequestQueue(MyApp.getAppContext());
        mImageLoader=new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

    }

    public static VolleySingleton getsInstance(){
        if(sInstance==null){
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){

        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
