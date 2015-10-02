package me.ahirani.cinematic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

    private String gitHubIDFromUser;

    RepoFragmentPagerAdapter repoFragmentPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        repoFragmentPagerAdapter = new RepoFragmentPagerAdapter(getSupportFragmentManager(), gitHubIDFromUser);
        viewPager.setAdapter(repoFragmentPagerAdapter);
    }

    public String getGitHubIDFromUser() {
        return gitHubIDFromUser;
    }

    public void setGitHubIDFromUser(String gitHubIDFromUser) {
        this.gitHubIDFromUser = gitHubIDFromUser;
    }
}