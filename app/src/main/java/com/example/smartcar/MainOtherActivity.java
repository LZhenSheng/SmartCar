package com.example.smartcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class MainOtherActivity extends BaseTitleActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;

    /***
     * 为各控件注册点击事件
     * @param view
     */
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                clearBackground();
                btn1.setBackgroundResource(R.drawable.background_button_down);
                break;
            case R.id.btn2:
                clearBackground();
                btn2.setBackgroundResource(R.drawable.background_button_down);

                break;
            case R.id.btn3:
                clearBackground();
                btn3.setBackgroundResource(R.drawable.background_button_down);

                break;
            case R.id.btn4:
                clearBackground();
                btn4.setBackgroundResource(R.drawable.background_button_down);

                break;
            case R.id.btn5:
                clearBackground();
                btn5.setBackgroundResource(R.drawable.background_button_down);

                break;
            case R.id.btn6:
                clearBackground();
                btn6.setBackgroundResource(R.drawable.background_button_down);
                break;
        }
    }

    private void clearBackground() {
        btn1.setBackgroundResource(R.drawable.background_button_up);
        btn2.setBackgroundResource(R.drawable.background_button_up);
        btn3.setBackgroundResource(R.drawable.background_button_up);
        btn4.setBackgroundResource(R.drawable.background_button_up);
        btn5.setBackgroundResource(R.drawable.background_button_up);
        btn6.setBackgroundResource(R.drawable.background_button_up);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main_other, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photograph:
                startActivity(MainActivity.class);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
