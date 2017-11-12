package com.crowdsourced_burrito.burrito;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dawnkd on 11/12/2017.
 */

public class ViewQuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_question_activity);

        FloatingActionButton new_question = findViewById(R.id.new_answer_button);
        new_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ViewQuestionActivity.this, AnswerActivity.class));
            }
        });
    }



}
