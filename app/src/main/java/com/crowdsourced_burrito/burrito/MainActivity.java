package com.crowdsourced_burrito.burrito;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView questionListView;
    SwipeRefreshLayout questionSwipeRefreshLayout;
    Adapter qestionAdapter;
    PlaceDetectionClient mPlaceDetectionClient;
    static Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServerHandler.setHost("129.161.69.63");
        Log.i("Main", "App Has Started");
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
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

        refresh();

    }


    public void refresh()
    {
        try {
            Task<PlaceLikelihoodBufferResponse> result = mPlaceDetectionClient.getCurrentPlace(null);
            result.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for(PlaceLikelihood placeLikelihood : likelyPlaces)
                    {
                        MainActivity.place = placeLikelihood.getPlace();
                        break;
                    }

                    likelyPlaces.release();
                }
            });

            class OnPlaceComplete implements OnCompleteListener<PlaceLikelihoodBufferResponse>
            {
                MainActivity act;
                public OnPlaceComplete(MainActivity act)
                {
                    this.act = act;
                }

                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task)
                {
                    act.onRefreshComplete(task);
                }
            }

            result.addOnCompleteListener(new OnPlaceComplete(this));
        } catch (SecurityException e) {
            Log.e("MainActivity", e.getLocalizedMessage());
            Toast toast = Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onRefreshComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
        ServerHandler handler = ServerHandler.get();
        QuestionMetaData data = new QuestionMetaData(new String[]{}, new Date(), place);
        ArrayList<Question> questions = handler.poll(data);

        ListView v = (ListView) findViewById(R.id.question_list);
        v.setAdapter(new QuestionAdapter(this, questions));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                // EX : call intent if you want to swich to other activity
                return true;
//            case R.id.help:
//                //your code
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

class QuestionAdapter extends ArrayAdapter<Question>
{
    public QuestionAdapter(Context context, ArrayList<Question> questions)
    {
        super(context, 0, questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Question question = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_list_item, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.question_title);
        TextView description = (TextView) convertView.findViewById(R.id.question_description);

        title.setText(question.getTitle());
        description.setText(question.getTitle());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                IntentViewQuestion(view, getItem(position));
            }
        });

        convertView.bringToFront();

        return convertView;
    }

    public void IntentViewQuestion(android.view.View view, Question question) {
        Intent intent = new Intent(getContext(), ViewQuestionActivity.class);
        intent.putExtra("questionID", question.getId());
        getContext().startActivity(intent);
    }
}
