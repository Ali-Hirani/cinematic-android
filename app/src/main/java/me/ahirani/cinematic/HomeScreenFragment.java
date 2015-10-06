package me.ahirani.cinematic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeScreenFragment extends Fragment {

    EditText githubIDEditText;
    Button repoSearchButton;
    Button clearStoredRepoDataButton;

    static HomeScreenFragment newInstance() {

        HomeScreenFragment homeScreenFragment = new HomeScreenFragment();

        Bundle args = new Bundle();
        homeScreenFragment.setArguments(args);

        return homeScreenFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen_layout, container, false);

        githubIDEditText = (EditText) view.findViewById(R.id.github_id_edit_text);

        repoSearchButton = (Button) view.findViewById(R.id.repo_search_button);
        repoSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity parentActivity = (MainActivity) getActivity();
                String gitHubIDFromUser = githubIDEditText.getText().toString();

                if (gitHubIDFromUser != null && !gitHubIDFromUser.isEmpty()) {
                    parentActivity.setGitHubIDFromUserAndCreateFragment(parsedUserName(gitHubIDFromUser));
                    Toast.makeText(getActivity(), "Repositories Grabbed!", Toast.LENGTH_SHORT).show();
                    githubIDEditText.setText("");
                }
            }
        });

        clearStoredRepoDataButton = (Button) view.findViewById(R.id.clear_stored_data_button);
        clearStoredRepoDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("repo-data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(getActivity(), "Data Cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String parsedUserName(String rawUserID) {
        rawUserID.replaceAll("\\s+", "");

        if (rawUserID != null && !rawUserID.isEmpty()) {
            return rawUserID;
        } else return rawUserID;
    }
}