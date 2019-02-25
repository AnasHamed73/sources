package com.example.kikuchio.unitconverter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ConverterActivity extends FragmentActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_converter);
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new UnitClassPagerAdapter(getSupportFragmentManager()));
    }
}
