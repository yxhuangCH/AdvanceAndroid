package com.example.yxhuang.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(R.id.tv);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xff00ff00);
//                animator.setEvaluator(new HsvEvaluator());
//                animator.start();

            }
        }, 2000);
    }
}
