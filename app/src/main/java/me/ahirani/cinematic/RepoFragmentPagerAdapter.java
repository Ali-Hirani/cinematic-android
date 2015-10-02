package me.ahirani.cinematic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RepoFragmentPagerAdapter extends FragmentPagerAdapter {

    public RepoFragmentPagerAdapter(FragmentManager fm, String gitHubIDFromUser) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return HomeScreenFragment.newInstance(position);
            case 1:
                return RepoCardsFragment.newInstance(position);
            default:
                return HomeScreenFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}