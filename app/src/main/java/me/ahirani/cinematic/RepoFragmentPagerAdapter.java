package me.ahirani.cinematic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RepoFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String gitHubIDFromUser;
    private List<Fragment> fragments = new ArrayList<>();


    public RepoFragmentPagerAdapter(FragmentManager fm, String gitHubIDFromUser) {
        super(fm);
        this.gitHubIDFromUser = gitHubIDFromUser;
    }

    public RepoFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
//        switch (position) {
//            case 0:
//                return HomeScreenFragment.newInstance(position);
//            case 1:
//                return RepoCardsFragment.newInstance(position, gitHubIDFromUser);
//            default:
//                return HomeScreenFragment.newInstance(position);
//        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addItemToList(Fragment fragment) {
        fragments.add(fragment);
    }
}