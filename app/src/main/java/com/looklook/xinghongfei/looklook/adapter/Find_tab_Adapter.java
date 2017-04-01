package com.looklook.xinghongfei.looklook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lin on 2017/2/25.
 */

public class Find_tab_Adapter extends FragmentPagerAdapter {
    private List<String> list_Title;
    private List<Fragment> list_fragment;

    public Find_tab_Adapter(FragmentManager fm,List<Fragment> paramList, List<String> paramList1) {
        super(fm);
        this.list_fragment = paramList;
        this.list_Title = paramList1;
    }


    public int getCount()
    {
        return this.list_Title.size();
    }

    public Fragment getItem(int paramInt)
    {
        return (Fragment)this.list_fragment.get(paramInt);
    }

    public CharSequence getPageTitle(int paramInt)
    {
        return (CharSequence)this.list_Title.get(paramInt % this.list_Title.size());
    }
}
