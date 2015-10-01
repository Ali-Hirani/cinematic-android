package me.ahirani.cinematic;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends Activity {


    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                final String OMDB_BASE_URL = "http://www.omdbapi.com/?";
                final String FILM_TITLE_PARAM = "t";
                final String YEAR_PARAM = "y";
                final String PLOT_LENGTH_PARAM = "plot";
                final String RESPONSE_FORMAT_PARAM = "r";

                Uri builtUri = Uri.parse(OMDB_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter(FILM_TITLE_PARAM, "Taken")
                        .appendQueryParameter(YEAR_PARAM, "")
                        .appendQueryParameter(PLOT_LENGTH_PARAM, "short")
                        .appendQueryParameter(RESPONSE_FORMAT_PARAM, "json")
                        .build();

                String url = builtUri.toString();

                // Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);

                System.out.println("Target URL: " + url);

                // Perform GET request and check status code
                HttpResponse response = client.execute(post);
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
                        film = new Film();
                        film = gson.fromJson(reader, Film.class);
                        System.out.println("Rating is: " + film.getImdbRating());
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

            TextView filmContentText = (TextView) findViewById(R.id.film_content_text_view);
            filmContentText.setText(buildFilmDataString(film));

        }
    }

    public String buildFilmDataString(Film film) {

        String filmData;
        String title = film.getTitle();
        String year = film.getYear();
        String rated = film.getRated();
        String released = film.getReleased();
        String runtime = film.getRuntime();
        String genre = film.getGenre();
        String director = film.getDirector();
        String writer = film.getWriter();
        String actors = film.getActors();
        String plot = film.getPlot();
        String language = film.getLanguage();
        String country = film.getCountry();
        String awards = film.getAwards();
        String poster = film.getPoster();
        String metaScore = film.getMetaScore();
        String imdbRating = film.getImdbRating();
        String imdbVotes = film.getImdbVotes();
        String imdbID = film.getImdbID();
        String type = film.getType();
        String response = film.getResponse();
        filmData = "Title: " + title + "Year: " + year + "Rated: " + rated + "Released: " + released + "Runtime "
                + runtime + "Genre: " + genre + "Director: " + director + "Writer: " + writer + "Actors: " + actors
                + "Plot: " + plot + "Language: " + language + "Country: " + country + "Awards: " + awards + "Poster "
                + poster + "Metascore: " + metaScore + "imdbRating: " + imdbRating + "imdbVotes: " + imdbVotes
                + "imdbID: " + imdbID + "Type: " + type + "Response: " + response;

        return filmData;
    }
}