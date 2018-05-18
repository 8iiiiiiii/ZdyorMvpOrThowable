package com.example.com.rk0517_demo1;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView plv;
    private int url = 1;
    private int type = 1;
    private String path = "https://www.zhaoapi.cn/product/getProducts?pscid="+type;
    private List<UserBean.DataBean> list = new ArrayList<>();
    private Mybase base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plv = (PullToRefreshListView) findViewById(R.id.plv);

        initPlv();
        getdata();
    }

    //初始化
    private void initPlv() {
        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            url = 1;
            type = 1;
                path = "https://www.zhaoapi.cn/product/getProducts?pscid="+type; 
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                url ++;
                type = 2;
                path = "https://www.zhaoapi.cn/product/getProducts?pscid="+type;
                getdata();
            }
        });
    }

    //调用
    private void getdata() {
    new MyTask().execute(path);
    }

    //获取数据
    class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                if(urlConnection.getResponseCode()==200){
                    InputStream inputStream = urlConnection.getInputStream();
                    String s = StreamToString(inputStream);
                    return s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            List<UserBean.DataBean> data = gson.fromJson(s, UserBean.class).getData();
            if(type==1){
                list.clear();
            }
            list.addAll(data);
            setAdpter();
            plv.onRefreshComplete();
        }
    }

    private String StreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    private void setAdpter() {
        if(base==null){
        plv.setAdapter(new Mybase());
        }else{
            base.notifyDataSetChanged();
        }
    }

    class Mybase extends BaseAdapter{

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//使用内存缓存
                .cacheOnDisk(true)//使用磁盘缓存
                .showImageOnFail(R.mipmap.ic_launcher)//下载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片色彩模式
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片的缩放模式
                .build();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHodler hodler;
            if(view==null){
                view = View.inflate(MainActivity.this,R.layout.mybase,null);
                hodler = new ViewHodler();
                hodler.image = view.findViewById(R.id.zk_image);
                hodler.title = view.findViewById(R.id.zk_title);
                hodler.yuan_price = view.findViewById(R.id.yuan_price);
                hodler.now_price = view.findViewById(R.id.now_price);
                view.setTag(hodler);
            }else{
                hodler = (ViewHodler) view.getTag();
            }
            ImageLoader.getInstance().displayImage(list.get(i).getImages().split("\\|")[0],hodler.image,options);
            hodler.title.setText(list.get(i).getTitle());
            hodler.yuan_price.setText("原价："+"￥"+(float)list.get(i).getPrice());
            hodler.now_price.setText("优惠价："+"￥"+(float)list.get(i).getBargainPrice());
            return view;
        }
        class ViewHodler{
            ImageView image;
            TextView title,yuan_price,now_price;
        }
    }
}
