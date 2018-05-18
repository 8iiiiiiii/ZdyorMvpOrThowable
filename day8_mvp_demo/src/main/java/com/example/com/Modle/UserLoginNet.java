package com.example.com.Modle;

import android.os.SystemClock;

/**
 * Created by 老赵的拯救者 on 2018/5/17.
 */

public class UserLoginNet {

    public boolean sendUserLoginInfo(UserBean user){
        SystemClock.sleep(2000);
        if("zsj".equals(user.userName) && "zsj".equals(user.passWord)) {
            //登陆成功
            return true;
        } else {
            //登录失败
        return false;
        }
    }

}
