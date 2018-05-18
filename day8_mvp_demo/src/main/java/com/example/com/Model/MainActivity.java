package com.example.com.Model;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.com.Modle.UserBean;
import com.example.com.Presenter.UserLoginPresenter;
import com.example.com.Presenter.userLoginInterface;
import com.example.com.day8_mvp_demo.R;

public class MainActivity extends AppCompatActivity implements userLoginInterface {

    private EditText name;
    private EditText pwd;
    private Button login;
    private ProgressDialog progressDialog;
    private UserLoginPresenter userLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        initFb();
        initPd();
        //得到Presenter对象并重写两个方法
        userLoginPresenter = new UserLoginPresenter(this);
    }

    private void initPd() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String uname = name.getText().toString().trim();
               final String upwd = pwd.getText().toString().trim();
                final UserBean user = new UserBean();
                user.userName = uname;
                user.passWord = upwd;
                //调用Presenter中的是否为null方法
                userLoginPresenter.submit(user);
            }
        });
    }
    //获取控件id
    private void initFb() {
        name = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
    }

    //重写接口内的两个方法
    @Override
    public void success() {
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "登陆成功，Welcome", Toast.LENGTH_SHORT).show();
        }
    });
    }
    @Override
    public void failure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "登陆失败，请重新再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void isnull() {
        Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isshow() {
        progressDialog.show();
    }


    private UserLoginModelInterface userLoginModelInterface;

    public void setUserLoginModelLogin(UserLoginModelInterface muserLoginModelLogin){
        muserLoginModelLogin = userLoginModelInterface;
    }
}
