package com.example.smartcar.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "MYTAGDETAIL";

    /***
     * 初始化操作
     */
    public void initData(){

    }

    /***
     * 设置监听器
     */
    protected void initListener() {}

    /***
     * 初始化控件
     */
    protected void initViews(){
        ButterKnife.bind(this);
    }

    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);

        //关闭当前界面
        finish();
    }


    /**
     * 获取界面方法
     * @return
     */
    protected BaseActivity getMainActivity() {
        return this;
    }

    /**
     * 在onCreate方法后面调用
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initViews();
        initData();
        initListener();
    }

    /***
     * 打印日志
     * @param content
     */
    public static void d(String content) {
        Log.i(TAG, content);
    }

    /***
     * 打印日志
     */
    public static void d() {
        Log.i(TAG, "");
    }


    /**
     * 启动界面，可以传递一个字符串参数
     *
     * @param clazz
     */
    protected void startActivityN(Class<?> clazz) {
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //启动界面
        startActivity(intent);
    }


}
