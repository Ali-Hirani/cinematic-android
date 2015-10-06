package me.ahirani.cinematic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RepositoryItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView repoNameTextView = (TextView) findViewById(R.id.repository_name_textview);
        repoNameTextView.setText("Repository Details");
    }
}