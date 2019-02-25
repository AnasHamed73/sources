package com.example.kikuchio.unitconverter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kikuchio.unitconverter.length.LengthClassFragment;
import com.example.kikuchio.unitconverter.temperature.TemperatureClassFragment;
import com.example.kikuchio.unitconverter.volume.VolumeClassFragment;
import com.example.kikuchio.unitconverter.weight.WeightClassFragment;

import java.util.ArrayList;
import java.util.List;

public class UnitClassPagerAdapter extends FragmentPagerAdapter {

    private static List<UnitClassFragment> pages = new ArrayList<>(3);

    static {
        pages.add(new WeightClassFragment());
        pages.add(new VolumeClassFragment());
        pages.add(new LengthClassFragment());
        pages.add(new TemperatureClassFragment());
    }

    UnitClassPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return pages.get(i);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).unitClass();
    }
}
