package com.nepalicoders.kcp.githubsearch;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

    public void makeGithubSearchQuery(){
        URL url = NetworkUtils.buildUrl(mSearchBoxEditText.getText().toString());
        mUrlDisplayTextView.setText(url.toString());
        new GithubQueryTask().execute(url);
    }

    public class GithubQueryTask extends AsyncTask<URL,Void,String>{

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
            if(s!=null && s!=""){
                mSearchResultsTextView.setText(s);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenu = item.getItemId();
        if(selectedMenu == R.id.action_search){
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
