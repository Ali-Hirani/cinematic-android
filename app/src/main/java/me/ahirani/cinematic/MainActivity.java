package me.ahirani.cinematic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    RepoFragmentPagerAdapter repoFragmentPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    public void setGitHubIDFromUserAndCreateFragment(String gitHubIDFromUser) {
        RepoCardsFragment fragment = RepoCardsFragment.newInstance(gitHubIDFromUser);
        repoFragmentPagerAdapter.addItemToList(fragment);
        repoFragmentPagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(gitHubIDFromUser));

    }
}