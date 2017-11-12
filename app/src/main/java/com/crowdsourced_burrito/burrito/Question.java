package com.crowdsourced_burrito.burrito;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.google.android.gms.location.places.Place;

import java.util.Date;

/**
 * Created by warde on 11/11/2017.
 */

public class Question {
    private static final String TAG = "Question";

    private String title;
    private String description;
    private int id;
    private QuestionMetaData data;
    private User asker;
    private Answer[] answers;

    public Question(int id)
    {
        this.id = id;
    }

    public Question(String title, String desc, QuestionMetaData data)
    {
        this.id = -1;  // uninitialized
        this.title = title;
        this.description = desc;
        this.data = data;
    }

    public Question(JSONObject json)
    {
        title = json.optString("title");
        description = json.optString("desc");
        id = json.optInt("id");
        String username = json.optString("asker");
        if(username != null)
        {
            asker = User.merge(new User(username));
        }

        JSONArray a = json.optJSONArray("answers");
        answers = new Answer[a.length()];
        for(int i = 0; i < a.length(); i++)
            answers[i] = new Answer(a.optJSONObject(i));

        data = new QuestionMetaData(json.optJSONObject("meta"));
    }

    public JSONObject toJSON()
    {
        JSONObject ret = new JSONObject();

        if(title != null)
        {
            try {
                ret.put("title", title);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(description != null)
        {
            try {
                ret.put("description", description);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(id >= 0)
        {
            try {
                ret.put("id", id);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(asker != null)
        {
            try {
                ret.put("asker", asker.getName());
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(data != null)
        {
            try {
                ret.put("meta", data.toJSON());
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return ret;
    }

    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public User getUser() {return asker;}
    public QuestionMetaData getData() {return data;}
    public Answer[] getAnswers() {return answers;}
    public int getId() {return id;}
}
