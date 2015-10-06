package me.ahirani.cinematic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.Collections;
import java.util.List;

public class RepoCardsFragment extends Fragment {
    @Nullable
    private String gitHubIDFromUser = "";
    private List<Repository> repositoriesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RepositoryRecyclerViewAdapter repositoryRecyclerViewAdapter;

    static RepoCardsFragment newInstance(String gitHubIDFromUser) {
        RepoCardsFragment repoCardsFragment = new RepoCardsFragment();

        Bundle args = new Bundle();
        args.putString("ID", gitHubIDFromUser);
        repoCardsFragment.setArguments(args);

        return repoCardsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.repo_cards_fragment_layout, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        Button closePageButton = (Button) mainActivity.findViewById(R.id.close_page_button);
        closePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity parentActivity = (MainActivity) getActivity();
                if (parentActivity != null) {
                    parentActivity.closeAllFragments();
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        repositoryRecyclerViewAdapter = new RepositoryRecyclerViewAdapter(repositoriesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), RepositoryItemActivity.class);

                        String serializedRepoID = new Gson().toJson(repositoriesList.get(position));
                        intent.putExtra("Repository", serializedRepoID);

                        startActivity(intent);
                    }
                })
        );

        Bundle args = getArguments();
        this.gitHubIDFromUser = args.getString("ID", gitHubIDFromUser);

        PostFetcher fetcher = new PostFetcher();
        fetcher.execute();

        return view;
    }

    public class PostFetcher extends AsyncTask {

        private static final String TAG = "PostFetcher";

        @Override
        protected Void doInBackground(Object... params) {
            try {

                String usernameParam = gitHubIDFromUser;
                final String url = "https://api.github.com/users/" + usernameParam + "/repos";
//                final String url = "google.ca/";

                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);

                HttpResponse response = client.execute(get);
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        if (repositoriesList != null) {
                            repositoriesList.addAll(Arrays.asList(gson.fromJson(reader, Repository[].class)));
                        }
                        content.close();
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
                    }
                } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                }
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            recyclerView.setAdapter(repositoryRecyclerViewAdapter);
        }
    }
}
