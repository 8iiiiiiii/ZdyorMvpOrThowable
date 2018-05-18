package com.example.com.day7_jicheng_demo1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyView myView = (MyView) findViewById(R.id.myview);
        image = (ImageView) findViewById(R.id.iv_detail);
        text = (TextView) findViewById(R.id.tv_titlebar);
        myView.setOnscrollChangeLitener(new MyView.onScrollChanged() {
            @Override
            public void setOnScrollChange(MyView myview, int l, int t, int oldl, int oldt, float alpha) {
                Bitmap abc = BitmapFactory.decodeResource(getResources(), R.mipmap.abc);
                //得到xml中图片的高度
                int height = image.getLayoutParams().height;
                if(height==0){
                    text.setVisibility(View.GONE);
                    text.setAlpha(1-alpha);
                }else if(height<t){
                    text.setVisibility(View.VISIBLE);
                    text.setAlpha(1-alpha);
                }else if(height>t){
                    text.setVisibility(View.VISIBLE);
                    text.setAlpha(1-alpha);
                }

            }
        });
    }
}
