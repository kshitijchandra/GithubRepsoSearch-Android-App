package com.nepalicoders.kcp.githubsearch;

import android.net.Uri;
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
        try {
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            mSearchResultsTextView.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
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
