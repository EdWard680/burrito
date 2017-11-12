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
    protected Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_question_activity);

        int qid = this.getIntent().getIntExtra("question", 0);

        ServerHandler handler = ServerHandler.get();
        question = handler.view(new Question(qid));

        FloatingActionButton new_question = findViewById(R.id.new_answer_button);
        new_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ViewQuestionActivity.this, AnswerActivity.class));
            }
        });
    }



}
