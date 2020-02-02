package com.lprims.androidrssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected RssReader rssReader; //rssReader qui nous permet de lire un flux RSS
    protected String RSSURL = new String(); //url du flux Rss
    protected ImageButton refreshButton; //bouton refresh (en bas à gauche)
    protected ArrayList<RssItem> rssItems; // array contenant des rssItems
    protected ListView rssList;
    ArrayAdapter<String> adapter;
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
        adapter = new ArrayAdapter<String>(this,R.layout.rss_feed,R.id.textview);
        rssList = (ListView) findViewById(R.id.rssView);
        refreshButton = (ImageButton) findViewById(R.id.reloadRssButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 refreshRssItems();
                 refreshRssList();
            }
        });
    }
    // met a jour l'array rssItems
    public void refreshRssItems() {
        try{
            rssItems = (ArrayList<RssItem>)rssReader.getItems();
            refreshRssList();
        }
        catch (Exception e)
        {
            Log.v("Error parsing data :", e + "");
        }

    }
    // met a jour la liste Rss
    public void refreshRssList() {
    for (int i=0 ; i < rssItems.size() ; i++)
    {

        adapter.add(rssItems.get(i).title);
        /*adapter.add(rssItems.get(i).link);
        adapter.add(rssItems.get(i).imageUrl);
        adapter.add(rssItems.get(i).description);*/
        adapter.notifyDataSetChanged();
        rssList.setAdapter(adapter);
        System.out.println("Titre : " + rssItems.get(i).title);
        System.out.println("Lien : " + rssItems.get(i).link);
        System.out.println("Image URL : " + rssItems.get(i).imageUrl);
        System.out.println("Description : " + rssItems.get(i).description);
    }

    }
    //aller vers les RSS Settings
    public void gotoRSSSettings(View view)
    {
        Intent intent = new Intent(this, RSSSettingsActivity.class);
        startActivity(intent);
    }



}
