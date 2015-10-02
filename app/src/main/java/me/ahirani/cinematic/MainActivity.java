package me.ahirani.cinematic;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {


    public List<Repository> repositoriesList = new ArrayList<>();
    public RecyclerView recyclerView;
    public RepositoryRecyclerViewAdapter repositoryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        repositoryRecyclerViewAdapter = new RepositoryRecyclerViewAdapter(repositoriesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PostFetcher fetcher = new PostFetcher();
        fetcher.execute();
    }

    private void failedLoadingPosts() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Failed to load Posts.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class PostFetcher extends AsyncTask {

        private static final String TAG = "PostFetcher";

        @Override
        protected Void doInBackground(Object... params) {
            try {

                String usernameParam = "ali-hirani";
                final String url = "https://api.github.com/users/" + usernameParam + "/repos";

                // Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);

                System.out.println("Target URL: " + url);

                // Perform GET request and check status code
                HttpResponse response = client.execute(get);
                StatusLine statusLine = response.getStatusLine();

                // Response is OK
                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        // Read response and attempt to parse
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        repositoriesList.addAll(Arrays.asList(gson.fromJson(reader, Repository[].class)));
                        System.out.println("First Repo is: " + repositoriesList.get(0).getName());
                        content.close();
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
                        failedLoadingPosts();
                    }
                } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            recyclerView.setAdapter(repositoryRecyclerViewAdapter);
        }
    }

    public String buildRepositoryString(Repository repository) {
        return repository.getName();
    }
}