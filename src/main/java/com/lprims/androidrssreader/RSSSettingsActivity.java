package com.lprims.androidrssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RSSSettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsssettings);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        EditText rss_url = (EditText)findViewById(R.id.rssUrl);
        if (b!=null)
        {
            String rssUrlFromIntent=(String)b.get("RSS_FEED_URL");
            if (!rssUrlFromIntent.equals(""))
                rss_url.setText(rssUrlFromIntent);
            else
                rss_url.setText("Enter RSS URL");
        }
    }
    public void settingsOver(View view)
    {
        Intent returnIntent = new Intent();
        EditText rss_url = (EditText)findViewById(R.id.rssUrl);
        returnIntent.putExtra("RSS_FEED_URL_BACK",rss_url.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
