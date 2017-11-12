package com.crowdsourced_burrito.burrito;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.Place;

/**
 * Created by dawnkd on 11/12/2017.
 */

public class NewQuestionActivity extends AppCompatActivity {


    protected Place place;
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
    public void submitQuestion(View view)
    {
        if(place ==null)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Please choose a Location",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        String description = ((EditText)findViewById(R.id.editText)).getText().toString();
        String title = description.split("\n")[0];

        String[] tags = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2)).getText().toString().split(" ");
        QuestionMetaData metaData = new QuestionMetaData(tags,null,place);
        Question question = new Question(title,description,metaData);

    }
    public void pickLocation(View view)
    {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                Button button = (Button)findViewById(R.id.button3);
                button.setText(place.getName());
            }
        }
    }
}
