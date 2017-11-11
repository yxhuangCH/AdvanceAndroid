package com.example.yxhuang.viewdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final View view = findViewById(R.id.tv);
//        view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xff00ff00);
////                animator.setEvaluator(new HsvEvaluator());
////                animator.start();
//
//            }
//        }, 2000);


        Intent intent =getIntent();
        if (intent != null){
            Log.e(TAG, "scheme:" +intent.getScheme());
            Uri uri =intent.getData();
            if (uri != null){
                Log.e(TAG, "scheme: "+uri.getScheme());
                Log.e(TAG, "host: "+uri.getHost());
                Log.e(TAG, "port: "+uri.getPort());
                Log.e(TAG, "path: "+uri.getPath());
                Log.e(TAG, "queryString: "+uri.getQuery());
                Log.e(TAG, "queryParameter: "+uri.getQueryParameter("key"));
            }
        }
    }
}
