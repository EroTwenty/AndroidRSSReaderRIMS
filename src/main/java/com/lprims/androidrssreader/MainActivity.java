package com.lprims.androidrssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected RssReader rssReader; //rssReader qui nous permet de lire un flux RSS
    protected String RSSURL = new String(); //url du flux Rss
    protected ImageButton refreshButton; //bouton refresh (en bas à gauche)
    protected ArrayList<RssItem> rssItems; // array contenant des rssItems

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        RSSURL="https://www.jeuxactu.com/rss/ja.rss";
        rssReader = new RssReader(RSSURL);
        refreshButton = (ImageButton) findViewById(R.id.reloadRssButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 refreshRssItems();
            }
        });
    }
    // met a jour l'array rssItems
    public void refreshRssItems() {
        try{
            rssItems = (ArrayList<RssItem>)rssReader.getItems();
            System.out.println();
        }
        catch (Exception e)
        {
            Log.v("Error parsing data :", e + "");
        }

    }
    public void gotoRSSSettings(View view)
    {
        Intent intent = new Intent(this, RSSSettingsActivity.class);
        startActivity(intent);
    }



}
