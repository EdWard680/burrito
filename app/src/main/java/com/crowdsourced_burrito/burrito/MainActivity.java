package com.crowdsourced_burrito.burrito;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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



        FloatingActionButton new_question = findViewById(R.id.new_question_button);
        new_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewQuestionActivity.class));
            }
        });




//        questionSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
//        questionListView = findViewById(R.id.activity_main_listview);

//        questionListView.setAdapter(new ArrayAdapter<String>(){
//
//        });

        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText("(Skree-EEEE)");
    }

}
