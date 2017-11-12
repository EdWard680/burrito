package com.crowdsourced_burrito.burrito;

import android.util.Log;

import com.google.android.gms.location.places.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by warde on 11/12/2017.
 */

public class QuestionMetaData {
    private static final String TAG = "QuestionMetaData";
    private String[] tags;
    private Date when;
    private Place where;

    public QuestionMetaData(String[] tags, Date when, Place where)
    {
        this.tags = tags;
        this.when = when;
        this.where = where;
    }

    public QuestionMetaData(JSONObject json)
    {
        tags = json.optString("tags", "").split(",");
        try {
            when = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.optString("timestamp", "2020-4-20 4:20:00.0").split(".")[0]);
        } catch (ParseException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public JSONObject toJSON()
    {
        JSONObject ret = new JSONObject();

        if(tags != null)
        {
            String Skree = "";
            for(String tag : tags) Skree += tag + ',';
            try {
                ret.put("tags", Skree.substring(0, Skree.length() - 1));
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(when != null)
        {
            try {
                ret.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(when));
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        if(where != null)
        {
            try {
                ret.put("lat", where.getLatLng().latitude);
                ret.put("lng", where.getLatLng().longitude);
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return ret;
    }
}
