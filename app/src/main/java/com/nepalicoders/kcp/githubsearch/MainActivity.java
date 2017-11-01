package com.nepalicoders.kcp.githubsearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;
import utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultsTextView;
    TextView mErrorMessageTextView;
    ProgressBar mProgressBar;

    // TODO (24) Create a ProgressBar variable to store a reference to the ProgressBar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // TODO (25) Get a reference to the ProgressBar using findViewById
    }

    private void makeGithubSearchQuery() {
        URL url = NetworkUtils.buildUrl(mSearchBoxEditText.getText().toString());
        mUrlDisplayTextView.setText(url.toString());
        new GithubQueryTask().execute(url);
    }

    private void showJsonDataView() {
        mSearchResultsTextView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenu = item.getItemId();
        if (selectedMenu == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        // TODO (26) Override onPreExecute to set the loading indicator to visible


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL stringUrl = params[0];
            String gitHubSearchResults = null;
            try {
                gitHubSearchResults = NetworkUtils.getResponseFromHttpUrl(stringUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            // TODO (27) As soon as the loading is complete, hide the loading indicator
            mProgressBar.setVisibility(View.INVISIBLE);
            if (s != null && s != "") {
                showJsonDataView();
                mSearchResultsTextView.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }
}
