package com.crowdsourced_burrito.burrito;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/**
 * Created by warde on 11/11/2017.
 */

public class Question {
    private static final String TAG = "Question";

    private String title;
    private String description;
    private int id;
    // private Tag[] tags;
    private User asker;

    public Question(String title, String desc)
    {
        this.id = -1;  // uninitialized
        this.title = title;
        this.description = desc;
    }

    public Question(JSONObject json)
    {
        title = json.optString("title");
        description = json.optString("desc");
        id = json.optInt("id");

    }

    public JSONObject toJSON()
    {
        JSONObject ret = new JSONObject();

        try {
            ret.put("title", title);
        } catch(JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        try {
            ret.put("description", description);
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

    public String getTitle() {return title;}
    public String getDescription() {return description;}
}
