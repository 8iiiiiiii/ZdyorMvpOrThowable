package com.example.com.Modle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 老赵的拯救者 on 2018/5/17.
 */

public class UserBean {

    public String userName;
    public String passWord;
    private List<String> list = new ArrayList<>();


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
