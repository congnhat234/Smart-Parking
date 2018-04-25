package com.smartparking.admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter  extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new Fragment1();
                break;
            case 1:
                frag = new Fragment2();
                break;
            case 2:
                frag = new Fragment3();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Login";
                break;
            case 1:
                title = "Thongtin";
                break;
            case 2:
                title = "Thanhtoan";
                break;
        }

        return title;
    }
}
