package com.crowdsourced_burrito.burrito;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

/**
 * Created by warde on 11/11/2017.
 */

public class Answer
{
    private static final String TAG = "Answer";

    private int id;
    private String content;
    // private User user;
    private int rating;
    private Date timestamp;

    public Answer(String content)
    {
        this.content = content;
        this.id = -1;
        this.rating = -1;
    }

    public Answer(JSONObject json)
    {
        id = json.optInt("id", -1);
        content = json.optString("content", "");
        rating = json.optInt("rating", 0);
        try {
            timestamp = SimpleDateFormat.getInstance().parse(json.optString("timestamp", "4:20AM 4/20/2020"));
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public JSONObject toJSON()
    {
        JSONObject ret = new JSONObject();

        try {
            ret.put("content", content);
        } catch(JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        if(id >= 0)
        {
            try {
                ret.put("id", id);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return ret;
    }

    public String getContent() {return content;}
    public int getRating() {return rating;}
    public Date getTimestamp() {return timestamp;}
}
