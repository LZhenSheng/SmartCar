package com.example.smartcar.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartcar.activity.base.BaseActivity;
import com.example.smartcar.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login)
    Button btn_login;

    @OnClick(R.id.login)
    public void OnClick(){
        startActivity(MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Resources resources=getMainActivity().getResources();
        Drawable drawable=resources.getDrawable(R.mipmap.mlogo);
    }
}
