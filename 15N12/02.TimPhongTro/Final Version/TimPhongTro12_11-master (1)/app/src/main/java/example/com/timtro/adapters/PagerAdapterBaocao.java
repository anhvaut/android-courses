package example.com.timtro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import example.com.timtro.views.FragmentBaoCaoStart;
import example.com.timtro.views.FragmentSuCo;
import example.com.timtro.views.FragmentLamDung;

public class PagerAdapterBaocao extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapterBaocao(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentBaoCaoStart tab1 = new FragmentBaoCaoStart();
                return tab1;
            case 1:
                FragmentSuCo tab2 = new FragmentSuCo();
                return tab2;
            case 2:
                FragmentLamDung tab3 = new FragmentLamDung();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}