package com.example.huynhle.tim_tro.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.huynhle.tim_tro.R;
import com.example.huynhle.tim_tro.adapter.viewpaper_adapter;

/**
 * Created by Huynh Le on 11/20/2017.
 */

public class chi_tiet_room extends AppCompatActivity {
    ViewPager mPager;
    viewpaper_adapter mPagerAdapter;
    int [] mImageResources =
            {
                    R.drawable.tro2,
                    R.drawable.tro3,
                    R.drawable.tro4,
                    R.drawable.tro1
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_room);

        mPager = (ViewPager) findViewById(R.id.viewPager);

        mPagerAdapter = new viewpaper_adapter(this, mImageResources);
        mPager.setAdapter(mPagerAdapter);
    }
}
