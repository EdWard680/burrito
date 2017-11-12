package com.crowdsourced_burrito.burrito;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView questionListView;
    SwipeRefreshLayout questionSwipeRefreshLayout;
    Adapter qestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        questionListView = findViewById(R.id.activity_main_listview);

        questionListView.setAdapter(new ArrayAdapter<String>(){

        });

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText("(Skree-EEEE)");
    }

}
