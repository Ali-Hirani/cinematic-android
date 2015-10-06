package me.ahirani.cinematic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public RepoFragmentPagerAdapter repoFragmentPagerAdapter;
    public ViewPager viewPager;

    public List<String> searchedIDs;
    public boolean needsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchedIDs = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.home_screen_toolbar_text);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        repoFragmentPagerAdapter = new RepoFragmentPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        repoFragmentPagerAdapter.addItemToList(HomeScreenFragment.newInstance());
        viewPager.setAdapter(repoFragmentPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

//        needsData = !(searchedIDs == null) && !searchedIDs.isEmpty();
        System.out.println("onPause end");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("asdf onStop");
//        if (needsData) {
        SharedPreferences sharedPrefs = this.getSharedPreferences("repo-data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet("me.ahirani.cinematic.IDSet", getSetFromList(searchedIDs));
        editor.apply();
        System.out.println("asdf Data stored");
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("asdf onStart");

        SharedPreferences sharedPrefs = this.getSharedPreferences("repo-data", Context.MODE_PRIVATE);
        Set<String> storedIdsSet = sharedPrefs.getStringSet("me.ahirani.cinematic.IDSet", null);

        if (storedIdsSet != null && !storedIdsSet.isEmpty()) {
            System.out.println("asdf" + storedIdsSet.size());
            searchedIDs = getListFromSet(storedIdsSet);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("asdf onResume");

        closeAllFragments();

        if (!(searchedIDs == null) && !searchedIDs.isEmpty()) {
            System.out.println("asdf Searched IDs is Length: " + searchedIDs.size());
//            for (int i = 0; i < searchedIDs.size(); i++) {
            setGitHubIDFromUserAndCreateFragment(searchedIDs.get(0));
//            }
        }
    }

    public void setGitHubIDFromUserAndCreateFragment(String gitHubIDFromUser) {
        searchedIDs.add(gitHubIDFromUser);

        RepoCardsFragment fragment = RepoCardsFragment.newInstance(gitHubIDFromUser);
        repoFragmentPagerAdapter.addItemToList(fragment);
        repoFragmentPagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(gitHubIDFromUser));
    }

    public void closeAllFragments() {
        repoFragmentPagerAdapter.removeAllItemsFromList();
        repoFragmentPagerAdapter.notifyDataSetChanged();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        repoFragmentPagerAdapter.notifyDataSetChanged();
        repoFragmentPagerAdapter.addItemToList(HomeScreenFragment.newInstance());
        repoFragmentPagerAdapter.notifyDataSetChanged();
    }

    public void closeAllFragmentsAndClearSavedList() {
        closeAllFragments();
        this.searchedIDs.clear();
    }

    public Set<String> getSetFromList(List<String> list) {
        Set<String> set = new HashSet<>();
        set.addAll(list);
        return set;
    }

    public List<String> getListFromSet(Set<String> set) {
        List<String> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    public void setSearchedIDs(List<String> searchedIDs) {
        this.searchedIDs = searchedIDs;
    }
}