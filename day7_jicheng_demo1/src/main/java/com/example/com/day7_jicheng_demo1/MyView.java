package com.example.com.day7_jicheng_demo1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by 老赵的拯救者 on 2018/5/16.
 */

public class MyView extends ScrollView {
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // alpha = 滑出去的高度/(screenHeight/3);
        float heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
        float scrollY = getScrollY();//该值 大于0
        float alpha = 1-scrollY/(heightPixels/3);// 0~1 透明度是1~0
//这里选择的screenHeight的1/3 是alpha改变的速率 （根据你的需要你可以自己定义）

        if(onScrollChanged != null){
            onScrollChanged.setOnScrollChange(this,l, t, oldl, oldt,alpha);
        }
    }

    public interface onScrollChanged{
        void setOnScrollChange(MyView myview, int l, int t, int oldl, int oldt, float alpha);
    }

    private onScrollChanged onScrollChanged;

    public void setOnscrollChangeLitener(onScrollChanged onscrollChangeLitener){
        onScrollChanged = onscrollChangeLitener;
    }
}
