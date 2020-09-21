package com.example.smartcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.smartcar.R;

/***
 * 声音Fragment
 * @author 胜利镇
 * @time 2020/8/7 8:16
 */
public class VoiceFragment extends BaseCommonFragment {


    /**
     * 构造方法
     *
     * 固定写法
     *
     * @return
     */
    public static VoiceFragment newInstance() {
        Bundle args = new Bundle();

        VoiceFragment fragment = new VoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        //初始化定位
    }

    /***
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();


        next();
    }


    /***
     * 获取View
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice,container,false);
    }


    /**
     * 显示数据
     */
    private void next() {


    }
}
