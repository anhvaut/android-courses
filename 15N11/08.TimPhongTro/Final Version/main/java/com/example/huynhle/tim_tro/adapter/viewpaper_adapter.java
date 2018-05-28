package com.example.huynhle.tim_tro.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huynhle.tim_tro.R;

/**
 * Created by Huynh Le on 10/13/2017.
 */

public class viewpaper_adapter extends PagerAdapter{
    Context mContext;
    int[] mResources;
    public viewpaper_adapter(Context mContext, int[] mResources)
    {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.img_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageResource(mResources[position]);
//        imageView.setImageDrawable (container.getResources().getDrawable(mResources[position]));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
