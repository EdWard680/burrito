package com.crowdsourced_burrito.burrito;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by warde on 11/11/2017.
 */

public class User
{
    private static final String TAG = "User";
    private static Map<String, User> users = new HashMap<>();

    private String name;
    private String first;
    private String last;
    private Integer rating;

    public static User merge(User input)
    {
        if(input.name == null) return null;
        User ret = users.putIfAbsent(input.name, input);

        if (input.first != null) ret.first = input.first;
        if (input.last != null) ret.last = input.last;
        if (input.rating != null) ret.rating = input.rating;

        return ret;
    }

    public User(String name)
    {
        this.name = name;
        this.rating = null;
    }

    public User(String name, String first, String last)
    {
        this(name);
        this.first = first;
        this.last = last;
    }

    public User(JSONObject json)
    {
        try {
            this.name = json.getString("name");
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        this.first = json.optString("first");
        this.last = json.optString("last");
        this.rating = json.optInt("rating", 0);
    }

    public JSONObject toJSON()
    {
        JSONObject ret = new JSONObject();

        try {
            ret.put("name", name);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        if (first != null) {
            try {
                ret.put("first", first);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if (last != null) {
            try {
                ret.put("last", last);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return ret;
    }

    public String getName() {return name;}
    public String firstName() {return first;}
    public String lastName() {return last;}
    public Integer getRating() {return rating;}
}
