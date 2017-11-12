package com.crowdsourced_burrito.burrito;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by dawnkd on 11/12/2017.
 */

public class NewQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_question_activity);

        Spinner question_type_spinner = findViewById(R.id.question_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> question_type_adapter = ArrayAdapter.createFromResource(this, R.array.question_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        question_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        question_type_spinner.setAdapter(question_type_adapter);

        String[] tags = getResources().getStringArray(R.array.tags);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,tags);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        textView.setAdapter(adapter);


    }
}
