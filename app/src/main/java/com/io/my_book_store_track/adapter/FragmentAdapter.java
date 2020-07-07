package com.io.my_book_store_track.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mfragments = new ArrayList<>();
    List<String> mfragmentlist = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f1,String s1){
        mfragments.add(f1);
        mfragmentlist.add(s1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mfragmentlist.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mfragments.get(i);
    }

    @Override
    public int getCount() {
        return mfragmentlist.size();
    }
}
