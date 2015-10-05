package me.ahirani.cinematic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RepoFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public RepoFragmentPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addItemToList(Fragment fragment) {
        fragments.add(fragment);
    }
}