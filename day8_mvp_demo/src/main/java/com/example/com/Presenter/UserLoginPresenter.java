package com.example.com.Presenter;

import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.com.Model.MainActivity;
import com.example.com.Modle.UserBean;
import com.example.com.Modle.UserLoginNet;

/**
 * Created by 老赵的拯救者 on 2018/5/18.
 */

public class UserLoginPresenter {

    public void submit(UserBean user) {
        if(TextUtils.isEmpty(user.userName) || TextUtils.isEmpty(user.passWord)){
            mUserLoginInterface.isnull();
            //return false;
        }else{
            mUserLoginInterface.isshow();
            userLogin(user);
        }
       // return true;
    }

    public void userLogin(final UserBean user){
        new Thread() {
            @Override
            public void run() {
                super.run();
                    SystemClock.sleep(2000);
                UserLoginNet net = new UserLoginNet();
                if (net.sendUserLoginInfo(user)) {
                mUserLoginInterface.success();
                }else {
                mUserLoginInterface.failure();
                }
            }
        }.start();

    }

private final userLoginInterface mUserLoginInterface;

    public UserLoginPresenter(userLoginInterface mUserLoginInterface){

        this.mUserLoginInterface = mUserLoginInterface;
    }

}
