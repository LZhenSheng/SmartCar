package com.example.smartcar.activity.base;

import androidx.appcompat.widget.Toolbar;

import com.example.smartcar.R;

import butterknife.BindView;

/**
 * 通用标题界面
 */
public class BaseTitleActivity extends BaseActivity {

    /**
     * 标题控件
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();
        //初始化Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public void initData() {
        super.initData();
        toolbar.setNavigationIcon(R.mipmap.mlogo);
    }

}
