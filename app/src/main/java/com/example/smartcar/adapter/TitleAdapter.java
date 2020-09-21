package com.example.smartcar.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.smartcar.fragment.ImageFragment;
import com.example.smartcar.fragment.LiftingPlatformFragment;
import com.example.smartcar.fragment.LocationFragment;
import com.example.smartcar.fragment.RadarFragment;
import com.example.smartcar.fragment.SolarEnergyFragment;
import com.example.smartcar.fragment.VoiceFragment;

public class TitleAdapter extends BaseFragmentPagerAdapter<Integer> {

    public TitleAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回Fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return RadarFragment.newInstance();
        } else if (position == 1) {
            return LocationFragment.newInstance();
        } else if (position == 2) {
            return VoiceFragment.newInstance();
        } else if (position==3){
            return ImageFragment.newInstance();
        } else if(position==4){
            return LiftingPlatformFragment.newInstance();
        }else if(position==5){
            return SolarEnergyFragment.newInstance();
        }else if(position==6){
            return RadarFragment.newInstance();
        }else{
            return LocationFragment.newInstance();
        }
    }


}