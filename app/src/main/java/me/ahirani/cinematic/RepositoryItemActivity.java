package me.ahirani.cinematic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RepositoryItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_item_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Gson gson = new Gson();
        String serializedRepositoryString = intent.getStringExtra("Repository");

        Repository repository = gson.fromJson(serializedRepositoryString, Repository.class);

        TextView authorNameTextview = (TextView) findViewById(R.id.author_name_textview);
        ImageView authorGithubAvatarImageview = (ImageView) findViewById(R.id.author_github_avatar_imageview);
        TextView repositoryNameTextview = (TextView) findViewById(R.id.repository_name_textview);
        TextView repositoryDescriptionTextview = (TextView) findViewById(R.id.repository_description_textview);
        TextView lastModifiedTextview = (TextView) findViewById(R.id.last_modified_textview);
        TextView creationDateTextview = (TextView) findViewById(R.id.creation_date_textview);
        TextView primaryLanguageTextview = (TextView) findViewById(R.id.primary_language_textview);

        authorNameTextview.setText("Owner ID: " + repository.getOwner().getLogin());
        repositoryNameTextview.setText("Repository Title: " + repository.getName());

        if (!repository.getDescription().isEmpty()) {
            repositoryDescriptionTextview.setText("Repository Description: " + repository.getDescription());
        } else {
            repositoryDescriptionTextview.setText("No Description Available");
        }

        SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd");

        String updatedFormattedDate = "";
        String createdFormattedDate = "";

        try {
            Date updatedRawDated = parserSDF.parse(repository.getUpdated_at());
            Date createdRawDate = parserSDF.parse(repository.getCreated_at());

            updatedFormattedDate = mySDF.format(updatedRawDated);
            createdFormattedDate = mySDF.format(createdRawDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        lastModifiedTextview.setText("Last Modified Date: " + updatedFormattedDate);
        creationDateTextview.setText("Creation Date: " + createdFormattedDate);

        primaryLanguageTextview.setText("Primary Language: " + repository.getRepoLanguage());
    }
}