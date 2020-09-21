package com.example.smartcar.activity;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartcar.activity.base.BaseTitleActivity;
import com.example.smartcar.R;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("视频监控中");
        d(getTitle()+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_steering_wheel:
                startActivity(MainOtherActivity.class);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
