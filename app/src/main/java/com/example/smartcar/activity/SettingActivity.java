package com.example.smartcar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.smartcar.R;
import com.example.smartcar.activity.base.BaseTitleActivity;
import com.example.smartcar.adapter.TitleAdapter;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseTitleActivity {

    private final String titles[]={"定位","声音","图像","升降台","太阳能","雷达"};
    private int left;
    private int mid;
    private int right;
    private int index;
    TitleAdapter titleAdapter;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.title3)
    TextView title3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @OnClick(R.id.title1)
    public void OnClick(){
        for(int i=0;i<titles.length;i++){
            if(titles[i].equals(title2.getText().toString())){
               if(i==0){
                   left=4;
                   mid=5;
                   right=0;
                   vp.setCurrentItem(6,false);
               }else if(i==1){
                   left=5;
                   mid=0;
                   right=1;
                   vp.setCurrentItem(1,false);
               }else{
                   left=i-2;
                   mid=i-1;
                   right=i;
                   vp.setCurrentItem(i-1);
               }
               title1.setText(titles[left]);
               title2.setText(titles[mid]);
               title3.setText(titles[right]);
               break;
            }
        }
    }

    @OnClick(R.id.title3)
    public void OnclickTitle3(){
        for(int i=0;i<titles.length;i++){
            if(titles[i].equals(title2.getText().toString())){
                if(i==5){
                    left=5;
                    mid=0;
                    right=1;
                    vp.setCurrentItem(1,false);
                }else if(i==4){
                    left=4;
                    mid=5;
                    right=0;
                    vp.setCurrentItem(5);
                }else{
                    left=i;
                    mid=i+1;
                    right=i+2;
                    vp.setCurrentItem(i+1);
                }
                title1.setText(titles[left]);
                title2.setText(titles[mid]);
                title3.setText(titles[right]);
                break;
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        vp.setOffscreenPageLimit(3);
        titleAdapter=new TitleAdapter(getMainActivity(),getSupportFragmentManager());
        vp.setAdapter(titleAdapter);
        ArrayList<Integer> datas=new ArrayList<>();
        datas.add(5);//0
        datas.add(0);//1
        datas.add(1);//2
        datas.add(2);//3
        datas.add(3);//4
        datas.add(4);//5
        datas.add(5);//6
        datas.add(0);//7
        titleAdapter.setDatum(datas);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    vp.setCurrentItem(6,false);
                    left=4;
                    mid=5;
                    right=0;
                }else if(position==7){
                    vp.setCurrentItem(1,false);
                    left=5;
                    mid=0;
                    right=1;
                }else if(position==1){
                    left=5;
                    mid=0;
                    right=1;
                }else if(position==6){
                    left=4;
                    mid=5;
                    right=0;
                }else{
                    left=position-2;
                    mid=position-1;
                    right=position;
                }
                title1.setText(titles[left]);
                title2.setText(titles[mid]);
                title3.setText(titles[right]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
