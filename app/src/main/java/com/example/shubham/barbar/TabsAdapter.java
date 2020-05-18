package com.example.shubham.barbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position)
        {
            case 0:

                return fragment = new Men();
            case 1:
                return fragment = new Women();
            case 2:
                return fragment = new Anniversary();
            case 3:
                return fragment = new Wedding();
            case 4:
                return fragment = new Parties();


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }


    public CharSequence getPageTitle(int Position)
    {
        switch (Position)
        {
            case 0:
                return "Men";
                case 1:
            return "Women";
            case 2:
                return "Anniversary";
            case 3:
                return "Wedding";
            case 4:
            return "Parties ";


            default:
                return null;
        }
    }
}
