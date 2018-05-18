package com.example.com.Presenter;

public interface userLoginInterface{
        /**
         * 成功
         * */
        void success();

        /**
         * 失败
         * */
        void failure();
        //判断为空
        void isnull();
       //根据为空再判断是否显示进度条
        void isshow();
}