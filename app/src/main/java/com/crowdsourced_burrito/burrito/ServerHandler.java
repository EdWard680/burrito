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
import java.net.URLEncoder;
import java.util.ArrayList;

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
        this.host = url;
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
        StringBuffer output = new StringBuffer("");
        try {
            URL url = new URL("http://" + host + page);
            final HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setDoInput(true);
                //http.setRequestProperty("json", json_string);
                class MyThread implements Runnable {
                    private  HttpURLConnection httpURLConnection;
                    private  StringBuffer stringBuffer;
                    private  String params;

                    public MyThread(HttpURLConnection connection, StringBuffer str, String params) {
                        httpURLConnection = connection;
                        stringBuffer = str;
                        this.params = params;
                    }

                    public void run() {
                        try {
                            http.setFixedLengthStreamingMode(params.length());


                            OutputStream out = new BufferedOutputStream(http.getOutputStream());
                            out.write(params.getBytes());
                            out.flush();
                            out.close();
                            http.connect();
                            InputStream in = new BufferedInputStream(http.getInputStream());

                            byte[] b = new byte[256];
                            while (in.read(b) != -1) {
                                stringBuffer.append(new String(b));
                            }
                            Log.i(TAG,stringBuffer.toString());
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG,e.getLocalizedMessage());
                        }
                        finally {
                            http.disconnect();
                        }
                    }
                }
                Thread thread = new Thread(new MyThread(http, output, "json="+URLEncoder.encode(json_string, "UTF-8")));

                thread.start();
                try {
                    thread.join();
                }
                catch (Exception e)
                {
                    Log.e(TAG,e.getLocalizedMessage());
                }

        } catch(IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        JSONObject ret = null;
        try {
            ret = new JSONObject(output.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
            ret = new JSONObject();
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

        if(response.length() == 0)
            return "u did it";
        else
            return response.toString();
    }

    public Question view(Question q)
    {
        JSONObject response = request("/view.php", q.toJSON());

        return new Question(response);
    }



}
