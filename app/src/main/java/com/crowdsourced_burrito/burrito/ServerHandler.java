package com.crowdsourced_burrito.burrito;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by warde on 11/12/2017.
 */

public class ServerHandler
{
    private static ServerHandler instance = null;

    public static void setHost(String host)
    {
        instance = new ServerHandler(host);
    }

    public static ServerHandler get()
    {
        return instance;
    }


    private static final String TAG = "ServerHandler";
    private String host;
    private String sessId;
    CookieManager cookieManager = new CookieManager();

    public ServerHandler(String url)
    {
        this.host = host;
        CookieHandler.setDefault(cookieManager);
    }

    public ServerHandler(String host, String sessId)
    {
        this(host);
        this.sessId = sessId;
    }

    private JSONObject request(String page, JSONObject params)
    {
        String json_string = params.toString();
        String output = "";
        try {
            URL url = new URL("http://" + host + page);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            try {
                http.setRequestMethod("POST");
                //http.setDoOutput(true);
                http.setRequestProperty("json", json_string);
                //http.setFixedLengthStreamingMode(json_string.length());

                //OutputStream out = new BufferedOutputStream(http.getOutputStream());
                //out.write(json_string.getBytes());

                InputStream in = new BufferedInputStream(http.getInputStream());

                byte[] b = new byte[256];
                while (in.read(b) != -1) {
                    output += new String(b);
                }
            } finally {
                http.disconnect();
            }

        } catch(IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        JSONObject ret = null;
        try {
            ret = new JSONObject(output);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return ret;
    }

    public String login(String username, String password)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }

        JSONObject response = request("", json);

        if(response.has("error"))
        {
            return response.optString("error", "Something Went Wrong");
        }

        try {
            sessId = response.getString("id");
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }

        HttpCookie cookie = new HttpCookie("PHPSESSID", sessId);
        cookie.setDomain(host);
        cookie.setPath("/");
        cookie.setVersion(0);
        try {
            cookieManager.getCookieStore().add(new URI("http://" + host + '/'), cookie);
        } catch (URISyntaxException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }

        return "u logged in";

    }

    public String add(Question q)
    {
        if(sessId == null)
        {
            return "u need to log in";
        }

        JSONObject response = request("/add.php", q.toJSON());

        return response.toString();
    }


}
