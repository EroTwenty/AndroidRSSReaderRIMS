package com.lprims.androidrssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    protected String[] linksList;
    protected ListView rssList;
    protected ArrayAdapter<String> adapter;
    protected int LAUNCH_SETTINGS_ACTIVITY = 1;
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
        rssList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String url = linksList[position];
                String url = linksList[position];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
    // met a jour l'array rssItems
    public void refreshRssItems() {
        try{
            rssReader.setUrl(RSSURL);
            rssItems = (ArrayList<RssItem>)rssReader.getItems();
            refreshRssList();
        }
        catch (Exception e)
        {
            rssItems = null;
            Log.v("Error parsing data :", e + "");
            Toast.makeText(getApplicationContext(),"Erreur : "+e,Toast.LENGTH_SHORT).show();
        }

    }
    // met a jour la liste Rss
    public void refreshRssList()
    {

        if(rssItems != null)
        {
            adapter.clear();
            linksList=new String[rssItems.size()];
            for (int i=0 ; i < rssItems.size() ; i++)
            {
                linksList[i]=rssItems.get(i).link;
                adapter.add(rssItems.get(i).title);
                adapter.notifyDataSetChanged();
                rssList.setAdapter(adapter);
            }

        }

    }
    //aller vers les RSS Settings
    public void gotoRSSSettings(View view)
    {

        Intent intent = new Intent(this, RSSSettingsActivity.class);
        intent.putExtra("RSS_FEED_URL",RSSURL);
        startActivityForResult(intent, LAUNCH_SETTINGS_ACTIVITY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SETTINGS_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                RSSURL = data.getStringExtra("RSS_FEED_URL_BACK");
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }



}
