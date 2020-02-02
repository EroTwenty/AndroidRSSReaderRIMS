package com.example.androidrssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    protected String RSSURL = new String();
    protected ImageButton refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RSSURL="https://www.jeuxvideo.com/rss/rss-ps4.xml";
        refreshButton = (ImageButton) findViewById(R.id.reloadRssButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromUrl(RSSURL);
            }
        });
    }
    public void gotoRSSSettings(View view)
    {
        Intent intent = new Intent(this, RSSSettingsActivity.class);
        startActivity(intent);
    }
    public void readFromUrl(String inurl){
        try {
            URL url = new URL(inurl);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
